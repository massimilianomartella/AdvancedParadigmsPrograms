package pap.ass05.sol.mindist;

public class DistCalcSeq extends DistCalc {
	
	public P3d getMinDist(P3d[] points, P3d po) {
		double minDist = Double.MAX_VALUE;
		P3d minP = null;
		for (P3d p: points){
			double dx = po.x - p.x;
			double dy = po.y - p.y;
			double dz = po.z - p.z;
			double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);
			if (dist < minDist){
				minDist = dist;
				minP = p;
			}
		}
		return minP;
	}


}
