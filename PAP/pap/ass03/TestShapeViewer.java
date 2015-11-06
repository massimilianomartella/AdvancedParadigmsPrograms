package pap.ass03;

import java.util.Arrays;
import java.util.List;

public class TestShapeViewer {

	public static void main(String[] args) {

		ShapeFrame viewer = new ShapeFrame();
		viewer.setVisible(true);

		final List<Shape> listShapeCombo = Arrays.asList(
				new Rect (200,140,250,160),
				new Rect (250,190,290,210)
				);
				
		final List<Shape> listShape = Arrays.asList(
				new Line(60, 10, 60, 100),
				new Circle(120, 100, 80), 
				new Rect(10, 10, 30, 50), 
				new Rect(50, 70, 80, 90),
				new Combo(listShapeCombo)
		);
		
//		List<Shape> listShape = Arrays.asList(
//				new Line(new P2d(300,10), new P2d(300,230)),
//				new Circle(new P2d(100,100),50),
//				new Rectangle(new P2d(20,20), new P2d(80,60)),
//				new Line(new P2d(35,35), new P2d(200,200)),
//				new Combo(new Shape[]{
//						new Circle(new P2d(30,150),70),
//						new Line(new P2d(30,150), new P2d(250,150)),
//						new Rectangle(new P2d(200,100), new P2d(300,150))})
//				);

		viewer.update(listShape);
		System.out.println("inBBox: " + Utils.inBBox(listShape, new P2d(1, 1), new P2d(100, 100)));

		System.out.println("maxPerim: " + Utils.maxPerim(listShape));
		
		System.out.println("shapeWithMaxPerim: " + Utils.shapeWithMaxPerim(listShape));
		
		System.out.println("sortShapesByX: " + Utils.sortShapesByX(listShape));

		System.out.println("contains: " + Utils.contains(listShape, new P2d(10, 10)) );

		System.out.println("getContaining: " + Utils.getContaining(listShape, new P2d(67, 83)));
		
		Utils.logAll(listShape);
		
		// test combo
		// final List<Shape> listShape2 = Arrays.asList(new Line(80, 30, 90,
		// 130),
		// new Circle(150, 130, 100), new Rect(50, 50, 70, 90)
		//
		// );
		// Combo shapes = new Combo(listShape2);
		// viewer.update(shapes.listShape);

	}
}