package com.starl0stgaming.gregicalitystarbound.api.space.timeline;

import org.jetbrains.annotations.NotNull;

import java.util.PriorityQueue;

public class Timeline<T> {
    private final PriorityQueue<TimelineEvent<T>> eventQueue = new PriorityQueue<>();

    /**
     * Runs the timeline at the specified time.
     * Any events scheduled to happen before the current time that have not run yet are run.
     */

    public void run(T handle, long currentTime) {
        while (!eventQueue.isEmpty() && eventQueue.peek().time <= currentTime) {
            TimelineEvent<T> event = eventQueue.poll();
            if (event != null && event.time == currentTime) {
                event.task.execute(handle);
            }
        }
    }

    /**
     * Registers an event to be run on the specified time
     *
     * @param time The time to run the event at.
     * @param task The task to do, can be a method lambda or a static method
     *             reference (look at {@link TimelineTest} for examples)
     */
    public void registerEvent(long time, TimelineTask<T> task) {
        synchronized (eventQueue) {
            eventQueue.add(new TimelineEvent<>(time, task));
        }
    }

    private static class TimelineEvent<T> implements Comparable<TimelineEvent<T>> {
        long time;
        TimelineTask<T> task;

        TimelineEvent(long time, TimelineTask<T> task) {
            this.time = time;
            this.task = task;
        }

        @Override
        public int compareTo(@NotNull Timeline.TimelineEvent<T> o) {
            return Long.compare(this.time, o.time);
        }
    }
}
