package pap.lab06.executors;

import java.io.*;
import java.util.concurrent.*;
import java.net.*;

public class TaskExecutionServer {

	  private static final int NTHREADS = Runtime.getRuntime().availableProcessors()+1;
	  private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

	  public static void main(String[] args) throws IOException {
	    ServerSocket socket = new ServerSocket(8090);
	    log("ready to process requests.");
	    while (true) {
	      Socket connection = socket.accept();
		  log("new request arrived.");
	      exec.execute(new ServerTask(connection));
	    }
	  }
	  
	  static private void log(String msg){
		System.out.println("[ SERVER ] "+msg);
	  }
}