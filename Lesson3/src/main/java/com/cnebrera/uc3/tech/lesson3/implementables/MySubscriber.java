package com.cnebrera.uc3.tech.lesson3.implementables;

import io.aeron.Aeron;
import io.aeron.Subscription;

public abstract class MySubscriber {

    private String channel;

    public MySubscriber() {
        this.channel = "aeron:ipc";
    }

    public MySubscriber(String channel) {
        this.channel = channel;
    }

    public void execution() {
        Aeron.Context ctx = new Aeron.Context();
        int streamId = 2;

        try (Aeron connection = Aeron.connect(ctx)) {
            try (Subscription subscription = connection.addSubscription(channel, streamId)) {
                subscriberAction(subscription);
            }
        }
    }

    protected abstract void subscriberAction(Subscription subscription);
}
