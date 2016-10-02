package br.tur.reservafacil.tutorials.akka.behaviour;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * Created by enrique on 10/2/16.
 */
public class App {

    public static void main(String[] args) {
        String password = "test";

        ActorSystem actorSystem = ActorSystem.create("behaviourSystem");
        ActorRef alarmActor = actorSystem.actorOf(AlarmActor.props(password), "alarmActor");

        alarmActor.tell(new AlarmActor.Activity(), ActorRef.noSender());
        alarmActor.tell(new AlarmActor.Enable(password), ActorRef.noSender());
        alarmActor.tell(new AlarmActor.Activity(), ActorRef.noSender());
        alarmActor.tell(new AlarmActor.Disable("abcd"), ActorRef.noSender());
        alarmActor.tell(new AlarmActor.Disable(password), ActorRef.noSender());
        alarmActor.tell(new AlarmActor.Activity(), ActorRef.noSender());

    }

}
