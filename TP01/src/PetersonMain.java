public class PetersonMain {

	public static void main(String[] args) {

		final Peterson2Lock p2 = new Peterson2Lock();

		Thread[] threads = new Thread[2];

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
