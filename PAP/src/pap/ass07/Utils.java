package pap.ass07;

import java.util.Random;

import akka.actor.ActorRef;

public class Utils {
	
	public static final int MAX_VALUE = 100000;
	public static final int MIN_VALUE = 0;

	/**
	 * Utility che restituisce un numero casuale fra min e max compresi
	 * @param min
	 * @param max
	 * @return	numero intero casuale
	 */
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    return rand.nextInt((max - min) + 1) + min;
	}
	
	/**
	 * Utility che stampa a video il messaggio di un ActorRef
	 * @param msg
	 * @param actorRef
	 */
	public static void log(String msg, ActorRef actorRef) {
		System.out.println("[Actor-" + actorRef + "] " + msg);
	}
	
	/**
	 * Utility di supporto che serve per stampare a video il Log desiderato
	 * @param msg
	 */
	public static void log(String msg) {
		System.out.println("[Log]: " + msg);
	}

}
