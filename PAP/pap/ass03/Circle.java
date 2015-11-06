package pap.ass03;

public class Circle implements Shape {

	P2d center;
	int radius;

	public Circle(int x, int y, int rad) {
		center = new P2d(x, y);
		radius = rad;
	}

	@Override
	public void move(V2d v) {
		center = center.sum(v);
	}

	@Override
	public double getPerim() {
		return 2 * Math.PI * radius;
	}

	@Override
	public boolean isInside(P2d p1, P2d p2) {

		int a1 = Math.abs(this.getBBox().getBottomRight().getX());
		int b1 = Math.abs(this.getBBox().getBottomRight().getY());

		int a2 = Math.abs(this.getBBox().getUpperLeft().getX());
		int b2 = Math.abs(this.getBBox().getUpperLeft().getY());

		if ((a1 <= Math.abs(p1.getX())) && // estremo sinistra X
				(b1 >= Math.abs(p1.getY())) && // estremo sinistra Y
				(a2 >= Math.abs(p2.getX())) && // estremo destra X
				(b2 <= Math.abs(p2.getY()))) // estremo destra Y
			return true;
		else
			return false;

	}

	@Override
	public boolean contains(P2d p) {
		/*
		 * In general, x and y must satisfy (x - center_x)^2 + (y - center_y)^2
		 * < radius^2.
		 */

		double dist = Math.sqrt((Math.abs((p.getX() - center.getX())
				* (p.getX() - center.getX())))
				+ Math.abs(((p.getY() - center.getY()) * (p.getY() - center
						.getY()))));
		if (dist <= radius)
			return true;
		else
			return false;

	}

	@Override
	public BBox getBBox() {
		int xupperLeft = center.getX() - radius;
		int yupperLeft = center.getY() - radius;
		int xbottomRight = center.getX() + radius;
		int ybottomRight = center.getY() + radius;

		P2d upperLeft = new P2d(xupperLeft, yupperLeft);
		P2d bottomRight = new P2d(xbottomRight, ybottomRight);

		return new BBox(upperLeft, bottomRight);
	}

	@Override
	public String toString() {
		return "Circle - Center(" + center.getX() + "-" + center.getY()
				+ ") Radius: " + radius + ")";
	}
}
