package com.cnebrera.uc3.tech.lesson3.implementables;

import io.aeron.Aeron;
import io.aeron.BufferBuilder;
import io.aeron.Publication;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;

import java.util.concurrent.TimeUnit;

public abstract class MyPublisher {

    private final int MSG_PER_SEC = 100000;

    public void execution() {
        Aeron.Context ctx = new Aeron.Context();
        BufferBuilder bb = new BufferBuilder();
        MutableDirectBuffer buffer = bb.buffer();
        String channel = "aeron:ipc";
        long result,
                nextOfferTime = System.currentTimeMillis(),
                expectedTime = TimeUnit.MILLISECONDS.toMillis(Math.round(1000.0 / MSG_PER_SEC));
        int streamId = 2;
        byte[] msgBytes = "".getBytes();
        boolean retry = false;

        try (Aeron connection = Aeron.connect(ctx)) {


            try (Publication publication = connection.addPublication(channel, streamId)) {
                while (true) {

                    while (nextOfferTime > System.currentTimeMillis()) ;

                    if (!retry) {
                        msgBytes = generateMsg().getBytes();
                        buffer.putBytes(0, msgBytes);
                    }

                    result = insideLoopAction(publication, buffer, msgBytes.length);

                    retry = analyzeResult(result);

                    nextOfferTime += expectedTime;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected abstract long insideLoopAction(Publication publication, DirectBuffer buffer, int size);

    protected abstract String generateMsg();

    private boolean analyzeResult(long result) throws InterruptedException {
        boolean retry = false;
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
            } else {
                System.err.println("Offer failed due to unknown reason");
            }
        } else {
            System.out.println("Package sent");
        }
        return retry;
    }

}
