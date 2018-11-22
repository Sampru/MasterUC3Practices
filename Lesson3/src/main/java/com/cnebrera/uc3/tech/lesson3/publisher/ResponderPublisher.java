package com.cnebrera.uc3.tech.lesson3.publisher;

import com.cnebrera.uc3.tech.lesson3.roadtrip.Responder;
import io.aeron.Publication;

import java.nio.ByteBuffer;

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
                this.directBuffer.wrap(this.buffer);
            }

            result = publication.offer(this.directBuffer, 0, this.buffer.limit());
            retry = analyzeResult(result);
        }

    }

    @Override
    protected void generateMsg() {
        ByteBuffer msg = null;

        while (msg == null) {
            msg = responder.readNextMessage();

            if (msg == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        this.buffer.put(msg);
        this.buffer.putLong(32, System.nanoTime());
    }
}
