package lecker.presenter;



import lecker.model.db.DBManager;



public class Handler {
	private final DBManager DBMANAGER;
	private final ExceptionHandler EXCEPTIONHANDLER;
	private final Loader LOADER;
	private final MealManager MEALMANAGER;
	private final UserManager USERMANAGER;
	
	private static final Handler instance = new Handler();
	
	
	
	private Handler() {
		synchronized (instance) {
			this.DBMANAGER = new DBManager();
			this.EXCEPTIONHANDLER = new ExceptionHandler();
			this.LOADER = new Loader();
			this.MEALMANAGER = new MealManager();
			this.USERMANAGER = new UserManager();
		}
	}



	public static Handler getInstance() {
		synchronized (instance) {
			return instance;
		}
	}
	
	public DBManager getDBManager() {
		synchronized (DBMANAGER) {
			return this.DBMANAGER;
		}
	}
	
	public ExceptionHandler getExceptionHandler() {
		synchronized (EXCEPTIONHANDLER) {
			return this.EXCEPTIONHANDLER;
		}
	}
	
	public Loader getLoader() {
		synchronized (LOADER) {
			return this.LOADER;
		}
	}
	
	public MealManager getMealManager() {
		synchronized (MEALMANAGER) {
			return this.MEALMANAGER;
		}
	}
	
	public UserManager getUserManager() {
		synchronized (USERMANAGER) {
			return this.USERMANAGER;
		}
	}	
}
