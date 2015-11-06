package pap.ass07.sol;

import java.util.*;

import akka.actor.*;

public class BootActor extends UntypedActor {
	 
	  private int nPlayers;
	  private int nActorsDone;
	
 	  public BootActor(int nPlayers) {
 		 this.nPlayers = nPlayers;
 		 this.nActorsDone = 0;
 		 
 		 ActorRef oracle = getContext().actorOf(Props.create(Oracle.class),"oracle");
 		 log("oracle created: "+oracle);
 		 List<ActorRef> players = new ArrayList<ActorRef>(nPlayers);
 		  for (int i = 0; i < nPlayers; i++){
 			 ActorRef peer = getContext().actorOf(Props.create(Player.class, i), "player-"+i);
 			 players.add(peer);
 		  }
 		  log("players created.");
 		  for (int i = 0; i < players.size(); i++){
 			 ActorRef player = players.get(i);
 			 InitMsg initMsg = new InitMsg(oracle, players.get((i + 1) % players.size()));
 			 player.tell(initMsg, getSelf());
 		  }
 		  log("players informed.");
 		  players.get(0).tell(new TurnMsg(), getSelf());
	  }
 	  
	  @Override
	  public void onReceive(Object msg) {
		  nActorsDone++;
		  if (nActorsDone == nPlayers){
			  log("exit.");
			  getContext().stop(getSelf());
		      getContext().system().shutdown();
		  }
	  }
	  
	  private void log(String msg){
 		  System.out.println("[BootActor] "+msg);
	  }	
}
