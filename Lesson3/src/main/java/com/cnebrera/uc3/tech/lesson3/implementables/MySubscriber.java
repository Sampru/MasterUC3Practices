package com.cnebrera.uc3.tech.lesson3.implementables;

import io.aeron.Aeron;
import io.aeron.Subscription;
import org.agrona.concurrent.BusySpinIdleStrategy;
import org.agrona.concurrent.IdleStrategy;

public abstract class MySubscriber {

    public void execution() {
        Aeron.Context ctx = new Aeron.Context();
        IdleStrategy idle = new BusySpinIdleStrategy();
        String channel = "aeron:ipc";
        int streamId = 2;

        try (Aeron connection = Aeron.connect(ctx)) {

            try (Subscription subscription = connection.addSubscription(channel, streamId)) {
                while (true) {
                    insideLoopAction(subscription, idle);
                }
            }
        }
    }

    protected abstract void insideLoopAction(Subscription subscription, IdleStrategy idleStrategy);
}
