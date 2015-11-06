package pap.lab06.mandel00a;

import javax.swing.*;

public class MandelbrotView extends JFrame {

	public MandelbrotView(int w, int h){
		super("Mandelbrot Viewer");
		setSize(w, h);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel = new MandelbrotPanel(w,h); 
		panel.setSize(w,h);
		getContentPane().add(panel);
	}
	
}
