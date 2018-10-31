package com.cnebrera.uc3.tech.lesson3.subscriber;

import io.aeron.Aeron;
import io.aeron.Subscription;
import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import org.agrona.DirectBuffer;
import org.agrona.concurrent.BusySpinIdleStrategy;
import org.agrona.concurrent.IdleStrategy;


public class HelloSubscriber {

    public static void main(String[] args) {
        HelloSubscriber ms = new HelloSubscriber();
        ms.execution();
    }

    private void execution() {
        Aeron.Context ctx = new Aeron.Context();
        FragmentHandler fh = new MyHandler();
        IdleStrategy idleStrategy = new BusySpinIdleStrategy();

        try (Aeron connection = Aeron.connect(ctx)) {
            int streamId = 2;
            String channel = "aeron:udp?endpoint=224.0.1.1:40456";

            try (Subscription subscription = connection.addSubscription(channel, streamId)) {
                while (true) {
                    int result = subscription.poll(fh, 1);

                    idleStrategy.idle(result);
                }
            }
        }
    }

    private class MyHandler implements FragmentHandler {

        @Override
        public void onFragment(DirectBuffer directBuffer, int i, int i1, Header header) {
            byte[] bytes = new byte[i1];
            directBuffer.getBytes(i, bytes);

            System.out.println("New message received:");
            System.out.println("\t" + new String(bytes));
        }
    }
}
