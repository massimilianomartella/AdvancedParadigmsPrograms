package pap.ass03.sol;

import pap.ass03.*;

public class Triangle implements Shape {

	private P2d v1;
	private P2d v2;
	private P2d v3;
	
	public Triangle(P2d v1, P2d v2, P2d v3){
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}
	
	@Override
	public void move(V2d v) {
		v1 = v1.sum(v);
		v2 = v2.sum(v);
		v3 = v3.sum(v);
	}

	@Override
	public double getPerim() {
		return new V2d(v1,v2).module();
	}

	@Override
	public boolean contains(P2d p){
		return false;
	}

	@Override
	public boolean isInside(BBox bbox) {
		return Triangle.isInside(bbox, v1) && 
				Triangle.isInside(bbox, v2) && 
				Triangle.isInside(bbox, v3);
	}
	
	
	public String toString(){
		return "Triangle("+v1+","+v2+","+v3+")";
	}

	@Override
	public BBox getBBox() {
		return new BBox(v1,v2);
	}

	private static boolean isInside(BBox bbox, P2d p){
		P2d p1 = bbox.getUpperLeft();
		P2d p2 = bbox.getBottomRight();
		return p.getX() >= p1.getX() && p.getY() <= p1.getY() 
				&& p.getX() <= p2.getX() && p.getY() >= p2.getY();
		
	}
}
