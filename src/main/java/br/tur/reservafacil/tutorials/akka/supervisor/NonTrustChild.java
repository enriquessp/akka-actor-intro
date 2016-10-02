package br.tur.reservafacil.tutorials.akka.supervisor;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by enrique1 on 10/2/16.
 */
public class NonTrustChild extends AbstractLoggingActor {

    public static class Command {}

    private int count;

    {
        receive(
                ReceiveBuilder
                    .match(Command.class, this::onCount)
                    .build()
        );
    }

    private void onCount(Command countMessage) {
        count++;
        if (count % 4 == 0) {
            throw new RuntimeException("Erro contando");
        } else {
            log().info("Counting... " + count);
        }
    }

    public static Props props() {
        return Props.create(NonTrustChild.class);
    }

}