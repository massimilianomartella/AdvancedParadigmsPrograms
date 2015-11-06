package pap.ass05.sol.mindist;

import java.util.Optional;

public class Min {

	private Optional<P3d> p;
	private double currentMin = Double.MAX_VALUE;
	
	public Min(){
		p = Optional.empty();
	}
	
	public synchronized void update(double min, P3d point){
		if (min < currentMin){
			currentMin = min;
			p = Optional.of(point);
		}
	}
	
	public synchronized P3d get(){
		return p.get();
	}
}
