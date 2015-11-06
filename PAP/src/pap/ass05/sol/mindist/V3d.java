/*
 *   V2d.java
 *
 * Copyright 2000-2001-2002  aliCE team at deis.unibo.it
 *
 * This software is the proprietary information of deis.unibo.it
 * Use is subject to license terms.
 *
 */
package pap.ass05.sol.mindist;

/**
 *
 * 2-dimensional vector
 * objects are completely state-less
 *
 */
public class V3d implements java.io.Serializable {

    public double x,y,z;

    public V3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public V3d sum(V3d v){
        return new V3d(x + v.x, y + v.y, z + v.z);
    }

    public double abs(){
        return (double)Math.sqrt(x*x+y*y+z*z);
    }

    public V3d getNormalized(){
        double module=(double)Math.sqrt(x*x+y*y+z*z);
        return new V3d(x/module,y/module,z/module);
    }

    public V3d mul(double fact){
        return new V3d(x*fact,y*fact,z*fact);
    }

    public String toString(){
        return "V3d("+x+","+y+","+z+")";
    }
}
