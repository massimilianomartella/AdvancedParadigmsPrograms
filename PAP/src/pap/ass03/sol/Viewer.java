package pap.ass03.sol;

import javax.swing.*;
import pap.ass03.*;
import static java.util.stream.Collectors.toList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

class ViewerPanel extends JPanel  {
	private List<Shape> shapes;
	private List<Shape> selected;
	
	public ViewerPanel(List<Shape> shapeList){
		this.shapes = shapeList;
		selected = new ArrayList<Shape>();
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				selected = shapes.stream()
					.filter(s -> s.contains(new P2d(e.getX(),e.getY())))
					.collect(toList());
				repaint();
			}
		});
	}
	
	public void updateShapes(List<Shape> shapes){
		this.shapes = shapes;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		          RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
		          RenderingHints.VALUE_RENDER_QUALITY);
		       		
		g2.setColor(Color.BLACK);
		drawShapes(g2,shapes);
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(4));
		drawShapes(g2,selected);	
	}

	private void drawShapes(Graphics2D g2, List<Shape> shapes){
		shapes.stream().forEach(s -> {
			if (s instanceof Line){
				P2d[] verts = ((Line)s).getVertices();
				g2.drawLine(verts[0].getX(), verts[0].getY(), verts[1].getX(), verts[1].getY());
			} else if (s instanceof Circle){
				Circle c = (Circle) s;
				g2.drawOval(c.getCenter().getX()-c.getRadius(), c.getCenter().getY()-c.getRadius(), c.getRadius()*2, c.getRadius()*2);
			} else if (s instanceof Rectangle){
				P2d[] verts = ((Rectangle)s).getVertices();
				java.util.stream.IntStream.range(0, verts.length).forEach(i -> {
					g2.drawLine(verts[i].getX(), verts[i].getY(), verts[(i+1)%4].getX(), verts[(i+1)%4].getY());
				});
			} else if (s instanceof Combo){
				drawShapes(g2,Arrays.asList(((Combo)s).getComponents()));
			}
		});
	}	
}

public class Viewer extends JFrame implements ShapeViewer {
	
	private ViewerPanel panel;
	
	public Viewer(List<Shape> shapes){
		setTitle("Viewer");
		setSize(400,400);
		setPreferredSize(new Dimension(400,400));
		panel = new ViewerPanel(shapes);
		this.getContentPane().add(panel);
		pack();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);		
			}
		});
	}
	
	public void display(){
		setVisible(true);
	}
	
	public void update(List<Shape> shapes){
		panel.updateShapes(shapes);
		repaint();
	}
}
