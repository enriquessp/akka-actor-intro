package br.tur.reservafacil.tutorials.akka.state;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.lightbend.akkasample.StdIn;

/**
 * Created by enrique on 10/1/16.
 */
public class App {

    public static void main(String[] args) throws InterruptedException {

        ActorSystem actorSystem = ActorSystem.create("stateSystem");

        // Its a good practice to give a name to every actor.
        ActorRef counter = actorSystem.actorOf(CounterActor.props(), "counterActor");

        for (int cii = 0; cii < 10; cii++) {
            new Thread(
                    () -> {
                        for (int cjj = 0; cjj < 5; cjj++) {
                            counter.tell(new CounterActor.CountMessage(), ActorRef.noSender());
                        }
                    }
            ).start();
        }

        System.out.println("ENTER to terminate");
        StdIn.readLine();

    }
}