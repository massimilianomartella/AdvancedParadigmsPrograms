package pap.test;

public class GaussianClass {
	private static final double EPSILON = 1e-10;

	// Gaussian elimination with partial pivoting
	public static double[] lsolve(double[][] A, double[] b) {
		int N = b.length;

		for (int p = 0; p < N; p++) {

			// find pivot row and swap
			int max = p;
			for (int i = p + 1; i < N; i++) {
				if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
					max = i;
				}
			}
			double[] temp = A[p];
			A[p] = A[max];
			A[max] = temp;
			double t = b[p];
			b[p] = b[max];
			b[max] = t;

			// singular or nearly singular
			if (Math.abs(A[p][p]) <= EPSILON) {
				throw new RuntimeException(
						"Matrix is singular or nearly singular");
			}

			// pivot within A and b
			for (int i = p + 1; i < N; i++) {
				double alpha = A[i][p] / A[p][p];
				b[i] -= alpha * b[p];
				for (int j = p; j < N; j++) {
					A[i][j] -= alpha * A[p][j];
				}
			}
		}

		// back substitution
		double[] x = new double[N];
		for (int i = N - 1; i >= 0; i--) {
			double sum = 0.0;
			for (int j = i + 1; j < N; j++) {
				sum += A[i][j] * x[j];
			}
			x[i] = (b[i] - sum) / A[i][i];
		}
		return x;
	}

	private static boolean calculateThreeCircleIntersection(double x0, double y0,
			double r0, double x1, double y1, double r1, double x2, double y2,
			double r2) {
		double a, dx, dy, d, h, rx, ry;
		double point2_x, point2_y;

		/*
		 * dx and dy are the vertical and horizontal distances between the
		 * circle centers.
		 */
		dx = x1 - x0;
		dy = y1 - y0;

		/* Determine the straight-line distance between the centers. */
		d = Math.sqrt((dy * dy) + (dx * dx));

		/* Check for solvability. */
		if (d > (r0 + r1)) {
			/* no solution. circles do not intersect. */
			return false;
		}
		if (d < Math.abs(r0 - r1)) {
			/* no solution. one circle is contained in the other */
			return false;
		}

		/*
		 * 'point 2' is the point where the line through the circle intersection
		 * points crosses the line between the circle centers.
		 */

		/* Determine the distance from point 0 to point 2. */
		a = ((r0 * r0) - (r1 * r1) + (d * d)) / (2.0 * d);

		/* Determine the coordinates of point 2. */
		point2_x = x0 + (dx * a / d);
		point2_y = y0 + (dy * a / d);

		/*
		 * Determine the distance from point 2 to either of the intersection
		 * points.
		 */
		h = Math.sqrt((r0 * r0) - (a * a));

		/*
		 * Now determine the offsets of the intersection points from point 2.
		 */
		rx = -dy * (h / d);
		ry = dx * (h / d);

		/* Determine the absolute intersection points. */
		double intersectionPoint1_x = point2_x + rx;
		double intersectionPoint2_x = point2_x - rx;
		double intersectionPoint1_y = point2_y + ry;
		double intersectionPoint2_y = point2_y - ry;

		System.out.println("INTERSECTION Circle1 AND Circle2: (" + intersectionPoint1_x
				+ "," + intersectionPoint1_y + ")" + " AND ("
				+ intersectionPoint2_x + "," + intersectionPoint2_y + ")");

		/*
		 * Lets determine if circle 3 intersects at either of the above
		 * intersection points.
		 */
		dx = intersectionPoint1_x - x2;
		dy = intersectionPoint1_y - y2;
		double d1 = Math.sqrt((dy * dy) + (dx * dx));

		dx = intersectionPoint2_x - x2;
		dy = intersectionPoint2_y - y2;
		double d2 = Math.sqrt((dy * dy) + (dx * dx));

		if (Math.abs(d1 - r2) < EPSILON) {
			System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3: ("
					+ intersectionPoint1_x + "," + intersectionPoint1_y + ")");
		} else if (Math.abs(d2 - r2) < EPSILON) {
			System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3: ("
					+ intersectionPoint2_x + "," + intersectionPoint2_y + ")"); // here
																				// was
																				// an
																				// error
		} else {
			System.out.println("INTERSECTION Circle1 AND Circle2 AND Circle3: NONE");
		}
		return true;
	}

	// sample client
	public static void main(String[] args) {
//		int N = 2;
//		double[][] A = { { 4 * 4, 4 * 4, 6 * 6 }, { 26 * 26, 100, 9 * 9 },
//				{ 16 * 16, 26 * 26, 4.5 * 4.5 } };
//		double[] b = { 1, 1, 1 };
//		double[] x = lsolve(A, b);
//
//		// print results
//		for (int i = 0; i < N; i++) {
//			System.out.println(x[i]);
//		}
		
		calculateThreeCircleIntersection(4, 4, 23, // circle 1 (center_x, center_y, radius)
                26, 10, 13, // circle 2 (center_x, center_y, radius)
                16, 26, 10.5);// circle 3 (center_x, center_y, radius)

	}

}
