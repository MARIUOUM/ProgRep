package tp02;

import java.util.concurrent.locks.*;

public class BD {

	int tab[];
	ReadWriteLock lock;

	public BD(int l) {
		tab = new int[l];
		lock = new MonReadWriteLock();
	}

}
