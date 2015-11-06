package pap.ass04.sol;

import pap.ass04.TextLibFactory;

public class TestTextBalls {
	public static void main(String[] args) {
		TextLibFactory.getInstance().cls();
		Boundary boundary = new Boundary(1,1,80,20);
		java.util.stream.IntStream.range(1,20).forEach(i -> {
			new BallAgent(boundary,i%10,new P2d(40,10)).start();
		});
	}

}
