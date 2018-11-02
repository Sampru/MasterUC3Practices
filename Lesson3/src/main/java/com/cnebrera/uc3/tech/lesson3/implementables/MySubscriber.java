package com.cnebrera.uc3.tech.lesson3.implementables;

import io.aeron.Aeron;
import io.aeron.Subscription;

public abstract class MySubscriber {

    public void execution() {
        Aeron.Context ctx = new Aeron.Context();

        try (Aeron connection = Aeron.connect(ctx)) {
            int streamId = 2;
            String channel = "aeron:udp?endpoint=224.0.1.1:40456";

            try (Subscription subscription = connection.addSubscription(channel, streamId)) {
                while (true) {
                    insideLoopAction(subscription);
                }
            }
        }
    }

    protected abstract void insideLoopAction(Subscription subscription );
}
