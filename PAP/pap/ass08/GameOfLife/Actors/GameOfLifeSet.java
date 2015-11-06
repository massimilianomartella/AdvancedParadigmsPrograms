package pap.ass08.GameOfLife.Actors;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

public class GameOfLifeSet {

	private ArrayList<Point> point = new ArrayList<Point>(0);
	private Dimension d_gameBoardSize;

	public GameOfLifeSet(Dimension matrixSize) {
		this.d_gameBoardSize = matrixSize;
	}

	public int getSizeX() {
		return d_gameBoardSize.width;
	}

	public int getSizeY() {
		return d_gameBoardSize.height;
	}

	public ArrayList<Point> getMatrix() {
		return point;
	}

	public void setupMatrix(ArrayList<Point> matrix) {
		point = matrix;
	}
}
