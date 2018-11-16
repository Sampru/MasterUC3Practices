package com.cnebrera.uc3.tech.lesson3.publisher;

import io.aeron.Publication;


public class SenderPublisher extends MyPublisher {

    private long nextOfferTime;
    private int msgPerSec = 1000;

    public SenderPublisher() {
        super();
    }

    public SenderPublisher(String channel) {
        super(channel);
    }
    public SenderPublisher(int msgPerSec) {
        super();
        this.msgPerSec = msgPerSec;
    }
    public SenderPublisher(String channel, int msgPerSec) {
        super(channel);
        this.msgPerSec = msgPerSec;
    }

    @Override
    protected void publisherAction(Publication publication) throws InterruptedException {
        long expectedTime = Math.round(1000.0 / msgPerSec),
                result;
        int msgCount = 0;
        byte[] msgBytes = "".getBytes();
        boolean retry = false;

        nextOfferTime = System.nanoTime();

        while (msgCount < 1000) {

            while (nextOfferTime > System.nanoTime()) ;

            if (!retry) {
                msgBytes = generateMsg().getBytes();
                this.getBuffer().putBytes(0, msgBytes);
            }

            result = publication.offer(this.getBuffer(), 0, msgBytes.length);

            if (result >= 0) msgCount++;

            retry = analyzeResult(result);

            nextOfferTime += expectedTime;
        }

    }

    @Override
    protected String generateMsg() {
        return "1$$" + this.nextOfferTime + "$" + String.valueOf(System.nanoTime());
    }

}
