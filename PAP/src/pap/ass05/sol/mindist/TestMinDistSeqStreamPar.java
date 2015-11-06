package pap.ass05.sol.mindist;

public class TestMinDistSeqStreamPar {

	public static void main(String[] args) {

		Chrono chrono = new Chrono();
		
		int size = Integer.parseInt(args[0]);

		DistCalc calc = new DistCalcSeqStreamPar();
		System.out.println("Generating test case - size: "+size);
		P3d[] testCase = calc.getTestSet(size);
		System.out.println("Get min point...");
		
		chrono.reset();
		P3d p = calc.getMinDist(testCase, new P3d(0,0,0));
		long dt = chrono.getTimeElapsed();
		
		System.out.println("TestMinDist PAR STREAM Result: "+p+" - collection size: "+size+ " - "+dt);
	}

}
