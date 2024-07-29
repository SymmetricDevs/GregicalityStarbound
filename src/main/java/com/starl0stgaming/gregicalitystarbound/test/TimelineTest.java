package com.starl0stgaming.gregicalitystarbound.test;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;
import com.starl0stgaming.gregicalitystarbound.api.space.timeline.Timeline;

public class TimelineTest {

    public void run() {
        Timeline<TestHandle> timeline = new Timeline<>();
        System.out.println("test timeline");
        timeline.registerEvent(2000, (handle) -> {
            handle.launch();
            System.out.println("successfully launched handle " + timeline.getCurrentTime());

        });

        //timeline.registerEvent(3000, TestHandle::launch); <- alternative way of registering a callback

        timeline.registerEvent(3500, (handle) -> {
            System.out.println("hello");
            System.out.println(timeline.getCurrentTime());
        });


        timeline.registerEvent(10000, (handle) -> {
            handle.explode();
            System.out.println("successfully exploded handle " + timeline.getCurrentTime());
        });

        TestHandle handle = new TestHandle();
        timeline.start(handle);


    }

    private class TestHandle {

        public void launch() {
            System.out.println("launched test handle!");
        }

        public void explode() {
            System.out.println("exploded test handle :(");
        }
    }
}
