package pap.ass03.sol;

import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import pap.ass03.*;

public class Utils {
	
	public static void moveShapes(List<Shape> shapes, V2d dv){
		shapes.stream().forEach(s -> { s.move(dv); });
	}
	
	public static boolean inBBox(List<Shape> shapes, BBox box){
		return shapes.stream().allMatch(s -> s.isInside(box));
	}

	public static Optional<Double> maxPerim(List<Shape> shapes){
		return shapes.stream().map(Shape::getPerim).reduce((p1,p2) -> p1 > p2 ? p1 : p2);
	}

	public static Optional<Shape> shapeWithMaxPerim(List<Shape> shapes){
		return shapes.stream()
					.map(s -> new ShapePerim(s,s.getPerim()))
					.sorted((ShapePerim o1, ShapePerim o2) -> -Double.compare(o1.getPerim(), o2.getPerim())) /* "-" because of the decreasing order */
					.limit(1)
					.map(sp -> sp.getSh())
					.reduce((s1,s2) -> s1);
	}
	
	public static void sortShapeByX(List<Shape> shapes){
		List<Shape> l = shapes.stream()
			.sorted((Shape s1, Shape s2) -> Integer.compare(s1.getBBox().getUpperLeft().getX(), s2.getBBox().getUpperLeft().getX()))
			.collect(toList());
		shapes.clear();
		shapes.addAll(l);
	}
	
	public static boolean contains(List<Shape> shapes, P2d p){
		return shapes.stream().anyMatch(s -> s.contains(p));
	}
	
	public static List<Shape> getContaining(List<Shape> shapes, P2d p){
		return shapes.stream().filter(s -> s.contains(p)).collect(toList());
	}
	
	public static void logAll(List<Shape> shapes){
		shapes.stream().forEach(System.out::println);
	}

	static class ShapePerim {
		private Shape sh;
		private double perim;
		public ShapePerim(Shape sh, double perim) {
			super();
			this.sh = sh;
			this.perim = perim;
		}
		public Shape getSh() {
			return sh;
		}
		public double getPerim() {
			return perim;
		}
	}
}
