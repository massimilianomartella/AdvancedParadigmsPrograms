package pap.ass08.GameOfLife.Actors;

import java.awt.Point;

import akka.actor.UntypedActor;

/**
 * Attore ActorPoint adibito al calcolo dell'insieme 3x3 passatogli come
 * messaggio. Infatti il caloco delle celle è spostato a valle.
 * 
 * @author Martella Massimiliano
 *
 */

public class ActorPoint extends UntypedActor {

	public ActorPoint() {
	}

	@Override
	public void preStart() throws Exception {

	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof MsgComputePoint) {
			MsgComputePoint cp = (MsgComputePoint) message;
			// copio l'istanza del messaggio nelle variabili locali dell'attore
			int i = cp.getI();
			int j = cp.getJ();
			boolean me = cp.getMe();
			boolean[][] board = cp.getPointBoardT0();

			try {

				// eseguo l'algoritmo per ogni cella 3x3 -1
				int surrounding = 0;
				if (board[0][0]) surrounding++;
				if (board[0][1]) surrounding++;
				if (board[0][2]) surrounding++;
				if (board[1][0]) surrounding++;
				// pointBoard[1][1]
				if (board[1][2]) surrounding++;
				if (board[2][0]) surrounding++;
				if (board[2][1]) surrounding++;
				if (board[2][2]) surrounding++;

				if (me) {
					// una cella m[i,j] che nello stato s(t) è live e ha
					// due o tre celle vicine live,
					// nello stato s(t+1) rimane live (“sopravvive”)
					if ((surrounding == 2) || (surrounding == 3)) {
						getSender().tell(new Point(i - 1, j - 1), getSelf());
						return;
					}
				} else {
					// una cella m[i,j] che nello stato s(t) è dead e ha
					// tre celle vicine live,
					// nello stato s(t+1) diventa live
					if (surrounding == 3) {
						getSender().tell(new Point(i - 1, j - 1), getSelf());
						return;
					}
				}
				/**
				 * 1) una cella m[i,j] che nello stato s(t) è live e ha zero o
				 * al più una cella vicina live (e le altre dead), nello stato
				 * s(t+1) diventa dead (“muore di solitudine”)
				 *
				 * 2) una cella m[i,j] che nello stato s(t) è live e ha quattro
				 * o più celle vicine live, nello stato s(t+1) diventa dead
				 * (“muore di sovrappopolamento”)
				 */

				// L'invio del punto -1, -1 stabilisce che la cella è deceduta
				getSender().tell(new Point(-1, -1), getSelf());
				return;
			} catch (Exception e) {
				getSender().tell(new akka.actor.Status.Failure(e), getSelf());
				throw e;
			}
		}

	}

}
