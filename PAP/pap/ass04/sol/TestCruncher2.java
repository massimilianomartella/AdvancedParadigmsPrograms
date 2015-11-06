package pap.ass04.sol;

import pap.ass04.*;
import pap.ass04.sol.SecretCruncher;

public class TestCruncher2 {

	public static void main(String[] args) {
		long max = Long.parseLong(args[0]);
		int nCrunchers = Integer.parseInt(args[1]);
		
		Secret secret = new Secret(max);
		long startTime = System.currentTimeMillis();				
		long range = max/nCrunchers;		
		long from = 0;
		long to = range;
		for (int i = 0; i < nCrunchers; i++){
			new SecretCruncher(from,to,secret,startTime).start();
			from+=range;
			to+=range;
		}	
	}

}
