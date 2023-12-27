package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.rocket.factory;

import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.rocket.Rocket;

public class RocketFactory {

    public Rocket createRocket(int id, String name) {
        Rocket leRocket = new Rocket(id, name);
        return leRocket;
    }
}
