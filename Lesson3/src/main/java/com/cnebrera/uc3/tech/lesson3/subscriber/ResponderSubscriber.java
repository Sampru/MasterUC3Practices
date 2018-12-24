package com.cnebrera.uc3.tech.lesson3.subscriber;

import com.cnebrera.uc3.tech.lesson3.handler.ResponderHandler;
import com.cnebrera.uc3.tech.lesson3.roadtrip.Responder;
import io.aeron.Subscription;
import org.agrona.concurrent.BusySpinIdleStrategy;
import org.agrona.concurrent.IdleStrategy;

import java.util.Observable;
import java.util.Observer;

public class ResponderSubscriber extends MySubscriber implements Observer {
    private Responder responder;

    public ResponderSubscriber(Responder responder) {
        super(2);
        this.responder = responder;
    }

    public ResponderSubscriber(Responder responder, String channel) {
        super(channel, 2);
        this.responder = responder;
    }

    @Override
    protected void subscriberAction(Subscription subscription) {
        ResponderHandler fh = new ResponderHandler();
        IdleStrategy idle = new BusySpinIdleStrategy();
        int result;

        fh.addObserver(this);

        while (true) {
            result = subscription.poll(fh, 1);
            idle.idle(result);
        }
    }

    private void addResponseToQueue(ResponderHandler fh) {
        while (!this.responder.putNewMessage(fh.getMsg())) try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        addResponseToQueue((ResponderHandler) observable);
    }
}
