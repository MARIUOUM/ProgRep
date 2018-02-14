
public class Main {
	
	/* 2 versions avec ou sans volatile */
	//public static volatile int check = 0; // version volatile
	 public static int check = 0; // version sans volatile

	public static void main(String[] args) {

		MyObject object = new MyObject();
		Stop s = new Stop();
		s.start();
		object.start();
	}
}
