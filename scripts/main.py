import scipy.integrate as ode
import numpy as np
import matplotlib.pyplot as plt
import util

# Gravitational constant
G = 6.7430e-11 # in m^3/kg/s^2

# Speed of light
c = 299792458 # in m/s

# Mass of the Sun
M_sun = 1.989e30 # in kg

# Schwarzschild radius of the Sun
r_s = 2 * G * M_sun / c**2 # in m

# Initial values (position in m and velocity in m/s) of earth
x0 = np.array([147.1e9, 0])
v0 = np.array([0, 29783])


# Defining equation of the system
def f(t, y, M):
    
    # Do inner sums first to avoid doing them many times over
    # Also calculate distances right away
    x,v = y.reshape(2, -1)
    x = x.reshape(-1, 3)
    v = v.reshape(-1, 3)
    n = len(x)
    r = np.empty((n,n))
    a_newtonian = np.empty((n, 3))
    inner_sums = np.empty(n)
    for i in range(n):
        sum = 0
        x_i = x[i]
        a_newtonian_i = 0
        for j in range(n):
            x_j = x[j]
            r_ij = 0
            
            if i == j:
                continue
            
            if(i < j):      
                r_ij = np.linalg.norm(x_i - x_j)
                r[i][j] = r_ij
                r[j][i] = r_ij
            else:
                r_ij = r[i][j]
            a_newtonian_i += M[j] / r_ij**3 * (x_j - x_i)
            sum += M[j] / r_ij
        a_newtonian[i] = G * a_newtonian_i
        inner_sums[i] = G * sum

    a_relativistic = np.empty((n, 3))

    for i in range(n):
        a_relativistic_i = 0
        x_i = x[i]
        v_i = v[i]
        for j in range(n):
            if i == j:
                continue

            a_relativistic_ij = 0

            x_j = x[j]
            v_j = v[j]
            r_ij = r[i][j]

            n_ji = (x_j - x_i) / r_ij

            sum = np.inner(v_i, v_i) + 2 * np.inner(v_j, v_j) - 4 * np.inner(v_i, v_j) - 3/2 * np.inner(n_ji, v_j)**2 - 4 * inner_sums[i] - inner_sums[j] + np.inner(x_j - x_i, a_newtonian[j]) / 2

            a_relativistic_ij += n_ji / r_ij**2 * sum
            prod = np.inner(-n_ji, 4 * v_i - 3 * v_j) * (v_i - v_j)

            a_relativistic_ij += prod / r_ij**2
            a_relativistic_ij += 7/2 * a_newtonian[j] / r_ij
            a_relativistic_i += M[j] * a_relativistic_ij
        a_relativistic[i] = G / c**2 * a_relativistic_i

    a = a_newtonian + a_relativistic

    print(t)
    return np.concatenate((v.flatten(), a.flatten()))

if __name__ == "__main__":
    data = util.read_celestial_data()
    M = util.collect_masses(data)
    y0 = util.collect_state_vectors(data)
    n = len(data)
    # Time span for integration (10 years)
    t_span = [0, 3.154e6] # in s => 1 years
    # Step size
    dt = 864 # in seconds, 1 day
    timesteps = np.arange(t_span[0], t_span[1], dt)

    # Additional options for the ODE solver
    options = {'rtol': 1e-6, 'atol': 1e0}

    sol = ode.solve_ivp(f, t_span, y0, 'DOP853', timesteps, args=(M,), **options)

    x,v = np.reshape(sol.y, (2, n, 3, -1))
    # Plot

    #ax = plt.figure().add_subplot(projection='3d')

    i = 0
    for x_ in x:
        x, y, z = x_
        plt.plot(x, y, label=data[i]["Body"])
        np.savetxt("data/" + data[i]["Body"] + '.out', x_, delimiter=',')
        i += 1

        a,b,c = x_
        if 0 in a:
            print(data[i]["Body"])




        #ax.plot(*x_, lw=0.5)
    #ax.set_xlabel("X Axis")
    #ax.set_ylabel("Y Axis")
    #ax.set_zlabel("Z Axis")
    plt.legend()
    plt.show()


