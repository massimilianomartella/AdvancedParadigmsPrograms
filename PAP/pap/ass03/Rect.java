package pap.ass03;

public class Rect implements Shape {

	private P2d a, b;

	public Rect(int x0, int y0, int x1, int y1) {
		a = new P2d(x0, y0);
		b = new P2d(x1, y1);
	}

	@Override
	public void move(V2d v) {
		a = a.sum(v);
		b = b.sum(v);
	}

	@Override
	public double getPerim() {
		P2d upperRight = new P2d(b.getX(), a.getY());
		return (P2d.distance(a, upperRight) + P2d.distance(upperRight, b)) * 2;

	}

	@Override
	public boolean isInside(P2d p1, P2d p2) {
		if ((Math.abs(a.getX()) <= Math.abs(p1.getX())) && // estremo sinistra X
				(Math.abs(a.getY()) >= Math.abs(p1.getY())) && // estremo
																// sinistra Y
				(Math.abs(b.getX()) >= Math.abs(p2.getX())) && // estremo destra
																// X
				(Math.abs(b.getY()) <= Math.abs(p2.getY()))) // estremo destra Y
			return true;
		else
			return false;
	}

	@Override
	public boolean contains(P2d p) {
		/**
		 * Se il punto è all'interno del rettangolo
		 */
		if(p.getX()>= a.getX() && p.getX()<=b.getX() &&
				p.getY()>=a.getY() && p.getY()<= b.getY())
			return true;
		else
			return false;

		/**
		 * Implementazione precedente dove ritorno true se clicco su un punto del rettangolo
		 */
//		 if (distance(A, C) + distance(B, C) == distance(A, B))
//		 return true; // C is on the line.
//		 return false; // C is not on the line.

			
//		if (Math.abs(P2d.distance(pointUpperLeft, p))
//				+ Math.abs(P2d.distance(pointUpperRight, p)) == Math.abs(P2d
//				.distance(pointUpperLeft, pointUpperRight))
//				|| Math.abs(P2d.distance(pointUpperRight, p))
//						+ Math.abs(P2d.distance(pointLowerRight, p)) == Math
//						.abs(P2d.distance(pointUpperRight, pointLowerRight))
//				|| Math.abs(P2d.distance(pointLowerRight, p))
//						+ Math.abs(P2d.distance(pointLowerLeft, p)) == Math
//						.abs(P2d.distance(pointLowerRight, pointLowerLeft))
//				|| Math.abs(P2d.distance(pointLowerLeft, p))
//						+ Math.abs(P2d.distance(pointUpperLeft, p)) == Math
//						.abs(P2d.distance(pointLowerLeft, pointUpperLeft)))
//			return true;
//		else
//			return false;

	}

	@Override
	public BBox getBBox() {

		int xupperLeft = a.getX();
		int yupperLeft = a.getY();
		int xbottomRight = b.getX();
		int ybottomRight = b.getY();

		P2d upperLeft = new P2d(xupperLeft, yupperLeft);
		P2d bottomRight = new P2d(xbottomRight, ybottomRight);

		return new BBox(upperLeft, bottomRight);
	}

	@Override
	public String toString() {
		return "Rectangle - PointUpperLeft (" + a.getX() + "-" + a.getY()
				+ ") " + "PointUpperRight (" + b.getX() + "-" + a.getY()
				+ ") " // upperLeft
				+ "PointLowerRight (" + b.getX() + "-" + b.getY() + ") "
				+ "PointLowerLeft (" + a.getX() + "-" + b.getY() + ") ";
	}

}
