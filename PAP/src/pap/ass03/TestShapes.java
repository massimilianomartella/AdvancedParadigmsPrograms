package pap.ass03;

import java.util.Arrays;
import java.util.List;
/* 
 * Definire la classe Utils con i seguenti metodi statici, 
 * utilizzando opportunamente espressioni Lambda e Stream nella loro implementazione 
 * 
 * moveShapes
 * data una lista di figure e un vettore dv, trasla ogni figura del vettore specificato 
 * 
 * inBBox
 * data una lista di figure e un bounding box, computa la lista delle figure contenute nel bounding box p0 p1 
 * 
 * maxPerim
 * data una lista di figure, determina il perimetro maggiore
 * 
 * shapeWithMaxPerim
 * data una lista di figure, determina la figura con perimetro maggiore
 * 
 * sortShapesByX
 * data una lista di figure, le ordina lungo l'asse x
 * 
 * contains
 * data una lista di figure e un punto, verifica se esiste una figura che contiene il punto
 * 
 * getContaining
 * data una lista di figure e un punto p, computa la lista delle figure che contengono il punto
 * 
 * logAll
 * data una lista di figure, le stampa in uscita
 * 
 * */
public class TestShapes {

	public static void main(String args[]) {

		final List<Shape> listShape = Arrays.asList(new Line(0, 0, 0, 7), //7
												 new Circle(5, 5, 3),	//20
												 new Line (0,0,0,8),	//8
												 new Rect(1, 1, 3, 5)	//12
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
