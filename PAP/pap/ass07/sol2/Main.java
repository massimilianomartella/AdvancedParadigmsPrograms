package pap.ass07.sol2;

import akka.actor.*;

public class Main {

  public static void main(String[] args) {
	  ActorSystem system = ActorSystem.create("guessTheNumber");
	  system.actorOf(Props.create(BootActor.class, Integer.parseInt(args[0])));
  }
}
