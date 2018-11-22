package com.cnebrera.uc3.tech.lesson3.handler;

import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import org.agrona.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.Observable;

public class SenderHandler extends Observable implements FragmentHandler {
    private long nextOfferTime = 0;
    private long firstSend = 0;
    private long firstArrive = 0;
    private long secondSend = 0;
    private long secondArrive = 0;

    @Override
    public void onFragment(DirectBuffer directBuffer, int i, int i1, Header header) {
        this.secondArrive = System.nanoTime();

        ByteBuffer bb = directBuffer.byteBuffer();

        this.nextOfferTime = bb.getLong(0);
        this.firstSend = bb.getLong(10);
        this.firstArrive = bb.getLong(20);
        this.secondSend = bb.getLong(30);

        this.setChanged();
        this.notifyObservers();

        System.out.println("New timestamp received:");
        System.out.println("\tSending time: " + (firstArrive - firstSend) + "ns - Arriving time: " + (secondArrive - secondSend) + "ns");
        System.out.println("\tNext expected send: " + this.nextOfferTime);
    }

    public long getFirstSend() {
        return this.firstSend;
    }

    public long getFirstArrive() {
        return this.firstArrive;
    }

    public long getSecondSend() {
        return this.secondSend;
    }

    public long getSecondArrive() {
        return this.secondArrive;
    }

    public long getNextOfferTime() {
        return this.nextOfferTime;
    }

    public void reset() {
        this.firstSend = 0;
        this.firstArrive = 0;
        this.secondSend = 0;
        this.secondArrive = 0;
    }
}
