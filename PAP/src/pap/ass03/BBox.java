package pap.ass03;

/**
 * Class representing a bounding box
 */
public class BBox {
	
	private P2d upperLeft,bottomRight;
	
	/**
<<<<<<< HEAD
	 * Build a bbox 123
=======
	 * Build a bbox 
>>>>>>> refs/remotes/origin/master
	 * 
	 * @param upperLeft -- upper left-corner
	 * @param bottomRight -- bottom-right corner
	 */
	public BBox(P2d upperLeft, P2d bottomRight){
		this.upperLeft = upperLeft; 
		this.bottomRight = bottomRight;
	}
	
	public P2d getUpperLeft(){
		return upperLeft;
	}
	
	public P2d getBottomRight(){
		return bottomRight;
	}
	
	@Override
	public String toString() {
		
		return "BBox: upperLeft" + upperLeft + " - bottomRight" + bottomRight;
	}

}
