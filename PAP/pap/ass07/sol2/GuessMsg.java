package pap.ass07.sol2;

public class GuessMsg {

	private final int guess;
	
	public GuessMsg(int guess){
		this.guess = guess;
	}
	
	public int getGuess(){
		return guess;
	}
}
