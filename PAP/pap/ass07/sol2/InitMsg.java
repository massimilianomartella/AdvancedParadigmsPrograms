package pap.ass07.sol2;

import akka.actor.ActorRef;

public class InitMsg {

	private final ActorRef oracle;
	private final ActorRef next;
	
	public InitMsg(ActorRef oracle, ActorRef next){
		this.oracle = oracle;
		this.next = next;
	}
	
	public ActorRef getOracle(){
		return oracle;
	}

	public ActorRef getNext(){
		return next;
	}
}
