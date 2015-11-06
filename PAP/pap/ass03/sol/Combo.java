package pap.ass03.sol;

import java.util.Arrays;

import pap.ass03.*;

public class Combo implements Shape {

	private Shape[] shapes;
	
	public Combo(Shape[] shapes){
		this.shapes = shapes;
	}
	
	@Override
	public void move(V2d v) {
		Arrays.stream(shapes)
				.forEach(sh -> { sh.move(v);});
	}

	@Override
	public double getPerim() {
		return Arrays.stream(shapes)
					.map(Shape::getPerim)
					.reduce(0.0, (p1,p2) -> p1 + p2);
	}

	@Override
	public boolean contains(P2d p){
		return Arrays.stream(shapes).anyMatch(s -> s.contains(p));
	}

	@Override
	public boolean isInside(BBox bbox) {
		return Arrays.stream(shapes).allMatch(s -> s.isInside(bbox));
	}
	
	
	public String toString(){
		return Arrays.stream(shapes).map(Shape::toString).reduce("",(st,s) -> st.equals("") ? s.toString() : st + "," + s.toString());
	}

	public Shape[] getComponents(){
		return shapes;
	}
	
	@Override
	public BBox getBBox() {
		return Arrays.stream(shapes)
				.map(Shape::getBBox)
				.reduce((b1,b2) -> new BBox(
						new P2d(b1.getUpperLeft().getX() < b2.getUpperLeft().getX() ? b1.getUpperLeft().getX() : b2.getUpperLeft().getX(),
								b1.getUpperLeft().getY() < b2.getUpperLeft().getY() ? b1.getUpperLeft().getY() : b2.getUpperLeft().getY()),
						new P2d(b1.getBottomRight().getX() > b2.getBottomRight().getX() ? b1.getBottomRight().getX() : b2.getBottomRight().getX(),
								b1.getBottomRight().getY() > b2.getBottomRight().getY() ? b1.getBottomRight().getY() : b2.getBottomRight().getY()))).get();
	}
	
	public boolean equals(Object obj){
		if (obj instanceof Combo){
			final Shape[] sl = ((Combo) obj).getComponents();
			if (sl.length == this.shapes.length){
				return Arrays.stream(this.shapes).allMatch(s -> Arrays.stream(sl).anyMatch(s2 -> s2.equals(s)));
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		return toString().hashCode();
	}

}
