package com.cnebrera.uc3.tech.lesson3.roadtrip;

import com.cnebrera.uc3.tech.lesson3.publisher.ResponderPublisher;
import com.cnebrera.uc3.tech.lesson3.subscriber.ResponderSubscriber;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Responder {
    private AtomicInteger producerIndex, consumerIndex, msgCount;
    private String[] buffer;
    private final int BUFFER_SIZE = 60;

    private Responder() {
        this.producerIndex = new AtomicInteger();
        this.consumerIndex = new AtomicInteger();
        this.msgCount = new AtomicInteger();
        this.buffer = new String[this.BUFFER_SIZE];
    }

    public static void main(String[] args) {
        (new Responder()).runtime(args);
    }

    private void runtime(String[] args) {
        ResponderPublisher publisher = (args.length > 0) ? new ResponderPublisher(this, args[0]) : new ResponderPublisher(this);
        ResponderSubscriber subscriber = (args.length > 0) ? new ResponderSubscriber(this, args[0]) : new ResponderSubscriber(this);
        ExecutorService pool = Executors.newFixedThreadPool(2);

        System.out.println("Executing threads");
        pool.execute(subscriber);
        pool.execute(publisher);

        pool.shutdown();
        try {
            if (!pool.awaitTermination(1000, TimeUnit.SECONDS)) pool.shutdownNow();
        } catch (InterruptedException e) {
            pool.shutdownNow();
            e.printStackTrace();
        }
        System.out.println("Execution finished");
    }

    public boolean putNewMessage(String msg) {
        if (this.producerIndex.get() != (this.consumerIndex.get() - 1) && !((this.producerIndex.get() == this.BUFFER_SIZE - 1) && this.consumerIndex.get() == 0)) {
            this.buffer[this.producerIndex.getAndAdd(1)] = msg;
            if (this.producerIndex.get() == BUFFER_SIZE) this.producerIndex.set(0);
            return true;
        } else {
            return false;
        }

    }

    public String readNextMessage() {
        String msg;
        if (this.producerIndex.get() != this.consumerIndex.get()) {
            msg = this.buffer[this.consumerIndex.getAndAdd(1)];
            if (this.consumerIndex.get() == BUFFER_SIZE) this.consumerIndex.set(0);
        } else {
            msg = "";
        }


        return msg;
    }
}
