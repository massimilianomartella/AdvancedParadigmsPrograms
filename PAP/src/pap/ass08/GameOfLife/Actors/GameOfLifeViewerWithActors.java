package pap.ass08.GameOfLife.Actors;

import java.awt.Dimension;

/**
 * Game of life with Actors - main program
 * @author Martella Massimiliano
 *
 */
public class GameOfLifeViewerWithActors {

	private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(800, 600);
	private static final Dimension DEFAULT_MATRIX_SIZE = new Dimension(79, 49);
	private static final int BLOCK_SIZE = 10;
	
	/**
	 * Program Main strutturato secondo il pattern MVC
	 * @param args
	 */
	public static void main(String[] args) {
		//Model
		GameOfLifeSet set = new GameOfLifeSet(DEFAULT_MATRIX_SIZE);
		//View
		GameOfLifeView view = new GameOfLifeView(DEFAULT_WINDOW_SIZE, DEFAULT_MATRIX_SIZE, BLOCK_SIZE);
		//Controller
		Controller controller = new Controller(set, view);
		
		view.addListener(controller);
		view.setVisible(true);
	}

}
