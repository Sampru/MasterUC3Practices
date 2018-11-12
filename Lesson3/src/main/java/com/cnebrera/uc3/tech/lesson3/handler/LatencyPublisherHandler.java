package com.cnebrera.uc3.tech.lesson3.handler;

import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import org.agrona.DirectBuffer;

public class LatencyPublisherHandler implements FragmentHandler {
    private long firstSend = 0;
    private long firstArrive = 0;
    private long secondSend = 0;
    private long secondArrive = 0;
    private boolean filled = false;

    @Override
    public void onFragment(DirectBuffer directBuffer, int i, int i1, Header header) {
        System.out.println("Fragment read");
        byte[] bytes = new byte[i1];
        directBuffer.getBytes(i, bytes);
        String msg = new String(bytes);
        if (msg.startsWith("2$")) {
            this.filled = true;
            secondArrive = System.nanoTime();
            System.out.println(msg);
            String[] times = msg.replace("2$$", "").split("[$]");
            firstSend = Long.valueOf(times[0]);
            firstArrive = Long.valueOf(times[1]);
            secondSend = Long.valueOf(times[2]);
            System.out.println("New timestamp received:");
            System.out.println("\tSending time: " + (firstArrive - firstSend) + "ns - Arriving time: " + (secondArrive - secondSend) + "ns");
        }
    }

    public long getFirstSend() {
        return firstSend;
    }

    public long getFirstArrive() {
        return firstArrive;
    }

    public long getSecondSend() {
        return secondSend;
    }

    public long getSecondArrive() {
        return secondArrive;
    }

    public boolean isFilled() {
        return filled;
    }

    public void reset() {
        firstSend = 0;
        firstArrive = 0;
        secondSend = 0;
        secondArrive = 0;
        filled = false;
    }
}
