package com.cnebrera.uc3.tech.lesson3.handler;

import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import org.agrona.DirectBuffer;

public class LatencyHandler implements FragmentHandler {

    @Override
    public void onFragment(DirectBuffer directBuffer, int i, int i1, Header header) {
        byte[] bytes = new byte[i1];
        directBuffer.getBytes(i, bytes);

        System.out.println("New message received:");
        System.out.println("\t" + new String(bytes));
    }
}
