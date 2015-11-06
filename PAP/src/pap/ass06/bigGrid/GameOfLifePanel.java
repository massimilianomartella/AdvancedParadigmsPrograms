package pap.ass06.bigGrid;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class GameOfLifePanel extends JPanel implements ComponentListener {

	private Dimension d_gameBoardSize = null;
	private Dimension lastDimension = null;
	private int blockSize;
	private ArrayList<Point> point = new ArrayList<Point>(0);

	public GameOfLifePanel(Dimension matrixSize, int blockSize) {
		d_gameBoardSize = matrixSize;
		this.blockSize = blockSize;

		addComponentListener(this);
	}

	public void updateImage(ArrayList<Point> result) {
		this.point = result;
		repaint();
	}

	public void addPoint(MouseEvent me) {
		int x = me.getPoint().x / 5;
		int y = me.getPoint().y / 7;
		if ((x >= 0) && (x < d_gameBoardSize.width) && (y >= 0)
				&& (y < d_gameBoardSize.height)) {
			addPoint(x, y);
		}
	}

	public void addPoint(int x, int y) {
		if (!point.contains(new Point(x, y)))
			point.add(new Point(x, y));
		repaint();
	}

	public ArrayList<Point> getMatrix() {
		return point;
	}

	public void removePoint(int x, int y) {
		point.remove(new Point(x, y));
	}

	public void removePoint(MouseEvent me) {
		int x = me.getPoint().x / blockSize - 1;
		int y = me.getPoint().y / blockSize - 7;
		if (point.contains(new Point(x, y)))
			point.remove(new Point(x, y));
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		try {
			for (Point newPoint : point) {
				// Draw new point
				g.setColor(Color.blue);
				g.fillRect(blockSize + (blockSize * newPoint.x), blockSize
						+ (blockSize * newPoint.y), blockSize, blockSize);
			}
		} catch (ConcurrentModificationException cme) {
		}
		// Setup grid
		g.setColor(Color.BLACK);
		for (int i = 0; i <= d_gameBoardSize.width; i++) {
			g.drawLine(((i * blockSize) + blockSize), blockSize,
					(i * blockSize) + blockSize, blockSize
							+ (blockSize * d_gameBoardSize.height));
		}
		for (int i = 0; i <= d_gameBoardSize.height; i++) {
			g.drawLine(blockSize, ((i * blockSize) + blockSize), blockSize
					* (d_gameBoardSize.width + 1),
					((i * blockSize) + blockSize));
		}
	}

	public void CannoneAliantiGosper() {
		// Blocco
		addPoint(22, 24);
		addPoint(22, 25);
		addPoint(23, 24);
		addPoint(23, 25);

		//
		addPoint(32, 24);
		addPoint(32, 25);
		addPoint(32, 26);
		addPoint(33, 23);
		addPoint(33, 27);
		addPoint(34, 22);
		addPoint(35, 22);
		addPoint(34, 28);
		addPoint(35, 28);
		addPoint(36, 25);
		addPoint(37, 23);
		addPoint(37, 27);
		addPoint(38, 24);
		addPoint(38, 25);
		addPoint(38, 26);
		addPoint(39, 25);

		//
		addPoint(42, 24);
		addPoint(42, 23);
		addPoint(42, 22);
		addPoint(43, 24);
		addPoint(43, 23);
		addPoint(43, 22);
		addPoint(44, 21);
		addPoint(44, 25);
		addPoint(46, 21);
		addPoint(46, 20);
		addPoint(46, 25);
		addPoint(46, 26);

		// Blocco
		addPoint(56, 22);
		addPoint(56, 23);
		addPoint(57, 22);
		addPoint(57, 23);
	}

	private void updateArraySize() {
		ArrayList<Point> removeList = new ArrayList<Point>(0);
		for (Point current : point) {
			if ((current.x > d_gameBoardSize.width - 1)
					|| (current.y > d_gameBoardSize.height - 1)) {
				removeList.add(current);
			}
		}
		if (!removeList.isEmpty()) {
			point.removeAll(removeList);
			repaint();
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// Setup the game board size with proper boundries
		d_gameBoardSize = new Dimension(getWidth() / blockSize - 2, getHeight()
				/ blockSize - 2);
		lastDimension = d_gameBoardSize;
		updateArraySize();
	}
	
	public Dimension getPanelDimension() {
		return lastDimension;
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

}
