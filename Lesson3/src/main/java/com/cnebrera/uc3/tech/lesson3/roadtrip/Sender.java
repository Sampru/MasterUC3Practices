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
        String channel = "";
        int msgPerSec = -1;

        if (args.length == 2) {
            try {
                msgPerSec = Integer.parseInt(args[0]);
                channel = args[1];
            } catch (NumberFormatException e) {
                msgPerSec = Integer.parseInt(args[1]);
                channel = args[0];
            }
            System.out.println("Parameters: mps: " + msgPerSec + " channel: " + channel);
        } else if (args.length == 1) {
            try {
                msgPerSec = Integer.parseInt(args[0]);
                System.out.println("Parameter: mps: " + msgPerSec);
            } catch (NumberFormatException e) {
                channel = args[0];
                System.out.println("Parameter: channel: " + channel);
            }
        }

        SenderPublisher publisher = (args.length == 2) ? new SenderPublisher(channel, msgPerSec) : (args.length == 1) ? (msgPerSec == -1) ? new SenderPublisher(channel) :new SenderPublisher(msgPerSec) : new SenderPublisher();
        SenderSubscriber subscriber = (!channel.equals("")) ? new SenderSubscriber(channel) : new SenderSubscriber();
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
