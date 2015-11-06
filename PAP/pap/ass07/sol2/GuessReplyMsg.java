package pap.ass07.sol2;

public class GuessReplyMsg {

	private final boolean isHigher;

	public GuessReplyMsg(boolean isHigher){
		this.isHigher = isHigher;
	}
	
	public boolean isHigher(){
		return isHigher;
	}
}
