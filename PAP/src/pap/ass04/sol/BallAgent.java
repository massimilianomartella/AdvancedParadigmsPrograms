package pap.ass04.sol;

import java.util.*;
import pap.ass04.*;

/**
 * Active entity in charge of moving a ball (represented by a "*").
 * 
 * @author aricci
 *
 */
public class BallAgent extends Thread {
    
    private P2d pos;
    private V2d vel;
    private double speed;
    private Boundary bounds;
    private long lastUpdate;
    private int x,y,oldX, oldY;
    private TextLib view;
    private int color;

    public BallAgent(Boundary bounds, int color, P2d pos){
        this.pos = pos;
        view = TextLibFactory.getInstance();
        Random rand = new Random(System.nanoTime());
        double dx = rand.nextDouble();
        vel = new V2d(dx,Math.sqrt(1-dx*dx));
        speed = 2+rand.nextDouble()*10;
        this.bounds = bounds;
        this.color = color;
    }

    public void run() {
    	try {
            lastUpdate = System.currentTimeMillis();
	        while (true){
	            updatePos();
	            updateView();
	            Thread.sleep(10);	
	        }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    /**
     * The position is updated considering the speed and the time
     * elapsed since last update.
     */
    private  void updatePos(){
        long time = System.currentTimeMillis();
        long dt = time - lastUpdate;
        lastUpdate = time;
        pos = pos.sum(vel.mul(speed*dt*0.001));
        if (pos.getX() > bounds.getX1()){
        	pos = new P2d(bounds.getX1(),pos.getY());
        	vel = new V2d(-vel.getX(),vel.getY());
        } else if (pos.getX() < bounds.getX0()){
        	pos = new P2d(bounds.getX0(),pos.getY());
        	vel = new V2d(-vel.getX(),vel.getY());
        } else if (pos.getY() > bounds.getY1()){
        	pos = new P2d(pos.getX(),bounds.getY1());
        	vel = new V2d(vel.getX(),-vel.getY());
        } else if (pos.getY() < bounds.getY0()){
        	pos = new P2d(pos.getX(),bounds.getY0());
        	vel = new V2d(vel.getX(),-vel.getY());
        }
     }

    /**
     * The view is updated by deleting the "*" in the
     * previous position and writing the "*" in the new
     * position.
     */
    private void updateView(){
        oldX = x;
        oldY = y;
        x = (int) pos.getX();
        y = (int) pos.getY();
        view.writeAt(oldX, oldY, " ");
        view.writeAt(x, y, "*",color);	            
    }
  
}
