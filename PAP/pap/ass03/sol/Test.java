package pap.ass03.sol;

import java.util.*;
import static pap.ass03.sol.Utils.*;
import pap.ass03.*;

public class Test {

	public static void main(String[] args) {

		List<Shape> shapes = Arrays.asList(
				new Circle(new P2d(80,60),20),
				new Line(new P2d(5,5), new P2d(40,30)),
				new Rectangle(new P2d(30,10), new P2d(80,60)),
				new Combo(new Shape[]{
						new Circle(new P2d(20,30),10),
						new Line(new P2d(20,40), new P2d(50,40))
				}));
		

		assert(contains(shapes, new P2d(80,60)));
		assert(!contains(shapes, new P2d(80,20)));		
		assert(inBBox(shapes, new BBox(new P2d(0,0), new P2d(100,100))));	
		assert(!inBBox(shapes, new BBox(new P2d(40,40), new P2d(100,100))));			
		assert(maxPerim(shapes).isPresent() && maxPerim(shapes).get() == 200);

		log("Assertions OK.");

		log("Log all...");
		logAll(shapes);

		P2d p = new P2d(75,55);
		log("Shapes containing "+p);
		logAll(getContaining(shapes, p));
		
		V2d dv = new V2d(10,5);
		log("moving shapes of "+dv+" ...");
		moveShapes(shapes, dv);
		logAll(shapes);
		
		log("Shape with max perim: "+Utils.shapeWithMaxPerim(shapes));
		
		log("Sorted:");
		
		ArrayList<Shape> updatableList = new ArrayList<>();
		updatableList.addAll(shapes);
		sortShapeByX(updatableList);
		logAll(updatableList);
	}
	
	static private void log(String msg){
		System.out.println("[TEST] "+msg);
	}
}
