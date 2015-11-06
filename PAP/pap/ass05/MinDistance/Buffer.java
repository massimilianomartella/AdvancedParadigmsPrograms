package pap.ass05.MinDistance;

import java.util.ArrayList;
import java.util.List;

public class Buffer<Point3d> {

	private List<Point3d> buffer;

	//private Semaphore mutex;

	public Buffer(int size) {
		buffer = new ArrayList<Point3d>();
		//mutex = new Semaphore(1);

	}

	// throws InterruptedException
	public synchronized void put(Point3d el) {
		//mutex.acquire();
		buffer.add(el);
		//mutex.release();
	}

	// throws InterruptedException
	public Point3d take(int i) {
		//mutex.acquire();
		Point3d el = buffer.get(i);
		//mutex.release();
		return el;
	}

	public int getSize() {
		return buffer.size();
	}
}
