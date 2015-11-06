package pap.ass05.sol.mindist;

public class Worker extends Thread {
	private int i0, i1;
	private P3d[] points;
	private Min min;
	private P3d po;
	
	public Worker(int i0, int i1, P3d[] points, P3d po, Min min){
		this.i0 = i0;
		this.i1 = i1;
		this.po = po;
		this.min = min;
		this.points = points;
	}
	
	public void run(){
		double minDist = Double.MAX_VALUE;
		P3d minP = null;
		System.out.println(this+" CRUNCHING "+i0+" - "+i1+"...");
		for (int i = i0; i < i1; i++){
			P3d p = points[i];
			double dx = po.x - p.x;
			double dy = po.y - p.y;
			double dz = po.z - p.z;
			double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);
			if (dist < minDist){
				minDist = dist;
				minP = p;
			}
		}
		min.update(minDist, minP);
	}
}
