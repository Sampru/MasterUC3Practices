package com.cnebrera.uc3.tech.lesson3.publisher;

import com.cnebrera.uc3.tech.lesson3.implementables.MyPublisher;
import io.aeron.Publication;
import org.agrona.DirectBuffer;

import java.util.Random;

public class HelloPublisher extends MyPublisher {

    public static void main(String[] args) {
        HelloPublisher mp = new HelloPublisher();
        mp.execution();
    }

    @Override
    protected long insideLoopAction(Publication publication, DirectBuffer buffer, int size) {
        return publication.offer(buffer, 0, size);
    }

    @Override
    protected String getMsg() {
        String[] msgs = {
                "Hello World!",
                "¡Hola Mundo!",
                "Kaixo Mundu!",
                "Hola Món!",
                "Ola Mundo!"
        };
        Random rnd = new Random();

        return msgs[rnd.nextInt(msgs.length)];
    }
}