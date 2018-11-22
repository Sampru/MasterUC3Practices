package com.cnebrera.uc3.tech.lesson3.handler;

import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import org.agrona.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.Observable;

public class ResponderHandler extends Observable implements FragmentHandler {
    private ByteBuffer bb = ByteBuffer.allocate(128);

    @Override
    public void onFragment(DirectBuffer directBuffer, int i, int i1, Header header) {
        ByteBuffer rec = directBuffer.byteBuffer();

        this.bb.putLong(24, System.nanoTime());
        this.bb.putLong(8, rec.getLong(8));
        this.bb.putLong(16, rec.getLong(16));

        System.out.println("next time: " + this.bb.getLong(8));
        System.out.println("sent time: " + this.bb.getLong(16));
        System.out.println("arri time: " + this.bb.getLong(24));

        this.setChanged();
        this.notifyObservers();
    }

    public ByteBuffer getMsg() {
        return this.bb;
    }

}
