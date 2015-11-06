package pap.ass08.GameOfLife.Actors;

/**
 * Messaggio che viene scambiato dall'attore Oracle al k-esimo attore adibito al
 * calocolo del suo intorno.
 * 
 * @author Martella Massimiliano
 *
 */
public class MsgComputePoint {

	private boolean me;
	private boolean[][] pointBoard;
	private int i, j;

	public MsgComputePoint(boolean b, boolean[][] pointBoardT0, int i, int j) {
		this.me = b;
		this.pointBoard = pointBoardT0;
		this.i = i;
		this.j = j;
	}

	public boolean getMe() {
		return me;
	}

	public boolean[][] getPointBoardT0() {
		return pointBoard;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

}
