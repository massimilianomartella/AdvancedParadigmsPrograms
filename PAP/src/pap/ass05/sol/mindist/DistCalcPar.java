package pap.ass05.sol.mindist;

public class DistCalcPar extends DistCalc {
	
	public P3d getMinDist(P3d[] points, P3d po) {
		int nWorkers = Runtime.getRuntime().availableProcessors() + 1;
		Min min = new Min();
		int delta = points.length / nWorkers;
		int i0 = 0;
		int i1 = delta;
		Worker[] workers = new Worker[nWorkers+1];
		for (int i = 0; i < nWorkers - 1; i++){
			workers[i] = new Worker(i0,i1,points,po,min);
			workers[i].start();
			i0 = i1;
			i1 += delta;
		}
		workers[nWorkers] = new Worker(i0,points.length,points,po,min);
		workers[nWorkers].start();
		for (Worker w: workers){
			try {
				w.join();
			} catch (Exception ex){}
		}
		
		return min.get();
	}


}
