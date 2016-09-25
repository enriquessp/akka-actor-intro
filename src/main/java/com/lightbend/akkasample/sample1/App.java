/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package com.lightbend.akkasample.sample1;

import akka.actor.*;
import akka.japi.pf.ReceiveBuilder;
import com.lightbend.akkasample.StdIn;

/**
 * counter - actor that keeps state
 */
public class App {

  public static void main(String[] args) {
    ActorSystem system = ActorSystem.create("sample1");

    final ActorRef counter = system.actorOf(CounterActor.props(), "counter");

    for (int i = 0; i < 5; i++) {
      new Thread(() -> {
        for (int j = 0; j < 5; j++) {
          counter.tell(new CounterActor.Message(), ActorRef.noSender());
        }
      }).start();
    }

    System.out.println("ENTER to terminate");
    StdIn.readLine();
  }
}
