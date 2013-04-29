package lecker.model;



import javax.servlet.ServletException;




/**
 * This class construct and destruct everything if needed.
 * 
 * @author LWagner
 *
 */
final public class Loader {
	private static Boolean isInit = false;
	
	
	
	public static boolean init() throws ServletException {
		synchronized (isInit) {
			if (!isInit) {
				DBManager.init();
				CanteenManager.init();
				UserManager.init();
				
				return isInit = true;
			} else {
				return false;
			}
		}
	}
	
	public static void destruct() {
		synchronized (isInit) {
			//TODO
			
			isInit = false;
		}
	}
}
