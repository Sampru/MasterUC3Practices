package com.cnebrera.uc3.tech.lesson3.handler;

import io.aeron.BufferBuilder;
import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;

import java.util.Observable;

public class ResponderHandler extends Observable implements FragmentHandler {
    private final BufferBuilder bb = new BufferBuilder();
    private DirectBuffer directBuffer = bb.buffer();

    @Override
    public void onFragment(DirectBuffer directBuffer, int i, int i1, Header header) {
        ((MutableDirectBuffer) directBuffer).putLong(24, System.nanoTime());
        this.directBuffer = directBuffer;

        System.out.println("next time: " + this.directBuffer.getLong(8));
        System.out.println("sent time: " + this.directBuffer.getLong(16));
        System.out.println("arri time: " + this.directBuffer.getLong(24));

        this.setChanged();
        this.notifyObservers();
    }

    public DirectBuffer getMsg() {
        return this.directBuffer;
    }

}
