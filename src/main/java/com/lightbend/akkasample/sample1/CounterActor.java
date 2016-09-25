package com.lightbend.akkasample.sample1;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by enrique1 on 9/25/16.
 */
public class CounterActor extends AbstractLoggingActor {

    static class Message {}

    private int counter = 0;

    {
        receive(
                ReceiveBuilder
                    .match(Message.class, this::onReceive)
                    .build()
        );
    }

    private void onReceive(Message message) {
        counter++;
        log().info("Increased counter: " + counter);
    }

    public static Props props() { return Props.create(CounterActor.class); }
}