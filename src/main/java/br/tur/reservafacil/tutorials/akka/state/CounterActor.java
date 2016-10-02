package br.tur.reservafacil.tutorials.akka.state;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by enrique on 10/1/16.
 */
public class CounterActor extends AbstractLoggingActor {

    // Protocol
    public static class CountMessage {}

    // Every actor is able to have state
    // However, the message it process must be immutable. Otherwise, the thread nightmare begins.
    private int counter;

    // This is a common way to initialize the receive method.
    // The receive method is responsible to process messages and a better way to declare it is using a builder.
    // The builder routes the message to a private method which is going to be responsible to process it.
    {
        receive(
                ReceiveBuilder
                        .match(CountMessage.class, this::onCount)
                        .build()
        );
    }

    private void onCount(CountMessage countMessage) {
        counter++;
        log().info("CounterActor incremented... " + counter);
    }

    // Props is the actors descriptor. It defines how to create an actor.
    public static Props props() {
        return Props.create(CounterActor.class);
    }

}