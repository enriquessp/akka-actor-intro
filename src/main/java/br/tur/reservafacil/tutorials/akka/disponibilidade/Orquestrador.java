package br.tur.reservafacil.tutorials.akka.disponibilidade;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by enrique1 on 10/3/16.
 */
public class Orquestrador extends AbstractLoggingActor {

    public static class Buscar {
        private final long buscaId;
        private final List<String> dadosBusca;
        public Buscar(long buscaId, List<String> dadosBusca) {
            this.buscaId = buscaId;
            this.dadosBusca = dadosBusca;
        }
    }

    public static class ContabilizarMeta {
        private final ResponseBusca responseBusca;
        public ContabilizarMeta(ResponseBusca responseBusca) {
            this.responseBusca = responseBusca;
        }
    }

    {
        receive(
                ReceiveBuilder
                    .match(Buscar.class, this::onBuscar)
                    .match(ContabilizarMeta.class, this::onContabilizaMeta)
                    .build()
        );
    }

    private long buscaId;
    private int totalBuscando;
    private int totalBuscado;

    private void onBuscar(Buscar buscar) {
        buscaId = buscar.buscaId;
        totalBuscando = buscar.dadosBusca.size();

        int count = 0;
        for (String dadoBusca : buscar.dadosBusca) {
            ActorRef buscador = getContext().actorOf(Buscador.props(), "buscador-"+buscar.buscaId+"-"+count++);
            buscador.tell(new Buscador.Iniciar(buscaId, dadoBusca), self());
        }
    }

    private void onContabilizaMeta(ContabilizarMeta contabilizarMeta) {
        totalBuscado++;
        log().info("Buscado: "+buscaId+" - "+totalBuscando+" - "+totalBuscado+" - "+ Arrays.toString(contabilizarMeta.responseBusca.getCias()));
    }

    public static Props props() {
        return Props.create(Orquestrador.class);
    }
}