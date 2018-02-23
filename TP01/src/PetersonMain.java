import java.util.concurrent.locks.Lock;

public class PetersonMain {

	public static void main(String[] args) {

		//final Lock p2 = new Peterson();
		final int N = 10;
		final Lock p2 = new Filter(N);

		//Thread[] threads = new Thread[2];
		Thread[] threads = new Thread[N];

		for (int i = 0; i < threads.length; ++i) {

			threads[i] = new Thread(new Runnable() {

				@Override
				public void run() {

					for (int j = 0; j < 10; ++j) {
						p2.lock();
						System.out.println(" - OK " + ThreadID.get());
						p2.unlock();
					}

				}
			});
		}

		for (Thread t : threads) {

			t.start();
		}
	}

}
