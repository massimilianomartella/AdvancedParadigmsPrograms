package pap.ass03.sol;

/**
 * Interfaccia che rappresenta una figura
 * in una viewport grafica (0,0)-(w,h)
 * 
 * @author aricci
 *
 */
public interface Shape {
	void move(V2d v);	
	double getPerim();
	boolean isInside(BBox box);
	boolean contains(P2d p);
	BBox getBBox();
}
