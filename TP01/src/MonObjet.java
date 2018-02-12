public class MonObjet {

	ThreadLocal<Integer> last;// nb ecriture de chaque thread

	int value;			// valeur commune
	int valuebis;		// valeur commune

	public MonObjet(int init) {
		value = init;
		last = new ThreadLocal<Integer>() {
			protected Integer initialValue() {
				return 0;
			}
		};
	};

	public int read() {
		return value;
	}

	public synchronized void add() {
		last.set(new Integer(last.get() + 1));
		value = value + 1;
		valuebis = valuebis + 1;
	}
}