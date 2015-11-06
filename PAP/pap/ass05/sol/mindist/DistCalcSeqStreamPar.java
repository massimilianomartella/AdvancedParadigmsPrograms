package pap.ass05.sol.mindist;

import java.util.Arrays;
import java.util.Optional;

public class DistCalcSeqStreamPar extends DistCalc {
	
	public P3d getMinDist(P3d[] points, P3d po) {
		
		Optional<DistInfo> result = Arrays.stream(points).parallel()
			.map(p -> {
				double dx = po.x - p.x;
				double dy = po.y - p.y;
				double dz = po.z - p.z;
				return new DistInfo(p,Math.sqrt(dx*dx + dy*dy + dz*dz));})
			.reduce((d1, d2) -> d1.dist < d2.dist ? d1 : d2);
		
		return result.get().p;
	}


}
