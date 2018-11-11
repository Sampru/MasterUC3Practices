package com.cnebrera.uc3.tech.lesson3.publisher;

import com.cnebrera.uc3.tech.lesson3.handler.LatencyPublisherHandler;
import com.cnebrera.uc3.tech.lesson3.implementables.MyPublisher;
import com.cnebrera.uc3.tech.lesson3.implementables.MySubscriber;
import io.aeron.Publication;
import io.aeron.Subscription;
import org.HdrHistogram.Histogram;
import org.agrona.concurrent.BusySpinIdleStrategy;
import org.agrona.concurrent.IdleStrategy;

import java.util.concurrent.TimeUnit;


public class LatencyPublisher extends MyPublisher {
    private static long LOWEST = TimeUnit.NANOSECONDS.toNanos(120);   /* Minimum registered value */
    private static long HIGHEST = TimeUnit.MICROSECONDS.toNanos(4200); /* Maximum registered value */
    private static int SIGNIF = 2; /* Significance, 2 will allow to have pretty accurate results */
    private static double SCALE = 1; /* Scale from ns to ms*/
    private Histogram hg, hgCumul;
    private ResponseSubscriber rs;

    private LatencyPublisher() {
        super();
        this.hg = new Histogram(LOWEST, HIGHEST, SIGNIF);
        this.hg.setAutoResize(true);
        this.rs = new ResponseSubscriber();
    }

    private LatencyPublisher(String channel) {
        super(channel);
        this.hg = new Histogram(LOWEST, HIGHEST, SIGNIF);
        this.hg.setAutoResize(true);
        this.rs = new ResponseSubscriber(channel);
    }

    public static void main(String[] args) {
        LatencyPublisher lp = (args.length > 0) ? new LatencyPublisher(args[0]) : new LatencyPublisher();
        lp.execution();
    }

    @Override
    protected void publisherAction(Publication publication) throws InterruptedException {
        long nextOfferTime = System.nanoTime(),
                expectedTime = Math.round(1000.0 / MSG_PER_SEC),
                result;
        int records = 0;
        byte[] msgBytes = "".getBytes();
        boolean retry = false;

        while (records < 100) {

            while (nextOfferTime > System.nanoTime()) ;

            if (!retry) {
                msgBytes = generateMsg().getBytes();
                this.getBuffer().putBytes(0, msgBytes);
            }

            result = publication.offer(this.getBuffer(), 0, msgBytes.length);
            if (result >= 0) {
                this.rs.execution();
                storeTime(nextOfferTime);
                records++;
            }
            retry = analyzeResult(result);

            nextOfferTime += expectedTime;
        }

        hg.outputPercentileDistribution(System.out, SCALE);
        hgCumul.outputPercentileDistribution(System.out, SCALE);
    }

    @Override
    protected String generateMsg() {
        return "1$" + String.valueOf(System.nanoTime());
    }

    private void storeTime(long nextOfferTime) {
        LatencyPublisherHandler lh = this.rs.getLph();
        long total = lh.getFirstArrive() - lh.getFirstSend() + lh.getSecondArrive() - lh.getSecondSend();
        long totalCumul = total + (lh.getFirstSend() > nextOfferTime ? lh.getFirstSend() - nextOfferTime : 0);

        hg.recordValue(total);
        hgCumul.recordValue(totalCumul);
    }

    private class ResponseSubscriber extends MySubscriber {
        private LatencyPublisherHandler lph;

        private ResponseSubscriber() {
            super();
            this.lph = new LatencyPublisherHandler();

        }

        private ResponseSubscriber(String channel) {
            super(channel);
            this.lph = new LatencyPublisherHandler();
        }

        @Override
        protected void subscriberAction(Subscription subscription) {
            IdleStrategy idle = new BusySpinIdleStrategy();
            while (this.lph.isFilled()) {
                int result = subscription.poll(this.lph, 1);
                idle.idle(result);
            }
        }

        LatencyPublisherHandler getLph() {
            return lph;
        }

    }
}
