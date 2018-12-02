package com.cnebrera.uc3.tech.lesson3.publisher;

import com.cnebrera.uc3.tech.lesson3.roadtrip.Responder;
import io.aeron.Publication;
import org.agrona.MutableDirectBuffer;

public class ResponderPublisher extends MyPublisher {
    private Responder responder;

    public ResponderPublisher(Responder responder) {
        super(3);
        this.responder = responder;
    }

    public ResponderPublisher(Responder responder, String channel) {
        super(channel, 3);
        this.responder = responder;
    }

    @Override
    protected void publisherAction(Publication publication) throws InterruptedException {
        long result;
        boolean retry = false;

        while (true) {

            if (!retry) {
                generateMsg();
            }

            result = publication.offer(this.directBuffer, 0, this.directBuffer.capacity());
            retry = analyzeResult(result);
        }

    }

    @Override
    protected void generateMsg() {
        this.directBuffer = null;

        while (this.directBuffer == null) {
            this.directBuffer = (MutableDirectBuffer) responder.readNextMessage();

            if (this.directBuffer == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        this.directBuffer.putLong(32, System.nanoTime());
    }
}
