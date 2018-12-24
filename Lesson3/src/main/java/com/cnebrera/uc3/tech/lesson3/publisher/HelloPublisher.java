package com.cnebrera.uc3.tech.lesson3.publisher;

import io.aeron.Publication;

import java.nio.ByteBuffer;
import java.util.Random;

public class HelloPublisher extends MyPublisher {

    private Random rnd = new Random();

    private HelloPublisher() {
        super(2);
    }

    private HelloPublisher(String channel) {
        super(channel, 2);
    }

    public static void main(String[] args) {
        HelloPublisher hp = (args.length > 0) ? new HelloPublisher(args[0]) : new HelloPublisher();
        hp.execution();
    }

    @Override
    protected void publisherAction(Publication publication) throws InterruptedException {
        long nextOfferTime = System.currentTimeMillis(),
                expectedTime = Math.round(1000.0 / MSG_PER_SEC),
                result;
        boolean retry = false;

        while (true) {

            while (nextOfferTime > System.currentTimeMillis()) ;

            if (!retry) {
                generateMsg();
            }

            result = publication.offer(this.directBuffer, 0, this.directBuffer.capacity());

            retry = analyzeResult(result);

            nextOfferTime += expectedTime;
        }
    }

    @Override
    protected void generateMsg() {
        String[] msgs = {
                "Hello World!",
                "¡Hola Mundo!",
                "Kaixo Mundu!",
                "Hola Món!",
                "Ola Mundo!"
        };

        byte[] msg = msgs[rnd.nextInt(msgs.length)].getBytes();
        this.directBuffer = this.bb.buffer();
        this.directBuffer.putBytes(0, msg);
    }
}