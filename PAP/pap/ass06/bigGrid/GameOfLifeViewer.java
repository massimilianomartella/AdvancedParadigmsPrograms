package pap.ass06.bigGrid;

import java.awt.Dimension;

/**
 * Game Of Life Viewer - structured sequential program.
 * 
 * @author mmartella
 *
 */
public class GameOfLifeViewer {

	//private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(800, 600);
	private static final Dimension DEFAULT_MATRIX_SIZE = new Dimension(79, 49);
	private static final int BLOCK_SIZE = 5;
	
	public static void main(String[] args) {

		
		GameOfLifeView view = new GameOfLifeView(BLOCK_SIZE);
		GameOfLifeSet set = new GameOfLifeSet(new Dimension(255,135));
		Controller controller = new Controller(set, view);
		view.addListener(controller);
		view.setVisible(true);
	}

}
