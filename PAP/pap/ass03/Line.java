package pap.ass03;

public class Line implements Shape {

	private P2d a, b;

	public Line(int x0, int y0, int x1, int y1) {
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
		return Math.abs(P2d.distance(a, b));
	}

	@Override
	public boolean isInside(P2d p1, P2d p2) {
		if ((Math.abs(a.getX()) <= Math.abs(p1.getX())) && // estremo sinistra X
				(Math.abs(a.getY()) >= Math.abs(p1.getY())) && // estremo
																// sinistra Y
				(Math.abs(b.getX()) <= Math.abs(p2.getX())) && // estremo destra
																// X
				(Math.abs(b.getY()) <= Math.abs(p2.getY()))) // estremo destra Y
			return true;
		else
			return false;
	}

	@Override
	public boolean contains(P2d p) {
		// if (distance(A, C) + distance(B, C) == distance(A, B))
		// return true; // C is on the line.
		// return false; // C is not on the line.

		if (P2d.distance(a, p) + P2d.distance(b, p) == P2d.distance(a, b))
			return true;
		else
			return false;
	}

	@Override
	public BBox getBBox() {
		// prendo xminore e ymaggiore come estremo upper left
		// prendo xmaggiore e yminore come estremo bottom right
		int xupperLeft = Math.min(a.getX(), b.getX());
		int yupperLeft = Math.max(a.getY(), b.getY());
		int xbottomRight = Math.max(a.getX(), b.getX());
		int ybottomRight = Math.min(a.getY(), b.getY());

		P2d upperLeft = new P2d(xupperLeft, yupperLeft);
		P2d bottomRight = new P2d(xbottomRight, ybottomRight);

		return new BBox(upperLeft, bottomRight);
	}

	@Override
	public String toString() {
		return "Line - Point a(" + a.getX() + "-" + a.getY() + ") Point b("
				+ b.getX() + "-" + b.getY() + ")";
	}
	
	
	
}
