package testJava8;

class A {
	public Runnable m() {
		final int c = 0;
		Runnable task = () -> {
			System.out.println("hello" + c);
			// c++; //in questo spazio le variabili non posso variare
		};
		return task;

	}
}

class TestCapture {

	public static void main(String[] args) {

		A obj = new A();
		Runnable t0 = obj.m();

		t0.run();

	}
}
