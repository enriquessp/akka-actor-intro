package br.tur.reservafacil.tutorials.akka.disponibilidade;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * Created by enrique1 on 10/3/16.
 */
public class Buscador extends AbstractLoggingActor {

    public static class Iniciar {

        private final long buscaId;
        private final String dadoBusca;
        public Iniciar(long buscaId, String dadoBusca) {
            this.buscaId = buscaId;
            this.dadoBusca = dadoBusca;
        }
    }

    {
        receive(
                ReceiveBuilder.match(Iniciar.class, this::onIniciar).build()
        );
    }

    private void onIniciar(Iniciar iniciar) {
        log().info("buscando... "+iniciar.buscaId+" - "+iniciar.dadoBusca);
        ResponseBusca responseBusca = new ResponseBusca( new String[]{"AF","KL","AZ"} );
        log().info("busca finalizada "+iniciar.buscaId+" - "+iniciar.dadoBusca);
        sender().tell(new Orquestrador.ContabilizarMeta(responseBusca), self());
    }

    public static Props props() {
        return Props.create(Buscador.class);
    }

}