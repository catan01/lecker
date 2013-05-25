package lecker.presenter;



import lecker.model.Parser;
import lecker.model.db.DBManager;



/**
 * This class construct and destruct everything if needed.
 * 
 * @author LWagner
 *
 */
final public class Loader {
	private Integer initCounter = 0;
	private Thread thread = null;
	private final static Loader INSTANCE = new Loader();
	
	
	
	private Loader() {
		
	}
	
	public static Loader getInstance() {
		synchronized (INSTANCE) {
			return INSTANCE;
		}
	}
	
	
	
	public void init() {
		synchronized (INSTANCE) {
			if (initCounter == 0) {
				if (thread == null? true : thread.isAlive()) {
					thread.interrupt();
					thread = null;
				}
				DBManager.init();
				MealManager.init();
				UserManager.init();
				Parser.init();
			}
			++initCounter;
		}
	}
	
	public void destruct() {
		synchronized (INSTANCE) {
			--initCounter;
			if (initCounter == 0) {
				thread = new Thread(new Waiter());
			}
		}
	}
	
	
	
	private void destructInterrupt() {
		synchronized (INSTANCE) {
			if (thread != null) {
				DBManager.destruct();
				MealManager.destruct();
				UserManager.destruct();
				thread = null;
			}
		}
	}
	
	
	
	
	
	private class Waiter implements Runnable {

		@Override
		public void run() {
			try {
				wait(1800000);
				destructInterrupt();
			} catch (InterruptedException e) {
				
			}
		}
		
	}
}
