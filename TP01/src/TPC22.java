public class TPC22 {

	public static void main(String[] args) {

		MonObjet o = new MonObjet(0);
		MyThread22 TH[] = new MyThread22[10];

		for (int i = 0; i < 10; i++)
			TH[i] = new MyThread22("nom" + i, 10, o);

		try {
			for (int i = 0; i < 10; i++)
				TH[i].start();
			for (int i = 0; i < 10; i++)
				TH[i].join();

		} catch (InterruptedException e) {
		}
	}
}
