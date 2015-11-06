package pap.ass07;

import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;

public class OracleActor extends UntypedActor {

	private int guessNumber;
	private ActorSelection oracle;
	private List<ActorRef> players;

	/**
	 * Attore Oracolo. Riceve la lista della distinta dei giocatori e rimane
	 * pronto a ricevere i numeri dai giocatori Genera un numero casuale Guess
	 * da indovinare.
	 * 
	 * @param players
	 */
	public OracleActor(List<ActorRef> players) {
		this.guessNumber = Utils.randInt(Utils.MIN_VALUE, Utils.MAX_VALUE);
		this.oracle = getContext().actorSelection("//System/user/Oracle");
		this.players = players;
	}

	/**
	 * All'avvio l'oracolo stampa a video il numero da indovinare
	 */
	public void preStart() {
		Utils.log("Oracle: guess Number --> " + guessNumber);
	}

	/**
	 * Tutte le volte che ricevo un messaggio, controllo se hanno indovinato
	 * oppure no. Se hanno indovinato: invio il messaggio al vincitore. Rimuovo
	 * il vincitore dalla lista dei giocatori e invio N-1 messaggi a tutti gli
	 * altri giocatori perdenti. Successivamente spengo il sistema. Se non hanno
	 * indovinato: controllo se il numero è più piccolo o più grande e lo
	 * comunico al giocatore.
	 * 
	 */
	@Override
	public void onReceive(Object msg) throws Exception {

		// Thread.sleep(100);
		// Utils.log("Oracle in action...");
		if (msg instanceof Message) {
			Message myMsg = (Message) msg;
			if (myMsg.getNum() == guessNumber) { // se il numero è uguale
				Utils.log("Hai indovinato! " + getSender().path()
						+ "; Il numero vincente è: " + guessNumber);
				Message win = new Message();
				win.setStatePalyer(StateOfPlayer.win());
				win.setWho(oracle);
				win.setNum(guessNumber);
				sender().tell(win, getSelf());

				players.remove(sender());

				myMsg.setStatePalyer(StateOfPlayer.sob());
				myMsg.setWho(oracle);
				players.stream().forEach(f -> {
					f.tell(myMsg, getSelf());
				});

				Utils.log("System: spegnimento.");
				getContext().system().shutdown();
			} else { // se il numero non è uguale
				myMsg.setStatePalyer(StateOfPlayer.trayAgain());
				if (myMsg.getNum() > guessNumber) {
					Utils.log("Numero: "
							+ myMsg.getNum()
							+ " sbagliato! Il numero da indovinare è più piccolo!");
					myMsg.setInd(Indication.bigger());
				} else {
					Utils.log("Numero: "
							+ myMsg.getNum()
							+ " sbagliato! Il numero da indovinare è più grande!");
					myMsg.setInd(Indication.smaller());
				}
				myMsg.setWho(oracle);
				sender().tell(myMsg, getSelf());
			}
		}
	}
}
