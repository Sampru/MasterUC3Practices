package com.cnebrera.uc3.tech.lesson3.publisher;

import com.cnebrera.uc3.tech.lesson3.roadtrip.Responder;
import io.aeron.Publication;

public class ResponderPublisher extends MyPublisher {
    private Responder responder;

    public ResponderPublisher(Responder responder) {
        super();
        this.responder = responder;
    }

    public ResponderPublisher(Responder responder, String channel) {
        super(channel);
        this.responder = responder;
    }

    @Override
    protected void publisherAction(Publication publication) throws InterruptedException {
        long result;
        boolean retry = false;
        byte[] msgBytes = "".getBytes();

        while (true) {

            if (!retry) {
                msgBytes = generateMsg().getBytes();
                this.getBuffer().putBytes(0, msgBytes);
            }

            result = publication.offer(this.getBuffer(), 0, msgBytes.length);
            retry = analyzeResult(result);
        }

    }

    @Override
    protected String generateMsg() {
        String msg = "";

        while (msg.isEmpty()) {
            msg = responder.readNextMessage();

            if (msg.equals("")){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return msg + "$" + System.nanoTime();
    }
}
