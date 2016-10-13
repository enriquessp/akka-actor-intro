package br.tur.reservafacil.tutorials.akka.disponibilidade;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.lightbend.akkasample.StdIn;

import java.util.Arrays;

/**
 * Created by enrique1 on 10/3/16.
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        ActorSystem actorSystem = ActorSystem.create("actorSystem");

        for (int i = 0; i < 3; i++) {
            final long buscaId = generateCorrelationId();
            new Thread(() -> {
                ActorRef orquestrador = actorSystem.actorOf(Orquestrador.props(), "orquestrador-"+buscaId);
                orquestrador.tell(new Orquestrador.Buscar(buscaId, Arrays.asList("AMD"+buscaId, "SAB"+buscaId, "GAL"+buscaId)), ActorRef.noSender());
            }).start();
        }

        StdIn.readLine();
        actorSystem.shutdown();
        actorSystem.awaitTermination();
    }


    private static int correlationId = 0;
    private static long generateCorrelationId() throws InterruptedException {
        return correlationId++;
    }

}