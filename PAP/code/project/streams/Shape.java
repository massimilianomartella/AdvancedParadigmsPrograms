package code.project.streams;

/**
 * Interface that rappresent a shape in the graphic viewport (0,0)->(w,h)
 */
public interface Shape {
	void move(V2d v);	
	double getPerim();
	boolean isInside(P2d p1, P2d p2);
	boolean contains(P2d p);
	BBox getBBox();
}
