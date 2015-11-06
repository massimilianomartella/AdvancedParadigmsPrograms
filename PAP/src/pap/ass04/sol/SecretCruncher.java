package pap.ass04.sol;

import pap.ass04.*;

public class  SecretCruncher extends Thread {
	
	private long from, to;
	private Secret secret;
	private long startTime;
	
	public SecretCruncher(long from, long to, Secret secret, long startTime){
		this.from = from;
		this.to = to;
		this.secret = secret;
		this.startTime = startTime;
	}
	
	public void run(){
		for (long num = from; num < to; num++){
			if (secret.guess(num)){
				long dt = System.currentTimeMillis()-startTime;
				System.out.println("FOUND: "+num);
				System.out.println("Total time: "+dt+" ms");
				System.exit(0);
			}
		}
	}
}
