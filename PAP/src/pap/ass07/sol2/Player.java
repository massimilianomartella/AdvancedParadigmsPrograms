package pap.ass07.sol2;

import java.util.*;

import akka.actor.*;

public class Player extends UntypedActor {
	 
	  private ActorRef oracle;
	  private ActorRef next;
	  private ActorRef boot;
	  private Random rand;
	  private int min, max, currentGuess;
	  private int playerIndex;
	  private int turnNumber;
	  
 	  public Player(int playerIndex) {
 		  rand = new Random(System.nanoTime());
 		  min = 0;
 		  max = Integer.MAX_VALUE;
 		  this.playerIndex = playerIndex;
 		  turnNumber = 0;
	  }
	
	  @Override
	  public void onReceive(Object msg) {
		  if (msg instanceof InitMsg){
			  InitMsg init = (InitMsg) msg;
			  oracle = init.getOracle();
			  next = init.getNext();
			  boot = getSender();
			  boot.tell(new ReadyMsg(), getSelf());
		  } else if  (msg instanceof TurnMsg){
			  if (playerIndex == 0){
				  turnNumber++;
				  System.out.println("turn: "+turnNumber);
			  }
			  currentGuess = chooseNum();
			  log("my turn - try: "+currentGuess);
			  oracle.tell(new GuessMsg(currentGuess), getSelf());
		  } else if  (msg instanceof GuessReplyMsg){
			  GuessReplyMsg replyMsg = (GuessReplyMsg) msg;
			  if (replyMsg.isHigher()){
				  min = currentGuess;
			  } else {
				  max = currentGuess;
			  }
			  next.tell(new TurnMsg(), getSelf());
		  } else if  (msg instanceof WonMsg){
			  System.out.println("won!");
			  next.tell(new LostMsg(), getSelf());
			  boot.tell(new DoneMsg(), getSelf());
			  getContext().stop(getSelf());
		  } else {
			  System.out.println("sob!");
			  next.tell(new LostMsg(), getSelf());
			  boot.tell(new DoneMsg(), getSelf());
			  getContext().stop(getSelf());
		  }  
	  }
	  
	  private int chooseNum(){
		  int delta = max - min;
		  int guess = Math.abs(rand.nextInt()) % delta;
		  return min + guess;
	  }
	  
	  private void log(String msg){
 		  System.out.println("[PLAYER "+this.getSelf()+"] "+msg);
	  }
}
