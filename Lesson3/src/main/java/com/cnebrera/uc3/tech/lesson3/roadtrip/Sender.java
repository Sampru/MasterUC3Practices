package com.cnebrera.uc3.tech.lesson3.roadtrip;

import com.cnebrera.uc3.tech.lesson3.publisher.SenderPublisher;
import com.cnebrera.uc3.tech.lesson3.subscriber.SenderSubscriber;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Sender {

    public static void main(String[] args) {
        (new Sender()).runtime(args);
    }

    private void runtime(String[] args) {
        SenderPublisher publisher = (args.length > 0) ? new SenderPublisher(args[0]) : new SenderPublisher();
        SenderSubscriber subscriber = (args.length > 0) ? new SenderSubscriber(args[0]) : new SenderSubscriber();
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
}
