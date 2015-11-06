package pap.ass04.sol;

/**
 *
 * 2-dimensional point
 * objects are completely state-less
 *
 */
public class P2d implements java.io.Serializable {

    private double x,y;

    public P2d(double x,double y){
        this.x=x;
        this.y=y;
    }

    public P2d sum(V2d v){
        return new P2d(x+v.getX(),y+v.getY());
    }

    public V2d sub(P2d v){
        return new V2d(x-v.x,y-v.y);
    }

    public double getX(){
    	return x;
    }
    
    public double getY(){
    	return y;
    }
    
    public String toString(){
        return "P2d("+x+","+y+")";
    }

}
