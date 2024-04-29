import math

import numpy as np

# Gravitational constant
G = 6.7430e-11 # in m^3/kg/s^2

def two_body_potential(A, B):
    return - G * A.mass * B.mass / A.distance(B)

def two_body_acceleration(A, B):
    return np.subtract(A, B) * G * B.mass / A.distance_squared(B)

class Body():
    def __init__(self, name, ID, mass, x, y, z, vx, vy, vz):
        self.name = name
        self.ID = ID
        self.mass = mass
        self.pos = np.array([x, y, z])
        self.vel = np.array([vx, vy, vz])

    def velocity_squared(self):
        return self.vel[0]**2 + self.vel[1]**2 + self.vel[2]**2
    
    def velocity(self):
        return math.sqrt(self.velocity_squared())
    
    def distance_squared(self, other = None):
        if other == None:
            return self.pos[0]**2 + self.pos[1]**2 + self.pos[2]**2
        else:
            return (self.pos[0] - other.pos[0])**2 + (self.pos[1] - other.pos[1])**2 + (self.pos[2] - other.pos[2])**2
        
    def distance(self, other = None):
        return math.sqrt(self.distance_squared(other))
    
    def kinetic_energy(self):
        return 0.5 * self.mass * self.velocity_squared()
    
    def energy(self, bodies):
        return self.kinetic_energy() + np.sum([two_body_potential(self, body) for body in bodies])
    
    # Remember that the cross product is anticommutative, do not swap pos and vel
    def angular_momentum(self):
        return self.mass * np.cross(self.pos, self.vel)