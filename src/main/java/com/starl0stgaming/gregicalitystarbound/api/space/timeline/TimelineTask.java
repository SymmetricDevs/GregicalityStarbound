package com.starl0stgaming.gregicalitystarbound.api.space.timeline;

@FunctionalInterface
public interface TimelineTask<T> {
    void execute(T handle);
}
