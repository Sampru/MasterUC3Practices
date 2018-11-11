package com.cnebrera.uc3.tech.lesson3.implementables;

import io.aeron.Aeron;
import io.aeron.BufferBuilder;
import io.aeron.Publication;
import org.agrona.MutableDirectBuffer;

import java.util.concurrent.TimeUnit;

public abstract class MyPublisher {

    protected final int MSG_PER_SEC = 100000;
    private MutableDirectBuffer buffer;
    private String channel;
    private final BufferBuilder bb = new BufferBuilder();

    public MyPublisher() {
        this.channel = "aeron:ipc";
        buffer = bb.buffer();
    }

    public MyPublisher(String channel) {
        this.channel = channel;
        buffer = bb.buffer();
    }

    public void execution() {
        Aeron.Context ctx = new Aeron.Context();
        int streamId = 2;

        try (Aeron connection = Aeron.connect(ctx)) {
            try (Publication publication = connection.addPublication(channel, streamId)) {
                publisherAction(publication);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void publisherAction(Publication publication) throws InterruptedException;

    protected abstract String generateMsg();

    protected boolean analyzeResult(long result) throws InterruptedException {
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

    protected MutableDirectBuffer getBuffer() {
        return this.buffer;
    }

}
