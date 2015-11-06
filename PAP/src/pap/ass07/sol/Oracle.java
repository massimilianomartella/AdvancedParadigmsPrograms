package pap.ass07.sol;

import java.util.*;

import akka.actor.*;

public class Oracle extends UntypedActor {
	 
	  private final int number;
		
 	  public Oracle() {
 		  Random rand = new Random(System.currentTimeMillis());
 		  number = rand.nextInt(100);
 		  log("the number to guess is: "+number);
	  }
	
	  @Override
	  public void onReceive(Object msg) {
		  GuessMsg guessMsg = (GuessMsg) msg;
		  int guess = guessMsg.getGuess();
		  if (guess == number){
			  this.getSender().tell(new WonMsg(), this.getSelf());
			  getContext().stop(getSelf());
			  log("exit.");
		  } else {
			  this.getSender().tell(new GuessReplyMsg(number > guess), this.getSelf());
		  }
	  }
	  
	  private void log(String msg){
 		  System.out.println("[ORACLE] "+msg);
	  }
	
}
