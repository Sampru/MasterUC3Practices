package com.cnebrera.uc3.tech.lesson3.subscriber;

import com.cnebrera.uc3.tech.lesson3.handler.LatencySubscriberHandler;
import com.cnebrera.uc3.tech.lesson3.implementables.MyPublisher;
import com.cnebrera.uc3.tech.lesson3.implementables.MySubscriber;
import io.aeron.Publication;
import io.aeron.Subscription;
import org.agrona.concurrent.BusySpinIdleStrategy;
import org.agrona.concurrent.IdleStrategy;

public class LatencySubscriber extends MySubscriber {

    private ResponsePublisher rp;

    private LatencySubscriber() {
        super();
        rp = new ResponsePublisher();
    }

    private LatencySubscriber(String channel) {
        super(channel);
        rp = new ResponsePublisher(channel);
    }

    public static void main(String[] args) {
        LatencySubscriber ms = (args.length > 0) ? new LatencySubscriber(args[0]) : new LatencySubscriber();
        ms.execution();
    }

    @Override
    protected void subscriberAction(Subscription subscription) {
        LatencySubscriberHandler fh = new LatencySubscriberHandler();
        IdleStrategy idle = new BusySpinIdleStrategy();
        int result;
        while (true) {
            result = subscription.poll(fh, 1);
            if (result > 0) publishResponse(fh);
            idle.idle(result);
        }
    }

    private void publishResponse(LatencySubscriberHandler fh) {
        this.rp.setFirstSend(fh.getFirstSend());
        this.rp.setFirstArrive(fh.getFirstArrive());
        this.rp.execution();
    }

    private class ResponsePublisher extends MyPublisher {
        private long firstSend;
        private long firstArrive;

        private ResponsePublisher() {
            super();
        }

        private ResponsePublisher(String channel) {
            super(channel);
        }

        @Override
        protected void publisherAction(Publication publication) throws InterruptedException {
            long result = -1;
            byte[] msgBytes;

            while (result < 0) {

                Thread.sleep(100);
                msgBytes = generateMsg().getBytes();
                this.getBuffer().putBytes(0, msgBytes);

                result = publication.offer(this.getBuffer(), 0, msgBytes.length);
                analyzeResult(result);
            }

        }

        @Override
        protected String generateMsg() {
            return "2$$" + this.firstSend + "$" + this.firstArrive + "$" + System.nanoTime();
        }

        protected void setFirstSend(long firstSend) {
            this.firstSend = firstSend;
        }

        protected void setFirstArrive(long firstArrive) {
            this.firstArrive = firstArrive;
        }
    }
}
