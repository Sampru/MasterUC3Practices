package com.cnebrera.uc3.tech.lesson3.subscriber;

import com.cnebrera.uc3.tech.lesson3.handler.LatencyHandler;
import com.cnebrera.uc3.tech.lesson3.implementables.MySubscriber;
import io.aeron.Subscription;
import io.aeron.logbuffer.FragmentHandler;
import org.agrona.concurrent.IdleStrategy;

public class LatencySubscriber extends MySubscriber {

    public static void main(String[] args) {
        LatencySubscriber ms = new LatencySubscriber();
        ms.execution();
    }

    @Override
    protected void insideLoopAction(Subscription subscription, IdleStrategy idleStrategy) {
        FragmentHandler fh = new LatencyHandler();

        int result = subscription.poll(fh, 1);
        idleStrategy.idle(result);

    }
}
