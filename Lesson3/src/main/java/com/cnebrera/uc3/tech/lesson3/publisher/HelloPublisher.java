package com.cnebrera.uc3.tech.lesson3.publisher;

import com.cnebrera.uc3.tech.lesson3.implementables.MyPublisher;
import io.aeron.Publication;

import java.util.Random;

public class HelloPublisher extends MyPublisher {

    private HelloPublisher() {
        super();
    }

    private HelloPublisher(String channel) {
        super(channel);
    }

    public static void main(String[] args) {
        HelloPublisher hp = (args.length > 0) ? new HelloPublisher(args[0]) : new HelloPublisher();
        hp.execution();
    }

    @Override
    protected void publisherAction(Publication publication) throws InterruptedException {
        long nextOfferTime = System.currentTimeMillis(),
                expectedTime = Math.round(1000.0 / MSG_PER_SEC);
        byte[] msgBytes = "".getBytes();
        boolean retry = false;

        while (true) {

            while (nextOfferTime > System.currentTimeMillis()) ;

            if (!retry) {
                msgBytes = generateMsg().getBytes();
                this.getBuffer().putBytes(0, msgBytes);
            }

            retry = analyzeResult(publication.offer(this.getBuffer(), 0, msgBytes.length));

            nextOfferTime += expectedTime;
        }
    }

    @Override
    protected String generateMsg() {
        String[] msgs = {
                "Hello World!",
                "¡Hola Mundo!",
                "Kaixo Mundu!",
                "Hola Món!",
                "Ola Mundo!"
        };
        Random rnd = new Random();

        return msgs[rnd.nextInt(msgs.length)];
    }
}