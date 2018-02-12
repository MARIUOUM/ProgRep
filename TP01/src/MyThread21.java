
public class MyThread21 extends Thread {

	/* premiere version */
	public MonObjet o;
	public int nbwrite;
	int x;
	public ThreadID tID;

	public MyThread21(String name, int nbwrite, MonObjet o) {
		
		this.o = o;
		this.nbwrite = nbwrite;
	}

	public void run() {
		tID = new ThreadID();
		System.out.println("Le thread " + tID.get());
		try {
			this.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Le thread n° " + tID.get() + " apres le sommeil");
	}

}
