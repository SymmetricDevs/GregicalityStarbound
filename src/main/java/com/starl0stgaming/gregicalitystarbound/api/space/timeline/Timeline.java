package com.starl0stgaming.gregicalitystarbound.api.space.timeline;

import com.starl0stgaming.gregicalitystarbound.test.TimelineTest;
import org.jetbrains.annotations.NotNull;

import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timeline<T> {
    private final PriorityQueue<TimelineEvent<T>> eventQueue = new PriorityQueue<>();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private volatile boolean running = false;

    private long currentTime;
    private long startTime;


    public Timeline() {

    }

    /**
     * Returns the current time of the timeline
     * @return Current time of the running timeline, will be null if it has not been started.
     */
    public long getCurrentTime() {
        return currentTime;
    }

    private void run(T handle, long startTime) {
        this.currentTime = System.currentTimeMillis() - startTime;
        synchronized (eventQueue) {
            while (!eventQueue.isEmpty() && eventQueue.peek().time <= currentTime) {
                TimelineEvent<T> event = eventQueue.poll();
                if(event != null) {
                    event.task.execute(handle);
                }
            }
            if (eventQueue.isEmpty()) {
                stop();
            }
        }
    }

    /**
     * Registers an event to be run on the specified time
     * @param time The time to run the event at.
     * @param task The task to do, can be a method lambda or a static method
     *            reference (look at {@link TimelineTest} for examples)
     */
    public void registerEvent(long time, TimelineTask<T> task) {
        synchronized (eventQueue) {
            eventQueue.add(new TimelineEvent<>(time, task));
        }
    }

    /**
     * Starts the timeline
     * @param handle The handle to pass to the method lambda in {@code registerEvent()}
     *               (Look at {@link TimelineTest} for examples)
     */
    public void start(T handle) {
        if(!running) {
            running = true;
            this.startTime = System.currentTimeMillis();
            executor.scheduleAtFixedRate(() -> run(handle, this.startTime), 0, 1, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Stops the timeline no matter what.
     */
    public void stop() {
        running = false;
        executor.shutdown();
        try {
            if(!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public boolean isRunning() {
        return this.running;
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
