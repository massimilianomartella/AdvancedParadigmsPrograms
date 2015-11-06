package code.project.streams;

import java.util.List;
import java.util.OptionalDouble;

import static java.util.stream.Collectors.toList;

public class Utils {
	public static void moveShapes(List<Shape> listShape, V2d v) {
		listShape.forEach(s -> s.move(v));
	}

	public static List<Shape> inBBox(List<Shape> listShape, P2d p0, P2d p1) {
		return listShape.stream()
				.filter(s -> s.isInside(p0, p1))
				.collect(toList());
	}

	public static OptionalDouble maxPerim(List<Shape> listShape) {
		return listShape.stream().mapToDouble(s -> s.getPerim()).max();
	}

	public static Shape shapeWithMaxPerim(List<Shape> listShape) {
		return listShape.stream()
				.max((p1, p2) -> Double.compare(p1.getPerim(), p2.getPerim()))
				.get();
	}

	public static List<Shape> sortShapesByX(List<Shape> listShape) {
		return listShape
				.stream()
				.sorted((p1, p2) -> p1.getBBox().getUpperLeft().getX()
						- p2.getBBox().getUpperLeft().getX()).collect(toList());
	}

	public static Boolean contains(List<Shape> listShape, P2d p) {
		return listShape.stream().filter(s -> s.contains(p)).findFirst()
				.isPresent();
	}

	public static List<Shape> getContaining(List<Shape> listShape, P2d p) {
		return listShape.stream().filter(s -> s.contains(p)).collect(toList());
	}

	public static void logAll(List<Shape> listShape) {
		listShape.forEach(System.out::println);
	}

}
