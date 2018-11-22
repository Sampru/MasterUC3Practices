package com.cnebrera.uc3.tech.lesson3.publisher;

import io.aeron.Publication;


public class SenderPublisher extends MyPublisher {

    private long nextOfferTime;
    private int msgPerSec = 600000;

    public SenderPublisher() {
        super(2);
    }

    public SenderPublisher(String channel) {
        super(channel, 2);
    }

    public SenderPublisher(int msgPerSec) {
        super(2);
        this.msgPerSec = msgPerSec;
    }

    public SenderPublisher(String channel, int msgPerSec) {
        super(channel, 2);
        this.msgPerSec = msgPerSec;
    }

    @Override
    protected void publisherAction(Publication publication) throws InterruptedException {
        long expectedTime = Math.round(1000.0 / msgPerSec),
                result;
        int msgCount = 0;
        boolean retry = false;

        nextOfferTime = System.nanoTime();

        while (msgCount < 1000) {

            while (nextOfferTime > System.nanoTime()) ;

            if (!retry) {
                generateMsg();
                this.directBuffer.wrap(this.buffer);
            }

            result = publication.offer(this.directBuffer, 0, this.buffer.limit());

            if (result >= 0) msgCount++;

            retry = analyzeResult(result);

            nextOfferTime += expectedTime;
        }

    }

    @Override
    protected void generateMsg() {
        this.buffer.putLong(8, this.nextOfferTime);
        this.buffer.putLong(16, System.nanoTime());
    }

}
