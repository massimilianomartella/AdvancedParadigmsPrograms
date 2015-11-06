package pap.ass05.sol.game;

public class Synch {
	
	private int nParticipants;
	private int currentTurn;
	private boolean closed;
	
	public Synch(int nParticipants){
		currentTurn = 0;
		this.nParticipants = nParticipants;
		closed = false;
	}
	
	public synchronized void waitForTurn(int turn) throws InterruptedException {
		if (!closed){
			while (turn != currentTurn){
				wait();
				if (closed){
					throw new InterruptedException();
				}
			}
		} else {
			throw new InterruptedException();
		}
	}
	
	public synchronized void next(){
		currentTurn = (currentTurn + 1) % nParticipants;
		notifyAll();
	}
	
	public synchronized void close(){
		closed = true;
		notifyAll();
	}

}
