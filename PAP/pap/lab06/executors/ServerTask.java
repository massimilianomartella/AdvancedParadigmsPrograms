package pap.lab06.executors;

import java.net.*;
import java.io.*;
import java.util.*;

public class ServerTask implements Runnable {

	private Socket channel;
	private Random gen;
	
	public ServerTask(Socket channel){
		this.channel = channel;
		gen = new Random(System.nanoTime());
	}
	
	public void run(){
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(channel.getOutputStream()));
			String svalue = in.readLine();
			try {
				long value = Long.parseLong(svalue);
				boolean result = isPrime(value);
				if (result){
					out.write("yes.");
				} else {
					out.write("no.");
				}
			} catch (Exception ex){
				out.write("error.");
			}
			out.flush();
			channel.close();
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Naive technique for checking if a number is prime.
	 */
	private boolean isPrime(long num){
		long sqrt = (long)Math.sqrt(num);
		for (long i = 2; i <= sqrt+1; i++){
			if (num % i == 0){
				return false;
			}
		}
		return true;
	}
	
}
