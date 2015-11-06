package pap.ass03.sol;

import pap.ass03.*;

public class Rectangle implements Shape {

	private P2d v1;
	private P2d v2;
	
	public Rectangle(P2d v1, P2d v2){
		this.v1 = v1;
		this.v2 = v2;
	}
	
	@Override
	public void move(V2d v) {
		v1 = v1.sum(v);
		v2 = v2.sum(v);
	}

	@Override
	public double getPerim() {
		return 2*(v2.getX()-v1.getX())+2*(v2.getY()-v1.getY());
	}

	@Override
	public boolean contains(P2d p){
		return p.getX() >= v1.getX() && p.getX() <= v2.getX() &&
			   p.getY() >= v1.getY() && p.getY() <= v2.getY();
	}

	@Override
	public boolean isInside(BBox bbox) {
		P2d p1 = bbox.getUpperLeft();
		P2d p2 = bbox.getBottomRight();
		return v1.getX() >= p1.getX() && v1.getY() <= p1.getY() 
				&& v2.getX() <= p2.getX() && v2.getY() >= p2.getY();
	}
	
	
	public String toString(){
		return "Rectangle("+v1+","+v2+")";
	}

	@Override
	public BBox getBBox() {
		return new BBox(v1,v2);
	}

	public P2d[] getVertices(){
		return new P2d[]{v1,new P2d(v2.getX(),v1.getY()), v2, new P2d(v1.getX(),v2.getY())};
	}
	
	public boolean equals(Object obj){
		return (obj instanceof Rectangle) 
				&& ((Rectangle) obj).v1.equals(v1) 
				&& ((Rectangle) obj).v2.equals(v2);
	}
	
	public int hashCode() {
		return toString().hashCode();
	}
	
}
