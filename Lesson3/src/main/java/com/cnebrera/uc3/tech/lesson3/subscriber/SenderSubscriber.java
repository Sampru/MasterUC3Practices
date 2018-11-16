package com.cnebrera.uc3.tech.lesson3.subscriber;

import com.cnebrera.uc3.tech.lesson3.handler.SenderHandler;
import io.aeron.Subscription;
import org.HdrHistogram.Histogram;
import org.agrona.concurrent.BusySpinIdleStrategy;
import org.agrona.concurrent.IdleStrategy;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

public class SenderSubscriber extends MySubscriber implements Observer {
    private Histogram hg, hgCumul;
    private SenderHandler lph;
    private static long LOWEST = TimeUnit.NANOSECONDS.toNanos(600000);   /* Minimum registered value */
    private static long HIGHEST = TimeUnit.MILLISECONDS.toNanos(11000); /* Maximum registered value */
    private static double SCALE = 1; /* Scale from ns to ms*/
    private static int SIGNIF = 2; /* Significance, 2 will allow to have pretty accurate results */

    public SenderSubscriber() {
        super();
        this.hg = new Histogram(LOWEST, HIGHEST, SIGNIF);
        this.hg.setAutoResize(true);
        this.hgCumul = new Histogram(LOWEST, HIGHEST, SIGNIF);
        this.hgCumul.setAutoResize(true);
        this.lph = new SenderHandler();
        this.lph.addObserver(this);

    }

    public SenderSubscriber(String channel) {
        super(channel);
        this.hg = new Histogram(LOWEST, HIGHEST, SIGNIF);
        this.hg.setAutoResize(true);
        this.hgCumul = new Histogram(LOWEST, HIGHEST, SIGNIF);
        this.hgCumul.setAutoResize(true);
        this.lph = new SenderHandler();
        this.lph.addObserver(this);
    }

    @Override
    protected void subscriberAction(Subscription subscription) {
        IdleStrategy idle = new BusySpinIdleStrategy();

        while (hg.getTotalCount() < 1000) {
            int result = subscription.poll(this.lph, 1);
            idle.idle(result);
        }

        hg.outputPercentileDistribution(System.out, SCALE);
        hgCumul.outputPercentileDistribution(System.out, SCALE);
    }

    @Override
    public void update(Observable observable, Object o) {
        long total = this.lph.getFirstArrive() - this.lph.getFirstSend() + this.lph.getSecondArrive() - this.lph.getSecondSend();
        long totalCumul = total + (this.lph.getFirstSend() > this.lph.getNextOfferTime() ? this.lph.getFirstSend() - this.lph.getNextOfferTime() : 0);

        this.hg.recordValue(total);
        this.hgCumul.recordValue(totalCumul);
        this.lph.reset();
    }
}