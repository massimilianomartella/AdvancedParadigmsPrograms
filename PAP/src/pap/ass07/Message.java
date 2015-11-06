package pap.ass07;

import akka.actor.ActorSelection;

public class Message {
	
	private int guess;
	private String indication;
	private String stateOfPlayer;
	private ActorSelection nextPlayer;
	
	public Message() {
		
	}
	
	public int getNum() {
		return guess;
	}
	
	public void setNum(int n) {
		this.guess = n;
	}
	
	public void setInd(String string) {
		this.indication = string;
	}
	
	public String getInd() {
		return this.indication;
	}
	
	public void setStatePalyer(String string) {
		this.stateOfPlayer = string;
	}
	
	public String getStatePlayer() {
		return this.stateOfPlayer;
	}

	public void setWho(ActorSelection nextPlayer) {
		this.nextPlayer = nextPlayer;
	}
	
	public ActorSelection getWho() {
		return nextPlayer;
	}
}
