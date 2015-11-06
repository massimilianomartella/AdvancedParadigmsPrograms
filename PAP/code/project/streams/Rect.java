package code.project.streams;

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
		if ((Math.abs(a.getX()) <= Math.abs(p1.getX())) && // left extrme of X
				(Math.abs(a.getY()) >= Math.abs(p1.getY())) && // left extreme of Y
				(Math.abs(b.getX()) >= Math.abs(p2.getX())) && // right extreme of X
				(Math.abs(b.getY()) <= Math.abs(p2.getY()))) // right extreme of Y
			return true;
		else
			return false;
	}

	@Override
	public boolean contains(P2d p) {
		/**
		 * If the point is inside at graphic viewport
		 */
		if(p.getX()>= a.getX() && p.getX()<=b.getX() &&
				p.getY()>=a.getY() && p.getY()<= b.getY())
			return true;
		else
			return false;
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
