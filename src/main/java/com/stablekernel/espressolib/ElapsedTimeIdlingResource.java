package com.stablekernel.espressolib;

import android.support.test.espresso.IdlingResource;

import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;

public class ElapsedTimeIdlingResource implements IdlingResource {
    private final long startTime;
    private final long waitingTime;
    private ResourceCallback resourceCallback;

    public ElapsedTimeIdlingResource(long durationMillis) {
        this.startTime = System.currentTimeMillis();
        this.waitingTime = durationMillis;
    }

    @Override
    public String getName() {
        return ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
    }

    @Override
    public boolean isIdleNow() {
        long elapsed = System.currentTimeMillis() - startTime;
        boolean idle = (elapsed >= waitingTime);
        if (idle) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(IdlingResource.ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }

    public static void waitAndRun(long millis, Runnable runnable) {
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(millis);
        registerIdlingResources(idlingResource);

        runnable.run();

        unregisterIdlingResources(idlingResource);
    }
}

