package br.tur.reservafacil.tutorials.akka.supervisor;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.*;

/**
 * Created by enrique1 on 10/2/16.
 */
public class Parent extends AbstractLoggingActor {

    private static int numberRetries = 5;

    private OneForOneStrategy STRATEGY = new OneForOneStrategy(
            numberRetries,
            Duration.create("10 seconds"),
            DeciderBuilder.matchAny(any -> resume()).build()
    );

    {
        ActorRef child = getContext().actorOf(NonTrustChild.props(), "child");

        receive(
                ReceiveBuilder
                    .matchAny(any -> child.forward(any, getContext()))
                    .build()
        );
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return STRATEGY;
    }

    public static Props props() {
        return Props.create(Parent.class);
    }
}