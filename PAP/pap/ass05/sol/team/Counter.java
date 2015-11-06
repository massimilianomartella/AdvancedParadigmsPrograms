package pap.ass05.sol.team;

public class Counter {

	private int cont;
	
	public Counter(){
		this.cont = 0;
	}
	
	public void inc(){
		cont++;
	}

	public int getValue(){
		return cont;
	}
}
