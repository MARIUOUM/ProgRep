public class TPC2 {

	public static void main(String[] args) {

		MonObjet o = new MonObjet(0);
		MyThread2 W;
		MyThread2 R;

		W = new MyThread2(o, 1000);
		R = new MyThread2(o, 5000);

		W.start();
		R.start();

		try {
			R.join();
			W.join();
		} catch (InterruptedException e) {
		}

		System.out.println("value: " + o.value + ", " + "valuebis: " + o.valuebis
				+ " et " + "last: " + o.last.get());
	}
}