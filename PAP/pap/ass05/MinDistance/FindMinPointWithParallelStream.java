package pap.ass05.MinDistance;

import java.util.List;
import java.util.Optional;

import javax.vecmath.Point3d;

public class FindMinPointWithParallelStream {

	private List<Point3d> p;
	private Point3d c;

	/**
	 * 
	 * @param points
	 * @param toFind
	 */
	public FindMinPointWithParallelStream(List<Point3d> points, Point3d toFind) {
		p = points;
		c = toFind;
	}

	/**
	 * 
	 */
	public void findDistanceMin() {
		long startTimeParallelStream = System.currentTimeMillis();

		Optional<Point3d> pMinParallelStream = p.parallelStream().min(
				(Point3d p1, Point3d p2) -> Double.compare(p1.distance(c),
						p2.distance(c)));
		System.out.println("Point min with Parallel Stream: "
				+ pMinParallelStream);
		long stopTimeParallelStream = System.currentTimeMillis()
				- startTimeParallelStream;
		System.out.println("Total time with Parallel Stream: "
				+ stopTimeParallelStream + " ms");
	}
}
