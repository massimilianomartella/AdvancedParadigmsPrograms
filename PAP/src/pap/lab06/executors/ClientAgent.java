package pap.lab06.executors;

import java.net.*;
import java.io.*;
import java.util.*;

public class ClientAgent extends Thread {

	private Random gen;
	
	public ClientAgent(String name){
		super(name);
		gen = new Random();
	}
	
	public void run(){
		try {
			int value = gen.nextInt(10000000);
			log("Checking if "+value+" is prime..");
			String response =doRequest("localhost",""+value);
			log("result: "+response);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void log(String msg){
		synchronized(System.out){
			System.out.println("["+getName()+"] "+msg);
		}
	}
	
	private String doRequest(String host, String req) throws Exception {
		int port = 8090;
		int index = host.indexOf(':');
		if (index != -1){
			port = Integer.parseInt(host.substring(index+1));
			host = host.substring(0, index);
		}
		Socket channel = new Socket(InetAddress.getByName("localhost"),port);
		BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(channel.getOutputStream()));
		out.write(req+"\n");
		out.flush();
		return in.readLine();
	}

	
}
