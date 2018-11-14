package com.cnebrera.uc3.tech.lesson3.handler;

import io.aeron.logbuffer.FragmentHandler;
import io.aeron.logbuffer.Header;
import org.agrona.DirectBuffer;

import java.util.Observable;

public class ResponderHandler extends Observable implements FragmentHandler {
    private String msg = "";

    @Override
    public void onFragment(DirectBuffer directBuffer, int i, int i1, Header header) {
        byte[] bytes = new byte[i1];
        directBuffer.getBytes(i, bytes);
        String recivedMsg = new String(bytes);
        if (recivedMsg.startsWith("1$$")) {
            this.msg = recivedMsg.replace("1$$", "2$$") + "$" + System.nanoTime();
            this.setChanged();
            this.notifyObservers();

            System.out.println("New message received:");
            System.out.println("\t" + recivedMsg);
        }
    }

    public String getMsg() {
        return this.msg;
    }

}
