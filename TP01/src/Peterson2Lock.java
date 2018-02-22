import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Peterson2Lock implements Lock {

	private volatile boolean[] flag = new boolean[2];
	private volatile int victim;

	public Peterson2Lock() {
		for (int i = 0; i < flag.length; ++i) {
			flag[i] = false;
		}
	}

	@Override
	public void lock() {

		int i = ThreadID.get() % 2;
		int j = 1 - i;
		flag[i] = true; // I want to go in Critical section
		victim = i;		// Ok, I go first
		
		while(flag[j] && victim == i);	// wait!!
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
		// TODO Auto-generated method stub
		int i = ThreadID.get() % 2; 
		flag[i] = false;
	}

}
