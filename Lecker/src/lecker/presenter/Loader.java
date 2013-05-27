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
	
	private final Object LOCK = new Object();
	
	
	
	public void init() {
		synchronized (LOCK) {
			if (initCounter == 0) {
				if (thread == null? true : thread.isAlive()) {
					thread.interrupt();
					thread = null;
				}
				DBManager.init();
				Handler.getInstance().getMealManager().init();
				Handler.getInstance().getUserManager().init();
				Parser.init();
			}
			++initCounter;
		}
	}
	
	public void destruct() {
		synchronized (LOCK) {
			--initCounter;
			if (initCounter == 0) {
				thread = new Thread(new Waiter());
			}
		}
	}
	
	
	
	private void destructInterrupt() {
		synchronized (LOCK) {
			if (thread != null) {
				DBManager.destruct();
				Handler.getInstance().getMealManager().destruct();
				Handler.getInstance().getUserManager().destruct();
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
