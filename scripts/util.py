from astroquery.jplhorizons import Horizons
import csv
import numpy as np

# Celestial bodies to query
sample_bodies = [
    {'name': 'Sun', 'id': '10', 'mass_kg': 1.989e30},
    {'name': 'Mercury', 'id': '199', 'mass_kg': 3.3011e23},
    {'name': 'Venus', 'id': '299', 'mass_kg': 4.8675e24},
    {'name': 'Earth', 'id': '399', 'moons': [{'name': 'Moon', 'id': '301', 'mass_kg': 7.34767309e22}], 'mass_kg': 5.972e24},
    {'name': 'Mars', 'id': '499', 'moons': [{'name': 'Phobos', 'id': '401', 'mass_kg': 1.0659e16}, {'name': 'Deimos', 'id': '402', 'mass_kg': 1.4762e15}], 'mass_kg': 6.4171e23},
    {'name': 'Jupiter', 'id': '599', 'moons': [{'name': 'Io', 'id': '501', 'mass_kg': 8.9319e22}, {'name': 'Europa', 'id': '502', 'mass_kg': 4.7998e22}, {'name': 'Ganymede', 'id': '503', 'mass_kg': 1.4819e23}, {'name': 'Callisto', 'id': '504', 'mass_kg': 1.0759e23}], 'mass_kg': 1.8982e27},
    {'name': 'Saturn', 'id': '699', 'moons': [{'name': 'Titan', 'id': '606', 'mass_kg': 1.3452e23}, {'name': 'Enceladus', 'id': '607', 'mass_kg': 1.08022e20}, {'name': 'Iapetus', 'id': '608', 'mass_kg': 1.805635e21}, {'name': 'Tethys', 'id': '609', 'mass_kg': 6.17449e20}], 'mass_kg': 5.6834e26},
    {'name': 'Uranus', 'id': '799', 'moons': [{'name': 'Miranda', 'id': '710', 'mass_kg': 6.59e19}, {'name': 'Ariel', 'id': '711', 'mass_kg': 1.353e21}, {'name': 'Titania', 'id': '712', 'mass_kg': 3.527e21}, {'name': 'Oberon', 'id': '713', 'mass_kg': 3.014e21}, {'name': 'Puck', 'id': '724', 'mass_kg': 2.9e17}], 'mass_kg': 8.6810e25},
    {'name': 'Neptune', 'id': '899', 'moons': [{'name': 'Triton', 'id': '801', 'mass_kg': 2.14e22}], 'mass_kg': 1.02413e26}
]

d_to_s = 86400
au_to_m = 149597870691

# Querries JPL Horizons for state vectors of celestial objects
def query_and_write_celestial_data(bodies): 
    with open('celestial_data.csv', 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(['Body', 'Date', 'X', 'Y', 'Z', 'VX', 'VY', 'VZ', 'Mass'])

        for body in bodies:
            obj = Horizons(id=body['id'], location='@0', epochs={'start':'2024-04-29', 'stop':'2024-04-30', 'step':'1d'})
            vecs = obj.vectors()
            
            row = [body['name'], vecs['datetime_jd'][0], vecs['x'][0], vecs['y'][0], vecs['z'][0], vecs['vx'][0], vecs['vy'][0], vecs['vz'][0], float(body['mass_kg'])]
            writer.writerow(row)

            if 'moons' in body:
                for moon in body['moons']:
                    moon_obj = Horizons(id=moon['id'], location='@0', epochs={'start':'2024-04-29', 'stop':'2024-04-30', 'step':'1d'})
                    moon_vecs = moon_obj.vectors()
                    row = [moon['name'], moon_vecs['datetime_jd'][0], moon_vecs['x'][0], moon_vecs['y'][0], moon_vecs['z'][0], moon_vecs['vx'][0], moon_vecs['vy'][0], moon_vecs['vz'][0], moon['mass_kg']]
                    writer.writerow(row)

    print("Data written to celestial_data.csv")

def read_celestial_data():
    result = []
    with open('celestial_data.csv', 'r') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            result.append(row)
    return result

def collect_masses(data):
    masses = []
    for body in data:
        masses.append(body['Mass'])
        
    return np.array(masses).astype(float)    

# Converts from au to m        
def collect_state_vectors(data, isAu = True):
    pos = []
    vel = []
    if(isAu):
        for body in data:
            pos.append(float(body['X']) * au_to_m)
            pos.append(float(body['Y']) * au_to_m)
            pos.append(float(body['Z']) * au_to_m)
            vel.append(float(body['VX']) * au_to_m / d_to_s)
            vel.append(float(body['VY']) * au_to_m / d_to_s)
            vel.append(float(body['VZ']) * au_to_m / d_to_s)
    else:
        for body in data:
            pos.append(body['X'])
            pos.append(body['Y'])
            pos.append(body['Z'])
            vel.append(body['VX'])
            vel.append(body['VY'])
            vel.append(body['VZ'])
    print(vel)    
    return np.concatenate((pos,vel)).astype(float)
    
