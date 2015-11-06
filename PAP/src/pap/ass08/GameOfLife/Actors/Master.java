package pap.ass08.GameOfLife.Actors;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Il Thread Master è adibito alla creazione di tutti gli n attori (n attori per
 * n celle della grigia); successivamente crea l'oracolo che ha il compito di
 * coordinare lo scambio di messaggi e aggiornare la viewport grafica.
 * 
 * @author Martella Massimiliano
 *
 */
public class Master extends Thread {

	private GameOfLifeView view;
	private GameOfLifeSet set;
	private Flag stopFlag;
	private List<ActorRef> actorsPoint = new ArrayList<ActorRef>();

	/**
	 * Thread principale per controllare lo stato dell'algoritmo Game of life
	 * 
	 * @param set
	 * @param view
	 * @param stopFlag
	 * @param matrix
	 */
	public Master(GameOfLifeSet set, GameOfLifeView view, Flag stopFlag,
			ArrayList<Point> matrix) {
		this.view = view;
		this.set = set;
		this.stopFlag = stopFlag;
	}

	@Override
	public void run() {
		// Creazione del contesto
		final ActorSystem system = ActorSystem.create("System");
		int k = 0;
		for (int i = 1; i < set.getSizeX(); i++) {
			for (int j = 1; j < set.getSizeY(); j++) {
				k++;
				// Creo un attore per ogni cella
				ActorRef actorPoint = system.actorOf(
						Props.create(ActorPoint.class), "Actor-" + k);
				actorsPoint.add(actorPoint);
				// Utils.log("Players created.", player);
			}
		}

		// System.out.println("Actors created!");

		// Creazione dell'attore Oracle, colui che coordinerà tutte le
		// operazioni sulla griglia.
		system.actorOf(Props.create(OracleActor.class, set, view, stopFlag),
				"Oracle");
	}
}
