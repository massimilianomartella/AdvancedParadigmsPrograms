package pap.ass07;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;

public class PlayersActor extends UntypedActor {

	private int myGuessNum;
	private int nextValue;
	private ActorSelection oracle, nextPlayer;
	private int numPlayers, myNum;
	private Message msg;

	/**
	 * Attore giocatore che tenda di indovinare il numero inviando all'oracolo
	 * il proprio numero generato casualmente e cambiato successivamente Il
	 * numero attori totali serve per far capire all'attore a che il suo
	 * successore è b e così via, ad esempio: a -> b, b -> c, c -> a
	 * 
	 * @param numPlayers
	 *            : numero attori totali
	 */
	public PlayersActor(int numPlayers) {
		this.myGuessNum = Utils.randInt(Utils.MIN_VALUE, Utils.MAX_VALUE);
		this.nextValue = 0; // prossimo valore da inviare
		oracle = getContext().actorSelection("//System/user/Oracle");
		this.numPlayers = numPlayers; // numero attori totali
		this.msg = new Message();
		getnextPlayer();
	}

	/**
	 * La funzione salva il riferimento al giocatore al turno successivo
	 */
	private void getnextPlayer() {
		Utils.log(getSelf().toString());
		int posEndName = getSelf().toString().indexOf("#");
		myNum = Integer
				.parseInt(getSelf().toString().substring(32, posEndName));

		if (myNum == numPlayers)
			nextPlayer = getContext().actorSelection("//System/user/Player-1");
		else
			nextPlayer = getContext().actorSelection(
					"//System/user/Player-" + (myNum + 1));
	}

	/**
	 * All'inizio, se sono il giocatore numero 1 devo cominciare la partita,
	 * altrimenti non faccio nulla
	 */
	public void preStart() {
		Utils.log("Player - " + myNum + ": " + myGuessNum);
		if (myNum == 1) { // se sono il primo, invio il messaggio
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msg.setNum(myGuessNum);
			Utils.log("Player-" + myNum + ": provo il numero -> " + myGuessNum);
			oracle.tell(msg, getSelf());
		}
	}

	/**
	 * Tutte le volte che ricevo un messaggio devo capire chi me lo ha mandato.
	 * 
	 * Caso oracolo: in questo caso l'oracolo mi comunica se sono il vincitore,
	 * perdente oppure se ho un tentativo in piu. Se vinco, stampo a video che
	 * sono il vincitore Se ho perso, stampo a video che sono il perdente Se ho
	 * un altro tentativo, l'oracolo mi deve comunicare se il numero che dovrò
	 * generare è più piccolo o più grande di quello che ho generato al turno
	 * precedente. 
	 * Caso giocatore: il giocatore mi "passa la palla" affinché io
	 * possa giocare e tentare nuovamente di indovinare il numero
	 */
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Message) {
			Message myMsg = (Message) msg;
			if (myMsg.getWho().equals(oracle)) {
				if (myMsg.getStatePlayer() == StateOfPlayer.win()) {
					Utils.log("I WIN! : " + myMsg.getNum(), getSelf());
					myMsg.setStatePalyer(StateOfPlayer.sob());
					myMsg.setWho(nextPlayer);
				} else if (myMsg.getStatePlayer() == StateOfPlayer.sob()) {
					Utils.log("I SOB! : " + getSelf());
				} else if (myMsg.getStatePlayer() == StateOfPlayer.trayAgain()) {

					if (myMsg.getInd() == Indication.smaller()) {
						// se il numero deve essere più alto
						nextValue = Utils.randInt(myGuessNum, Utils.MAX_VALUE);
					} else if (myMsg.getInd() == Indication.bigger()) {
						// se il numero deve essere più piccolo
						nextValue = Utils.randInt(Utils.MIN_VALUE, myGuessNum);
					}

					myGuessNum = nextValue;
					myMsg.setWho(nextPlayer);
					nextPlayer.tell(myMsg, getSelf());

				}
			} else {

				myMsg.setNum(myGuessNum);
				Utils.log("Player-" + myNum + ": provo il numero -> "
						+ myGuessNum);
				oracle.tell(myMsg, getSelf());

			}
		}
	}
}
