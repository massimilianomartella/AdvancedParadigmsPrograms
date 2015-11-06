package pap.ass08.GameOfLife.Actors;

import java.awt.Point;
import java.util.ArrayList;
import scala.concurrent.duration.Duration;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.util.Timeout;

import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;
import scala.concurrent.Await;
import scala.concurrent.Future;

/**
 * L'attore Oracolo ha il compito di coordinare tutti gli scambi di messaggi. Il
 * rilassamento del vincolo dello scambio di messaggi consiste nel monitor Flag,
 * unico monitor utilizzato per lo start e lo stop del programma.
 * 
 * @author Martella Massimiliano
 *
 */
public class OracleActor extends UntypedActor {

	private GameOfLifeView view;
	private GameOfLifeSet set;
	private Flag stopFlag;

	private ActorSelection kActorPoint;

	private boolean[][] gameBoard;
	private boolean[][] pointBoardT0;
	private Point pointT1;

	private final Timeout t = new Timeout(
			Duration.create(100, TimeUnit.SECONDS));

	public OracleActor(GameOfLifeSet set, GameOfLifeView view, Flag stopFlag) {
		this.set = set;
		this.view = view;
		this.stopFlag = stopFlag;
		this.pointT1 = new Point();
	}

	public void preStart() {
		// Una volta creato l'attore Oracolo, invio un messaggio a me stesso per
		// svegliarmi ed attivarmi
		getSelf().tell("start", getSender());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			String message = (String) msg;
			if (message.equals("start")) {
				// Una volta partito, controllo se la bandierina è settata o
				// meno (rilassamento del vincolo ad attori)
				while (!stopFlag.isSet()) {
					// Thread.sleep(50);
					/*
					 * Lo scambio di messaggi fra l'oracolo e gli attori avviene
					 * in modo sincrono. Ogni attore viene interpellato solo se
					 * necessario, e l'oracolo, una volta inviatogli il
					 * messaggio aspetta un risposta. Tutte le risposte (oggetti
					 * della classe java.awt.Point) sono aggiunte nella lista
					 * ArrayList<Point> pointsT1, che rappresenta lo stato della
					 * griglia nell'istante T1.
					 */

					final ArrayList<Future<Object>> futures = new ArrayList<Future<Object>>();
					ArrayList<Point> pointsT1 = new ArrayList<Point>();
					gameBoard = new boolean[set.getSizeX() + 1][set.getSizeY() + 1];
					for (Point current : set.getMatrix()) {
						if (current != null)
							gameBoard[current.x + 1][current.y + 1] = true;
					}

					// eseguo lo scan di tutte le celle per controllarle tutte.
					int k = 0;
					for (int i = 1; i < gameBoard.length - 1; i++) {
						for (int j = 1; j < gameBoard[0].length - 1; j++) {
							k++;
							pointBoardT0 = new boolean[3][3];
							// carico i punti vicini all'istante T0
							pointBoardT0[0][0] = gameBoard[i - 1][j - 1];
							pointBoardT0[0][1] = gameBoard[i - 1][j];
							pointBoardT0[0][2] = gameBoard[i - 1][j + 1];
							pointBoardT0[1][0] = gameBoard[i][j - 1];
							pointBoardT0[1][1] = gameBoard[i][j];
							pointBoardT0[1][2] = gameBoard[i][j + 1];
							pointBoardT0[2][0] = gameBoard[i + 1][j - 1];
							pointBoardT0[2][1] = gameBoard[i + 1][j];
							pointBoardT0[2][2] = gameBoard[i + 1][j + 1];

							// se nell'insieme 3x3 ci sono celle vive, allora
							// interrogo gli attori
							int check = 0;
							for (boolean state[] : pointBoardT0) {
								for (boolean b : state) {
									if (b)
										check++;
								}
							}

							// interrogo solamente il k-esimo attore per la
							// cella i-j interessata
							kActorPoint = getContext().actorSelection(
									"//System/user/Actor-" + k);

							if (check != 0) {
								// Mando un messaggio sincrono istanziando
								// MsgConputePoint
								futures.add(ask(kActorPoint,
										new MsgComputePoint(pointBoardT0[1][1],
												pointBoardT0, i, j), t));

								for (Future<Object> f : futures) {
									// Una volta inviato il messaggio aspetto
									// fino a che non ho ricevuto una risposta
									pointT1 = (Point) Await.result(f,
											t.duration());
									// Una volta ricevuto la risposta, inserisco
									// il nuovo punto all'interno della lista
									// dei punti all'istante T1
									if (!pointT1.equals(new Point(-1, -1)))
										if (!pointsT1.contains(pointT1))
											pointsT1.add(pointT1);
								}
							}
						}
					}

					// Una volta finito di interrogare tutti gli attori, la
					// lista pointsT1 è riempita correttamente e posso
					// aggiornare sia il set che la view
					set.setupMatrix(pointsT1);
					view.setUpdated(pointsT1);

					//aggiorno lo il contatore delle celle vive e lo visualizzo nella view
					if (stopFlag.isSet()) {
						view.changeState("Interrupted. Cell live: "
								+ pointsT1.size());
					} else
						view.changeState(String.valueOf(pointsT1.size()));

				}
			}
		}
	}
}
