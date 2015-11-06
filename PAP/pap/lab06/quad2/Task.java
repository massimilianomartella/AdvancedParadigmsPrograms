package pap.lab06.quad2;

public class Task {
	
	private IFunction mf;
	private double a, b;
	
	Task (double a, double b, IFunction mf) {
		this.mf = mf;
		this.a = a;
		this.b = b;
	}
	
	public double getA () {
		return a;
	}

	public double getB () {
		return b;
	}
	
	public IFunction getFunction () {
		return mf;
	}
}
