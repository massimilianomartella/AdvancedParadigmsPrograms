package pap.ass03.sol;

import java.util.*;

import pap.ass03.sol.P2d;
import pap.ass03.sol.Shape;

public class TestShapeViewer {

	public static void main(String[] args) {
		List<Shape> shapes = Arrays.asList(
				new Line(new P2d(300,10), new P2d(300,230)),
				new Circle(new P2d(100,100),50),
				new Rectangle(new P2d(20,20), new P2d(80,60)),
				new Line(new P2d(35,35), new P2d(200,200)),
				new Combo(new Shape[]{
						new Circle(new P2d(30,150),70),
						new Line(new P2d(30,150), new P2d(250,150)),
						new Rectangle(new P2d(200,100), new P2d(300,150))})
				);
		Viewer viewer = new Viewer(shapes);
		viewer.display();

		/*
		Shape[] shapes2 = {
				new Circle(new P2d(80,60),20),
				new Line(new P2d(5,5), new P2d(40,30)),
				new Rectangle(new P2d(30,10), new P2d(80,60)),
				new Combo(new Shape[]{
						new Circle(new P2d(20,30),10),
						new Line(new P2d(20,40), new P2d(50,40))
				})
		};
		Viewer viewer2 = new Viewer(Arrays.asList(shapes2));
		viewer2.display();
		 */ 
		
		

	}

}
