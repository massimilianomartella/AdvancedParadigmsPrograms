package pap.ass07;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
	

	public static void main(String[] args) {
//		akka.Main.main(new String[] { BootActor.class.getName()});

		final int numPlayers = 5000;
		List<ActorRef> players;
		//Create a system
		final ActorSystem system = ActorSystem.create("System");
		
		players = createActorsPlayers(system, numPlayers);
		createActorOracle(system, players);
		
		
	}
	
	static void createActorOracle(ActorSystem system, List<ActorRef> players) {
		system.actorOf(Props.create(OracleActor.class, players),"Oracle");
		Utils.log("Oracle created.");
	}
	
	static List<ActorRef> createActorsPlayers(ActorSystem system, int numPlayers) {
		List<ActorRef> players = new ArrayList<ActorRef>();
		
		for (int i = 1; i <= numPlayers; i++) {
			ActorRef player = system.actorOf(Props.create(PlayersActor.class, numPlayers),"Player-" + i);
			players.add(player);
		}
		
		Utils.log("Players created.");
		return players;
	}
}

