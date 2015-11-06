package pap.lab05.monitors;

public class TestSynchCell {
		
	public static void main(String args[]){
		SynchCell2 cell = new SynchCell2();
		new Getter(cell).start();
		new Getter(cell).start();
		new Getter(cell).start();
		new Setter(cell,303).start();
	}
}
