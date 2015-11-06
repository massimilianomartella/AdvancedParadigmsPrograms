package pap.lab06.quad2;

public class QuadratureResult {
	
	private double sum; 
	private int nTotalResultsToWait;
	private int nResultsArrived;
	
	public QuadratureResult(int nResults){
		nTotalResultsToWait = nResults;
		nResultsArrived = 0;
	}
	
	public synchronized void add(double value){
		sum += value;
		nResultsArrived++;
		if (nResultsArrived >= nTotalResultsToWait){
			notifyAll();
		}
	}

	public synchronized double getResult() throws InterruptedException {
		while (nResultsArrived < nTotalResultsToWait){
			wait();
		}
		return sum;
	}
}
