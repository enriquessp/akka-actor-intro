package br.tur.reservafacil.tutorials.akka.disponibilidade;

/**
 * Created by enrique1 on 10/3/16.
 */
public class ResponseBusca {
    private final String[] cias;

    public ResponseBusca(String[] cias) {
        this.cias = cias;
    }

    public String[] getCias() {
        return cias;
    }
}
