package pap.ass05.MinDistance;

import java.util.List;
import java.util.Optional;

import javax.vecmath.Point3d;

public class FindMinPointWithStream {
	private List<Point3d> p;
	private Point3d c;
	
	/**
	 * 
	 * @param points
	 * @param toFind
	 */
	public FindMinPointWithStream(List<Point3d> points, Point3d toFind) {
		p = points;
		c = toFind;
	}
	
	/**
	 * 
	 */
	public void findDistanceMin() {
		long startTimeStream = System.currentTimeMillis();

		Optional<Point3d> pMinStream = p.stream().min(
				(Point3d p1, Point3d p2) -> Double.compare(p1.distance(c),
						p2.distance(c)));
		System.out.println("Point min with Stream: " + pMinStream);
		long stopTimeStream = System.currentTimeMillis() - startTimeStream;
		System.out.println("Total time with Stream: " + stopTimeStream + " ms");
	}

}
