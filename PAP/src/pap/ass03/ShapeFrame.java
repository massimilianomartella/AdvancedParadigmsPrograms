package pap.ass03;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

import javax.swing.JFrame;
import javax.swing.JPanel;


class Surface extends JPanel implements MouseListener {

	/**
	 * 
	 */

	private final Boolean selected = true;
	private final Boolean unselected = false;

	private static final long serialVersionUID = 1L;

	private List<Shape> shapes;
	private List<Shape> shapesSelected;
	private Random rand = new Random();
	private Color colorCircle = Color.black, colorLine = Color.black,
			colorRect = Color.black, colorCombo = Color.black;
	P2d p = null;
	private boolean click;

	// costruttore a cui aggiungo Mouse Listener per l'evento mousePressed
	public Surface(List<Shape> shapes) {
		this.shapes = shapes;
		addMouseListener(this);
	}

	// metodo per disegnare le shape sulla viewport grafica
	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		printShape(shapes, g2d, unselected);
		if (click)
			printShape(shapesSelected, g2d, selected);

	}

	private void printShape(List<Shape> shapes, Graphics2D g2d, Boolean defCol) {
		shapes.stream().forEach(s -> {
			if (s instanceof Line) {
				int x0 = ((Line) s).getBBox().getUpperLeft().getX();
				int y0 = ((Line) s).getBBox().getUpperLeft().getY();
				int x1 = ((Line) s).getBBox().getBottomRight().getX();
				int y1 = ((Line) s).getBBox().getBottomRight().getY();

				// all'inizio il colore è sempre nero, poi viene modificato
				// tutte le volte che faccio un click
				// sulla figura visualizzata (repaint() nel metodo
				// MousePressed)
				if (defCol) {
					g2d.setColor(colorLine);
					g2d.setStroke(new BasicStroke(4));
				} else
					g2d.setColor(Color.black);

				// disegno la Linea tramite il metodo fornito dall'istanza
				// Graphics2D di java
				g2d.drawLine(x0, y0, x1, y1);

			} else if (s instanceof Rect) {

				int x0 = ((Rect) s).getBBox().getUpperLeft().getX();
				int y0 = ((Rect) s).getBBox().getUpperLeft().getY();
				int x1 = ((Rect) s).getBBox().getBottomRight().getX();
				int y1 = ((Rect) s).getBBox().getBottomRight().getY();

				int width = Math.abs(x1 - x0);
				int height = Math.abs(y1 - y0);
				if (defCol) {
					g2d.setColor(colorRect);
					g2d.setStroke(new BasicStroke(4));
				} else
					g2d.setColor(Color.black);
				g2d.drawRect(x0, y0, width, height);

			} else if (s instanceof Circle) {
				int radius = ((Circle) s).radius;
				int x = ((Circle) s).center.getX() - radius;
				int y = ((Circle) s).center.getY() - radius;
				int width = radius * 2;
				int height = radius * 2;
				if (defCol) {
					g2d.setColor(colorCircle);
					g2d.setStroke(new BasicStroke(4));
				}

				else
					g2d.setColor(Color.black);
				g2d.drawOval(x, y, width, height);

			}

			else if (s instanceof Combo) {
				if (defCol) {
					g2d.setColor(colorCombo);
					g2d.setStroke(new BasicStroke(4));
				} else
					g2d.setColor(Color.black);
				printShape(((Combo) s).getComponents(), g2d, defCol);

			}
		});
	}

	public void update(List<Shape> shapes) {
		this.shapes = shapes;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// intercetto le coordinate del mouse
		int x = e.getX();
		int y = e.getY();

		click = true;

		// Stampo le coordinate su console per effettuare test
		System.out.println(">> (x,y)=(" + x + "," + y + ")");
		P2d p = new P2d(x, y);

		shapesSelected = new ArrayList<Shape>();
		shapesSelected = shapes.stream().filter(q -> q.contains(new P2d(x, y)))
				.collect(toList());

		// Per ogni shape presente nella lista shapes
		shapesSelected.stream().forEach(s -> {
			// controllo se il punto è all'interno della shape ed è una istanza
			// di Line, Rect o Circle
				if (s.contains(p) && s instanceof Line) {
					// cambio il colore in modo casuale
					colorLine = getRandomColor();
					// stampo su console
					System.out.println("Line clicked!");
					// ridisegno la viewport
					repaint();
				}

				else if (s.contains(p) && s instanceof Rect) {
					colorRect = getRandomColor();
					System.out.println("Rect clicked!");
					repaint();
				}

				else if (s.contains(p) && s instanceof Circle) {
					colorCircle = getRandomColor();
					System.out.println("Circle clicked!");
					repaint();
				}

				else if (s.contains(p) && s instanceof Combo) {
					colorCombo = getRandomColor();
					System.out.println("Combo clicked");
					repaint();
				}
			});

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	// Restituisce un colore casuale
	private Color getRandomColor() {
		return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
	}
}

public class ShapeFrame extends JFrame implements ShapeViewer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Shape> shapes;
	private Surface panel;

	public ShapeFrame() {
		shapes = new ArrayList<Shape>();
		initUI();
	}

	// inizializzo la viewport
	private void initUI() {

		setTitle("Assignment #3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new Surface(shapes);
		add(panel);

		setSize(350, 250);
		setLocationRelativeTo(null);
	}

	@Override
	public void update(List<Shape> shapes) {
		this.shapes = shapes;
		panel.update(shapes);
		repaint();
	}
}