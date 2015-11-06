package pap.ass05.MinDistance;

import java.util.List;

import javax.vecmath.Point3d;

public class FindMinPointWithFor {
	private List<Point3d> p;
	private Point3d c;
	
	/**
	 * 
	 * @param points
	 * @param toFind
	 */
	public FindMinPointWithFor(List<Point3d> points, Point3d toFind) {
		p = points;
		c = toFind;
	}
	
	/**
	 * 
	 */
	public void findDistanceMin() {
		long startTimeFor = System.currentTimeMillis();
		Point3d pMinFor = new Point3d(Utils.MAX_RAND, Utils.MAX_RAND,
				Utils.MAX_RAND);
		for (int i = 0; i < Utils.NUM_POINTS; i++) {
			if (p.get(i).distance(c) <= c.distance(pMinFor))
				pMinFor = p.get(i);
		}

		System.out.println("Point min with For: " + pMinFor);
		long stopTimeFor = System.currentTimeMillis() - startTimeFor;
		System.out.println("Total time with For: " + stopTimeFor + " ms");
	}
}
