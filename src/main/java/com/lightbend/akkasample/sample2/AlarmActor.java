package com.lightbend.akkasample.sample2;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Created by enrique1 on 9/25/16.
 */
public class AlarmActor extends AbstractLoggingActor {

    // Protocol
    static class Activity {}
    static class Disable {
        private final String password;
        public Disable(String password) { this.password = password; }
    }
    static class Enable {
        private final String password;
        public Enable(String password) {
            this.password = password;
        }
    }

    private final String password;
    private final PartialFunction<Object, BoxedUnit> disabled;
    private final PartialFunction<Object, BoxedUnit> enabled;

    public AlarmActor(String password) {
        this.password = password;

        enabled =  ReceiveBuilder
                        .match(Activity.class, this::onActivity)
                        .match(Disable.class, this::onDisable)
                        .build();

        disabled = ReceiveBuilder
                        .match(Enable.class, this::onEnable)
                        .build();

        receive(disabled);
    }

    private void onEnable(Enable enable) {
        if (password.equals(enable.password)) {
            log().info("Alarm enabled!");
            getContext().become(enabled);
        } else {
            log().warning("Someone failed to enable the alarm!");
        }
    }

    private void onDisable(Disable disable) {
        if (password.equals(disable.password)) {
            log().info("Alarm disabled!");
            getContext().become(disabled);
        } else {
            log().warning("Someone who didn't know the password tried to disable it!");
        }
    }

    private void onActivity(Activity activity) {
        log().warning("lollsowewfow alarm! alarm! alarm! ");
    }

    public static Props props(String password) {
        return Props.create(AlarmActor.class, password);
    }
}