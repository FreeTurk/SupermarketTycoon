package org.supermarkettycoon;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.AsyncEventBus;

import java.util.concurrent.Executors;

public class EventBusFactory {
    private static EventBus eventBus = new AsyncEventBus(Executors.newCachedThreadPool());

    public static EventBus getEventBus() {
        return eventBus;
    }
}
