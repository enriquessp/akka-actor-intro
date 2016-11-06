package br.tur.reservafacil.tutorials.akka.disponibilidade;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by enrique1 on 10/12/16.
 */
public class Notificador extends AbstractLoggingActor {

    public static class Notificar {
        private final boolean ultimaBusca;
        public Notificar(boolean ultimaBusca) {
            this.ultimaBusca = ultimaBusca;
        }
    }

    public Notificador() {
        receive(
                ReceiveBuilder
                    .match(Notificar.class, this::onNotificar)
                    .build()
        );
    }

    private void onNotificar(Notificar notificar) {
        log().info("POST:::");
        if (notificar.ultimaBusca) {
            sender().tell(PoisonPill.getInstance(), ActorRef.noSender());
        }
    }

    public static Props props() {
        return Props.create(Notificador.class);
    }
}
