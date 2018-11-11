package com.cnebrera.uc3.tech.lesson3.subscriber;

import com.cnebrera.uc3.tech.lesson3.handler.HelloHandler;
import com.cnebrera.uc3.tech.lesson3.implementables.MySubscriber;
import io.aeron.Subscription;
import io.aeron.logbuffer.FragmentHandler;
import org.agrona.concurrent.BusySpinIdleStrategy;
import org.agrona.concurrent.IdleStrategy;


public class HelloSubscriber extends MySubscriber {

    private HelloSubscriber() {
        super();
    }

    private HelloSubscriber(String channel) {
        super(channel);
    }

    public static void main(String[] args) {
        HelloSubscriber hs = (args.length > 0) ? new HelloSubscriber(args[0]) : new HelloSubscriber();
        hs.execution();
    }

    @Override
    protected void subscriberAction(Subscription subscription) {
        IdleStrategy idle = new BusySpinIdleStrategy();
        FragmentHandler fh = new HelloHandler();
        while (true) {

            int result = subscription.poll(fh, 1);
            idle.idle(result);
        }
    }
}
