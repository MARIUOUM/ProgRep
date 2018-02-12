public class MyThread22 extends Thread {

	/* deuxieme version */
	public MonObjet o;
	public int nbwrite;
	int x;
	public ThreadID tID;

	public MyThread22(String name, int nbwrite, MonObjet o) {
		
		this.o = o;
		this.nbwrite = nbwrite;
		tID = new ThreadID();
	}

	public void run() {
		System.out.println("Le thread " + tID.get());
		try {
			this.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Le thread n° " + tID.get() + " apres le sommeil");
	}

}
