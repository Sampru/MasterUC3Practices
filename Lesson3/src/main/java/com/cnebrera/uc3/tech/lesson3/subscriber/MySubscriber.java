package com.cnebrera.uc3.tech.lesson3.subscriber;

import io.aeron.Aeron;
import io.aeron.Subscription;

public abstract class MySubscriber implements Runnable {

    private String channel;
    private int streamId;

    MySubscriber(int streamId) {
        this.channel = "aeron:ipc";
        this.streamId = streamId;
    }

    MySubscriber(String channel, int streamId) {
        this.channel = channel;
        this.streamId = streamId;
    }

    void execution() {
        Aeron.Context ctx = new Aeron.Context();

        try (Aeron connection = Aeron.connect(ctx)) {
            try (Subscription subscription = connection.addSubscription(this.channel, this.streamId)) {
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
