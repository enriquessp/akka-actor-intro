package br.tur.reservafacil.tutorials.akka.behaviour;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Created by enrique on 10/2/16.
 */
public class AlarmActor extends AbstractLoggingActor {

    // Protocols
    public static class Activity {}
    public static class Enable {
        private final String password;
        public Enable(String password) {
            this.password = password;
        }
    }
    public static class Disable {
        private final String password;
        public Disable(String password) {
            this.password = password;
        }
    }

    private final String password;
    private final PartialFunction<Object, BoxedUnit> enabled;
    private final PartialFunction<Object, BoxedUnit> disabled;

    public AlarmActor(String password) {
        this.password = password;

        enabled = ReceiveBuilder
                    .match(Activity.class, this::onActivity)
                    .match(Disable.class, this::onDisable)
                    .build();
        disabled = ReceiveBuilder
                    .match(Enable.class, this::onEnable)
                    .build();
        context().become(disabled);
    }

    private void onEnable(Enable enable) {
        if (password.equals(enable.password)) {
            context().become(enabled);
            log().info("Alarm enabled!");
        } else {
            log().warning("Someone not authorized tried to activate the alarm!!!!!!!!");
        }
    }

    private void onDisable(Disable disable) {
        if (password.equals(disable.password)) {
            context().become(disabled);
            log().info("Alarm disabled!");
        } else {
            log().warning("Someone not authorized tried to deactivate the alarm!!!!!!!!");
        }
    }

    private void onActivity(Activity activity) {
        log().info("whwhheheehwwe Alarm! Alarm!");
    }

    public static Props props(String password) {
        return Props.create(AlarmActor.class, password);
    }

}