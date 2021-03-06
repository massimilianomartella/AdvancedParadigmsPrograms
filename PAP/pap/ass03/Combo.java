package pap.ass03;

import java.util.List;

public class Combo implements Shape {

	List<Shape> listShape;

	public Combo(List<Shape> s) {
		listShape = s;
	}

	@Override
	public void move(V2d v) {
		// data una lista di figure e un vettore dv, trasla ogni figura del
		// vettore specificato
		listShape.forEach(s -> s.move(v));

	}

	@Override
	public double getPerim() {
		// data una lista di figure, determina la somma di tutti i perimetri
		return listShape.stream().mapToDouble(s -> s.getPerim()).sum();
	}

	@Override
	public boolean isInside(P2d p1, P2d p2) {
		// data una lista di figure e un bounding box, computa la lista delle
		// figure contenute nel bounding box p1 p2
		return listShape.stream().filter(s -> s.isInside(p1, p2)).findFirst()
				.isPresent();
	}

	@Override
	public boolean contains(P2d p) {
		// data una lista di figure e un punto, verifica se esiste una figura
		// che contiene il punto
		return listShape.stream().filter(s -> s.contains(p)).findFirst()
				.isPresent();
	}

	@Override
	public BBox getBBox() {
		//public BBox(P2d upperLeft, P2d bottomRight)
		int x1 = listShape.stream()
				.map(s -> s.getBBox().getBottomRight().getX())
				.min((p1, p2) -> Math.abs(p2) - Math.abs(p1))
				.get();
		int y1 = listShape.stream()
				.map(s -> s.getBBox().getBottomRight().getY())
				.max((p1, p2) -> Math.abs(p1) + Math.abs(p2))
				.get();
		
		
		int x2 = listShape.stream()
				.map(s -> s.getBBox().getUpperLeft().getX())
				.max((p1, p2) -> Math.abs(p1) + Math.abs(p2))
				.get();
		int y2 = listShape.stream()
				.map(s -> s.getBBox().getUpperLeft().getY())
				.min((p1, p2) -> Math.abs(p2) - Math.abs(p1))
				.get();
		
		return new BBox(new P2d(Math.abs(x2), Math.abs(y2)), new P2d(Math.abs(x1), Math.abs(y1)));
	}

	@Override
	public String toString() {
		return listShape.toString();
	}

	public List<Shape> getComponents() {
		return listShape;
	}

}
