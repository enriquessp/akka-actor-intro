package br.tur.reservafacil.tutorials.akka.supervisor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.lightbend.akkasample.StdIn;

/**
 * Created by enrique1 on 10/2/16.
 */
public class App {

    public static void main(String[] args) {

        ActorSystem actorSystem = ActorSystem.create("actorSystem");

        ActorRef parent = actorSystem.actorOf(Parent.props(), "parent");

        for (int i = 0; i < 10; i++) {
            parent.tell(new NonTrustChild.Command(), ActorRef.noSender());
        }

        System.out.println("ENTER to terminate");
        StdIn.readLine();

        actorSystem.shutdown();
        actorSystem.awaitTermination();

    }

}
