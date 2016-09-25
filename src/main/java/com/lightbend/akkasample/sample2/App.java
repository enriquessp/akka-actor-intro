/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package com.lightbend.akkasample.sample2;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.lightbend.akkasample.StdIn;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * actor that changes behavior
 */
public class App {

  public static void main(String[] args) {
    ActorSystem system = ActorSystem.create();

    final ActorRef alarm = system.actorOf(AlarmActor.props("cat"), "alarm");

    alarm.tell(new AlarmActor.Activity(), ActorRef.noSender());
    alarm.tell(new AlarmActor.Enable("dogs"), ActorRef.noSender());
    alarm.tell(new AlarmActor.Enable("cat"), ActorRef.noSender());
    alarm.tell(new AlarmActor.Activity(), ActorRef.noSender());
    alarm.tell(new AlarmActor.Disable("dogs"), ActorRef.noSender());
    alarm.tell(new AlarmActor.Disable("cat"), ActorRef.noSender());
    alarm.tell(new AlarmActor.Activity(), ActorRef.noSender());

    System.out.println("ENTER to terminate");
    StdIn.readLine();
    system.terminate();
  }

}