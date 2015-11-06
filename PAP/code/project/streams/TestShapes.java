package code.project.streams;

import java.util.Arrays;
import java.util.List;
public class TestShapes {

	public static void main(String args[]) {

		final List<Shape> listShape = Arrays.asList(new Line(0, 0, 0, 7),
												 new Circle(5, 5, 3),
												 new Line (0,0,0,8),
												 new Rect(1, 1, 3, 5)
									);
		
		Combo shapes = new Combo(listShape);
		System.out.println(shapes);
		shapes.move(new V2d(1, 1));
		System.out.println(shapes.getBBox());
		
		Utils.logAll(listShape);
		Utils.moveShapes(listShape, new V2d(1, 1));
		Utils.logAll(listShape);
		
		System.out.println("inBBox: " + Utils.inBBox(listShape, new P2d(8, 1), new P2d(1, 8)));

		System.out.println("maxPerim: " + Utils.maxPerim(listShape));
		
		System.out.println("shapeWithMaxPerim: " + Utils.shapeWithMaxPerim(listShape));
		
		System.out.println("sortShapesByX: " + Utils.sortShapesByX(listShape));

		System.out.println("contains: " + Utils.contains(listShape, new P2d(1, 1)) );

		System.out.println("getContaining: " + Utils.getContaining(listShape, new P2d(2, 1)));
		
		Utils.logAll(listShape);
		
	}

}
