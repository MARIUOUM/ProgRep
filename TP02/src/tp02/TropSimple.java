package tp02;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class TropSimple implements ReadWriteLock {

	private ReentrantLock l;

	public TropSimple() {
		l = new ReentrantLock();
	}

	public Lock readLock() {
		return l;
	}

	public Lock writeLock() {
		return l;
	}
}
