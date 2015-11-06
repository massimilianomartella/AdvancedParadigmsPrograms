package pap.ass05.sol.mindist;

import java.util.Random;

public abstract class DistCalc {

	abstract public P3d getMinDist(P3d[] points, P3d point);

	public P3d[] getTestSet(int n){
		Random rand = new Random();
		P3d[] list = new P3d[n];
		for (int i = 0; i < n; i++){
			double x = rand.nextDouble();
			double y = rand.nextDouble();
			double z = rand.nextDouble();
			list[i] = new P3d(x,y,z);
		}
		return list;
	}

}
