package com.cnebrera.uc3.tech.lesson3.subscriber;

import io.aeron.Aeron;
import io.aeron.Subscription;

public abstract class MySubscriber implements Runnable {

    private String channel;

    MySubscriber() {
        this.channel = "aeron:ipc";
    }

    MySubscriber(String channel) {
        this.channel = channel;
    }

    void execution() {
        Aeron.Context ctx = new Aeron.Context();
        int streamId = 2;

        try (Aeron connection = Aeron.connect(ctx)) {
            try (Subscription subscription = connection.addSubscription(channel, streamId)) {
                subscriberAction(subscription);
            }
        }
    }

    protected abstract void subscriberAction(Subscription subscription);

    @Override
    public void run() {
        this.execution();
    }
}
