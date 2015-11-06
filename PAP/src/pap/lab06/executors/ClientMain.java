package pap.lab06.executors;

public class ClientMain {
	public static void  main(String[] args){
		try {
			for (int i = 0; i < 100; i++){
				ClientAgent cl = new ClientAgent("client-"+i);
				cl.start();
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
