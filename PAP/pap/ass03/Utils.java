package pap.ass03;

import java.util.List;
import java.util.OptionalDouble;

import static java.util.stream.Collectors.toList;

public class Utils {
	public static void moveShapes(List<Shape> listShape, V2d v) {
		// data una lista di figure e un vettore dv, trasla ogni figura del
		// vettore specificato
		listShape.forEach(s -> s.move(v));
	}

	public static List<Shape> inBBox(List<Shape> listShape, P2d p0, P2d p1) {
		// data una lista di figure e un bounding box, computa la lista delle
		// figure contenute nel bounding box p0 p1
		return listShape.stream().filter(s -> s.isInside(p0, p1))
				.collect(toList());
	}

	public static OptionalDouble maxPerim(List<Shape> listShape) {
		// data una lista di figure, determina il perimetro maggiore
		return listShape.stream().mapToDouble(s -> s.getPerim()).max();
	}

	public static Shape shapeWithMaxPerim(List<Shape> listShape) {
		// data una lista di figure, determina la figura con perimetro maggiore
		return listShape.stream()
				.max((p1, p2) -> Double.compare(p1.getPerim(), p2.getPerim()))
				.get();

	}

	public static List<Shape> sortShapesByX(List<Shape> listShape) {
		// data una lista di figure, le ordina lungo l'asse x
		return listShape
				.stream()
				.sorted((p1, p2) -> p1.getBBox().getUpperLeft().getX()
						- p2.getBBox().getUpperLeft().getX()).collect(toList());
	}

	public static Boolean contains(List<Shape> listShape, P2d p) {
		// data una lista di figure e un punto, verifica se esiste una figura
		// che contiene il punto
		return listShape.stream().filter(s -> s.contains(p)).findFirst()
				.isPresent();
	}

	public static List<Shape> getContaining(List<Shape> listShape, P2d p) {
		// data una lista di figure e un punto p, computa la lista delle figure
		// che contengono il punto
		return listShape.stream().filter(s -> s.contains(p)).collect(toList());
	}

	public static void logAll(List<Shape> listShape) {
		// data una lista di figure, le stampa in uscita

		// Per come era stato inteso l'esercizio, era sufficiente un log
		// classico, inteso come descrizione testuale ([Linea: puntoA - puntoB,
		// ... etc.)
		listShape.forEach(System.out::println);
	}

}
