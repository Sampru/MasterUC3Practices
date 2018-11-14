package com.cnebrera.uc3.tech.lesson3.handler;

import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import org.agrona.DirectBuffer;

import java.util.Observable;

public class SenderHandler extends Observable implements FragmentHandler {
    private long nextOfferTime = 0;
    private long firstSend = 0;
    private long firstArrive = 0;
    private long secondSend = 0;
    private long secondArrive = 0;

    @Override
    public void onFragment(DirectBuffer directBuffer, int i, int i1, Header header) {
        String msg;
        byte[] bytes = new byte[i1];

        directBuffer.getBytes(i, bytes);
        msg = new String(bytes);

        if (msg.startsWith("2$$")) {
            this.secondArrive = System.nanoTime();
            this.setChanged();
            this.notifyObservers();

            String[] times = msg.replace("2$$", "").split("[$]");
            this.nextOfferTime = Long.valueOf(times[0]);
            this.firstSend = Long.valueOf(times[1]);
            this.firstArrive = Long.valueOf(times[2]);
            this.secondSend = Long.valueOf(times[3]);

            System.out.println("New timestamp received:");
            System.out.println("\tSending time: " + (firstArrive - firstSend) + "ns - Arriving time: " + (secondArrive - secondSend) + "ns");
            System.out.println("\tNext expected send: " + this.nextOfferTime);
        }
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
