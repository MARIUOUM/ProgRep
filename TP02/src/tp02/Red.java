package tp02;

public class Red extends Thread {

	BD base;

	public Red(BD b) {
		base = b;
	}

	public void run() {

		for (int tour = 0; tour < 10; tour++) {

			base.lock.writeLock().lock();

			try {

				int j = (int) (Math.random() * 100);

				System.out.println(" ecrivain n° " + ThreadID.get() + " ecrit -> " + j);

				for (int i = 0; i < base.tab.length; i++)
					base.tab[i] = j;

				Thread.sleep((long) Math.random() * base.tab.length * 1);

			} catch (InterruptedException e) {

				System.out.println(" interompu " + ThreadID.get());
				break;
			
			} finally {
				
				base.lock.writeLock().unlock();
			}

			System.out.println("- verrou ecrivain enleve " + ThreadID.get());

			try {

				Thread.sleep((long) Math.random() * base.tab.length * 100);

			} catch (InterruptedException e) {
				System.out.println("- interompu en dehors" + ThreadID.get());
			}
		}
	}

}
