package pap.ass04.sol;

import pap.ass04.Secret;


class Cruncher extends Thread {
	private long min, max;
	private Secret secret;
	private long t0;
	
	public Cruncher(long min, long max, Secret secret, long t0){
		this.min = min;
		this.max = max;
		this.secret = secret;
		this.t0 = t0;
	}
	
	public void run(){
		long guess = min;
		while (guess < max){
			if (secret.guess(guess)){
				System.out.println("FOUND: "+guess+" in "+ (System.currentTimeMillis()-t0)+"ms");
				break;
			} else {
				guess++;
			}
		}
	}
}

public class TestSecret {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		long max = 10000000000l;
		
		Secret secret = new Secret(max);
		
		long t0 = System.currentTimeMillis();
		//Thread c0 = new Cruncher(0,max,secret,t0);
		Thread c0 = new Cruncher(0,max/2,secret,t0);
		Thread c1 = new Cruncher(max/2,max,secret,t0);
		c0.start();
		c1.start();
		
		
	}

}
