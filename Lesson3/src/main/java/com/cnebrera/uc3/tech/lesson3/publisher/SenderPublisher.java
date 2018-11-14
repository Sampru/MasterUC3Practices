package com.cnebrera.uc3.tech.lesson3.publisher;

import io.aeron.Publication;


public class SenderPublisher extends MyPublisher {

    private long nextOfferTime;

    public SenderPublisher() {
        super();
    }

    public SenderPublisher(String channel) {
        super(channel);
    }

    @Override
    protected void publisherAction(Publication publication) throws InterruptedException {
        long expectedTime = Math.round(1000.0 / MSG_PER_SEC),
                result;
        int msgCount = 0;
        byte[] msgBytes = "".getBytes();
        boolean retry = false;

        nextOfferTime = System.nanoTime();

        while (msgCount < 100) {

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
