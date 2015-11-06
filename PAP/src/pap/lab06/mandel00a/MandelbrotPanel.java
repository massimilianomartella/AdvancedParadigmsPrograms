package pap.lab06.mandel00a;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class MandelbrotPanel extends JPanel {
	
	private int w;
	private int h;
	private int maxIteration;
	
	public MandelbrotPanel(int w, int h){
		this.w = w;
		this.h = h;
		maxIteration = 1000;
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);   
        Graphics2D g2 = (Graphics2D)g;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        //Complex c0 = new Complex(0,0);
        //double diam = 4;  

        Complex c0 = new Complex(-0.75,0.1);
        double diam = 0.004;
        
        //Complex c0 = new Complex(-0.7485,0.0505);
        //double diam = 0.000004;
        
        double diamX = diam;
        double diamY = diamX*h/w;
        
        try {
    	    double cx, cy;
    	    double dx = diamX / w;
    	    double dy = diamY / h;
    	    double radiusX = diamX*0.5;
    	    double radiusY = diamY*0.5;
    		for (int x = 0; x < w; x++ ){
    			for (int y = 0; y < h; y++){
    				cx = x*dx + c0.re() - radiusX;
    				cy = c0.im()+radiusY - y*dy; 
    				double color = computeColor(cx,cy);
    				int rgb = (int)(color*255);
    				image.setRGB(x, y, rgb+(rgb<<8)+(rgb<<16));
    			}
    		}		

        	g2.drawImage(image, 0, 0, null);   
        } catch (Exception ex){
        	ex.printStackTrace();
        }
    }
	
	private double computeColor(double x0, double y0){
		int iteration = 0;		
		double x = x0;
		double y = y0;
		while ( x*x + y*y <= 4 &&  iteration < maxIteration ){
		    double xtemp = x*x - y*y + x0;
		    y = 2*x*y + y0;
		    x = xtemp;
		    iteration++;
		  }		 
		  if ( iteration == maxIteration ){
			  return 0;
		  } else {
			  return 1.0-0.001*iteration;
		  }
	}
	
}
