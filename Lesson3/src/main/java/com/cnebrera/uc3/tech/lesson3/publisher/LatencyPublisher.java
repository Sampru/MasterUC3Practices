package com.cnebrera.uc3.tech.lesson3.publisher;

import com.cnebrera.uc3.tech.lesson3.implementables.MyPublisher;
import io.aeron.Publication;
import org.HdrHistogram.Histogram;
import org.agrona.DirectBuffer;


public class LatencyPublisher extends MyPublisher {
    Histogram histogram;


    public static void main (String []args){
        LatencyPublisher lp = new LatencyPublisher();
        lp.execution();
    }

    @Override
    protected long insideLoopAction(Publication publication, DirectBuffer buffer, int size) {
        return 0;
    }

    @Override
    protected String generateMsg() {
        return String.valueOf(System.nanoTime());
    }
}
