package pap.ass03.sol;

import pap.ass03.*;

public class Circle implements Shape {

	private P2d pos;
	private int radius;
	
	public Circle(P2d pos, int radius){
		this.pos = pos;
		this.radius = radius;
	}

	@Override
	public void move(V2d v) {
		pos = pos.sum(v);
	}

	@Override
	public double getPerim() {
		return radius*2*Math.PI;
	}

	@Override
	public boolean contains(P2d p){
		return new V2d(p,pos).module() <= radius;
	}
	
	public String toString(){
		return "Circle("+pos+","+radius+")";
	}

	@Override
	public boolean isInside(BBox box) {
		P2d p1 = box.getUpperLeft();
		P2d p2 = box.getBottomRight();		
		return pos.getX() - radius >= p1.getX() && pos.getX() + radius <= p2.getX() &&
				   pos.getY() - radius <= p1.getY() && pos.getY() + radius >= p2.getY();
	}

	@Override
	public BBox getBBox() {
		return new BBox(new P2d(pos.getX() - radius, pos.getY() - radius), 
				        new P2d(pos.getX() + radius, pos.getY() + radius));
	}
	
	public int getRadius(){
		return radius;
	}
	
	public P2d getCenter(){
		return pos;
	}
	
	public boolean equals(Object obj){
		return (obj instanceof Circle) 
				&& ((Circle) obj).pos.equals(pos) 
				&& ((Circle) obj).radius == radius;
	}

	public int hashCode() {
		return toString().hashCode();
	}
}
