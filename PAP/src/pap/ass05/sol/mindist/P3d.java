package pap.ass05.sol.mindist;

/**
 *
 * 2-dimensional point
 * objects are completely state-less
 *
 */
public class P3d implements java.io.Serializable {

    public double x,y,z;

    public P3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public P3d sum(V3d v){
        return new P3d(x + v.x, y + v.y, z + v.z);
    }

    public String toString(){
        return "P3d("+x+","+y+", "+z+")";
    }

}
