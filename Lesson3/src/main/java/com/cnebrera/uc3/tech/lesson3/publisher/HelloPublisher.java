package com.cnebrera.uc3.tech.lesson3.publisher;

import io.aeron.Aeron;
import io.aeron.BufferBuilder;
import io.aeron.Publication;
import org.agrona.MutableDirectBuffer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HelloPublisher {
    private final BufferBuilder bb = new BufferBuilder();

    public static void main(String[] args) {
        HelloPublisher mp = new HelloPublisher();
        mp.execution();
    }

    private void execution() {
        Aeron.Context ctx = new Aeron.Context();
        MutableDirectBuffer buffer = bb.buffer();
        boolean exit = false,
                retry = false;
        byte[] msgBytes = "".getBytes();
        int msgsPerSec = 1;
        long result,
                nextOfferTime = System.currentTimeMillis(),
                expectedTime = TimeUnit.SECONDS.toMillis(1 / msgsPerSec);

        try (Aeron connection = Aeron.connect(ctx)) {
            int streamId = 2;
            String channel = "aeron:udp?endpoint=224.0.1.1:40456";

            try (Publication publication = connection.addPublication(channel, streamId)) {
                while (!exit) {
                    while (nextOfferTime > System.currentTimeMillis()) ;

                    if (!retry) {
                        msgBytes = getRandomMsg().getBytes();
                        buffer.putBytes(0, msgBytes);
                    }
                    else retry = false;

                    result = publication.offer(buffer, 0, msgBytes.length);

                    if (result < 0) {
                        if (result == Publication.BACK_PRESSURED) {
                            System.err.println("Offer failed due to back pressure");
                            Thread.sleep(TimeUnit.MILLISECONDS.toMillis(100));
                            retry = true;
                        } else if (result == Publication.NOT_CONNECTED) {
                            System.err.println("Offer failed because publisher is not yet connected to subscriber");
                            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                        } else if (result == Publication.ADMIN_ACTION) {
                            System.err.println("Offer failed because of an administration action in the system");
                        } else if (result == Publication.CLOSED) {
                            System.err.println("Offer failed publication is closed");
                            exit = true;
                        } else {
                            System.err.println("Offer failed due to unknown reason");
                        }
                    } else {
                        System.out.println("Package sent");
                    }
                    nextOfferTime += expectedTime;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getRandomMsg() {
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
