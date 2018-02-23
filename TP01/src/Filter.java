import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Filter implements Lock {

	private int IDLE = -1;
	private int[] level;
	private int[] victim;
	private int size;

	public Filter(int threads) {

		size = threads;
		level = new int[threads];
		victim = new int[threads - 1];
	}

	@Override
	public void lock() {

		int me = ThreadID.get();

		for (int i = 0; i < size; i++) {

			level[me] = i;
			victim[i] = me;
			// spin while conflicts exist
			while (sameOrHigher(me, i) && victim[i] == me) {

			}
		}
	}

	private boolean sameOrHigher(int me, int myLevel) {

		for (int id = 0; id < size; id++) {

			if (id != me && level[id] >= myLevel)
				return true;
		}
		return false;
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long arg0, TimeUnit arg1)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unlock() {

		int me = ThreadID.get();
		level[me] = IDLE;
	}

}
