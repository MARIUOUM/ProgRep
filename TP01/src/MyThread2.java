public class MyThread2 extends Thread {
	
	public MonObjet o;
	public int nbwrite;

	public MyThread2(MonObjet o, int nbwrite) {
		this.o = o;
		this.nbwrite = nbwrite;
	}

	public void run() {
		// System.out.println("Je suis la thread " +ThreadID.get());
		for (int i = 0; i < nbwrite; i++) {
			o.add();
			this.yield();
		}
		// System.out.println("la thread "+ThreadID.get()+
		System.out.println("value: " + o.value + ", " + "valuebis: " + o.valuebis
				+ " et " + "last: " + o.last.get());
	}
}