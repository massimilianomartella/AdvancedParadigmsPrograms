package pap.ass03.sol;

import pap.ass03.*;

public class Line implements Shape {

	private P2d v1;
	private P2d v2;
	
	public Line(P2d v1, P2d v2){
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
		return new V2d(v1,v2).module();
	}

	@Override
	public boolean contains(P2d p){
		/*
		V2d v = new V2d(v2.getX()-v1.getX(),v2.getY()-v1.getY());
		V2d dv0 = new V2d(p.getX()-v1.getX(), p.getY()-v1.getY());
		if (v.getX() != 0){
			double t = dv0.getX()/v.getX();
			if (t >= 0 && t <=1){
				if (v.getY()!=0){
					double t1 = dv0.getY()/v.getY();
					return Math.abs(t1-t) < 0.001;
				} else {
					return  (p.getY() == v1.getY()) && 
							(p.getX() >= Integer.min(v1.getX(), v2.getX()) && 
							(p.getX() <= Integer.max(v1.getX(), v2.getX())));
				}
			} else {
				return false;
			}
		} else {
			return  (p.getX() == v1.getX()) && 
					(p.getY() >= Integer.min(v1.getY(), v2.getY()) && 
					(p.getY() <= Integer.max(v1.getY(), v2.getY())));
		}
		*/
		if (p.getY() >= Integer.min(v1.getY(), v2.getY()) && p.getY() <= Integer.max(v1.getY(), v2.getY()) &&
		    p.getX() >= Integer.min(v1.getX(), v2.getX()) && p.getX() <= Integer.max(v1.getX(), v2.getX())) {
			
			double dx = (v2.getX()-v1.getX());
			if (dx != 0){
				double dy = (v2.getY()-v1.getY()); 
				double m = dy/dx;
				double dx1 = (p.getX() - v1.getX());
				double dy1 = (p.getY() - v1.getY());
				double m1 = dy1/dx1;
				return Math.abs(m-m1) < 0.01;
			} else {
				return  (p.getX() == v1.getX()) ;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean isInside(BBox bbox) {
		P2d p1 = bbox.getUpperLeft();
		P2d p2 = bbox.getBottomRight();
		return v1.getX() >= p1.getX() && v1.getY() <= p1.getY() 
				&& v2.getX() <= p2.getX() && v2.getY() >= p2.getY();
	}
	
	
	public String toString(){
		return "Line("+v1+","+v2+")";
	}

	@Override
	public BBox getBBox() {
		return new BBox(v1,v2);
	}
	
	public P2d[] getVertices(){
		return new P2d[]{v1,v2};
	}

	public boolean equals(Object obj){
		return (obj instanceof Line) 
				&& ((Line) obj).v1.equals(v1) 
				&& ((Line) obj).v2.equals(v2);
	}
	
	public int hashCode() {
		return toString().hashCode();
	}
	
	
}
