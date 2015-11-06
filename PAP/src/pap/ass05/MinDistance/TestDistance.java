package pap.ass05.MinDistance;

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Point3d;

public class TestDistance {

	private static List<Point3d> p;

	private static void generationRandomListPoint3d() {
		int n = Utils.NUM_POINTS;
		// Lista P punti, n punti tridimensionali casuali
		p = new ArrayList<Point3d>();
		long startTimeGeneration = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			p.add(new Point3d(Utils.randInt(Utils.MIN_RAND, Utils.MAX_RAND),
					Utils.randInt(Utils.MIN_RAND, Utils.MAX_RAND), Utils
							.randInt(Utils.MIN_RAND, Utils.MAX_RAND)));
		}
		long stopTimeGeneration = System.currentTimeMillis()
				- startTimeGeneration;
		System.out.println("Total time generation point 3d: "
				+ stopTimeGeneration + " ms");
		// points.stream().forEach(point -> {
		// System.out.println(p.toString());
		// });

	}

	public static void main(String[] args) {
		generationRandomListPoint3d();

		// Punto C - origine
		Point3d c = new Point3d(0, 0, 0);
		// System.out.println("Punto C -> " + c.toString());

		FindMinPointWithFor firstSearch = new FindMinPointWithFor(p, c);
		firstSearch.findDistanceMin();

		FindMinPointWithStream secondSearch = new FindMinPointWithStream(p, c);
		secondSearch.findDistanceMin();

		FindMinPointWithParallelStream thirdSearch = new FindMinPointWithParallelStream(
				p, c);
		thirdSearch.findDistanceMin();

		FindMinPointWithThread fourthSearch = new FindMinPointWithThread(p, c);
		fourthSearch.findDistanceMin();

	}
}
