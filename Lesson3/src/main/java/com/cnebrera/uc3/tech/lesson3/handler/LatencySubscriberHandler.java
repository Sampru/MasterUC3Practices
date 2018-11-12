package com.cnebrera.uc3.tech.lesson3.handler;

import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import org.agrona.DirectBuffer;

public class LatencySubscriberHandler implements FragmentHandler {
    private long firstSend = 0;
    private long firstArrive = 0;

    @Override
    public void onFragment(DirectBuffer directBuffer, int i, int i1, Header header) {
        byte[] bytes = new byte[i1];
        directBuffer.getBytes(i, bytes);
        String msg = new String(bytes);
        if (msg.startsWith("1$")) {
            firstArrive = System.nanoTime();
            firstSend = Long.valueOf(msg.replace("1$$", ""));
            System.out.println("New timestamp received:");
            System.out.println("\t" + firstSend);
        }
    }

    public long getFirstSend() {
        return firstSend;
    }

    public long getFirstArrive() {
        return firstArrive;
    }

}
