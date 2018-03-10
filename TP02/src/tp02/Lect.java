package tp02;

public class Lect extends Thread {

	BD base;

	public Lect(BD b) {
		base = b;
	}

	public void run() {

		for (int tour = 0; tour < 10; tour++) {

			base.lock.readLock().lock();

			try {

				System.out.print(" lecteur " + ThreadID.get() + " - ");

				for (int i = 0; i < base.tab.length; i++)
					System.out.print(base.tab[i] + " ");

				System.out.println("");
				Thread.sleep((long) Math.random() * base.tab.length * 1000);

			} catch (InterruptedException e) {

				System.out.println("- interompu " + ThreadID.get());
				break;

			} finally {
				
				base.lock.readLock().unlock();
			}

			System.out.println("verrou enlevé : " + ThreadID.get() + " sort");

			try {

				Thread.sleep((long) Math.random() * base.tab.length * 100);

			} catch (InterruptedException e) {

				System.out.println("- interrompu en dehors: " + ThreadID.get());
			}
		}
	}
}