package lecker.presenter;



import lecker.model.db.DBManager;



public class Handler {
	private DBManager dbManager;
	private final ExceptionHandler exceptonHandler;
	private final Loader loader;
	private MealManager mealsManager;
	private UserManager userManager;
	
	private static Handler instance = new Handler();
	
	private final Object DBMANAGERLOCK = new Object();
	private final Object MEALMANAGERLOCK = new Object();
	private final Object USERMANAGERLOCK = new Object();
	
	
	
	private Handler() {
		exceptonHandler = new ExceptionHandler();
		loader = new Loader();
	}



	public static Handler getInstance() {
		synchronized (instance) {
			return instance;
		}
	}
	
	public DBManager getDBManager() {
		synchronized (this.DBMANAGERLOCK) {
			return this.dbManager;
		}
	}
	
	public ExceptionHandler getExceptionHandler() {
		synchronized (this.exceptonHandler) {
			return this.exceptonHandler;
		}
	}
	
	public Loader getLoader() {
		synchronized (this.loader) {
			return this.loader;
		}
	}
	
	public MealManager getMealManager() {
		synchronized (this.MEALMANAGERLOCK) {
			return this.mealsManager;
		}
	}
	
	public UserManager getUserManager() {
		synchronized (this.USERMANAGERLOCK) {
			return this.userManager;
		}
	}
	
	
	
	public void init() {
		synchronized (this.DBMANAGERLOCK) {
			synchronized (this.MEALMANAGERLOCK) {
				synchronized (this.USERMANAGERLOCK) {
					this.dbManager = new DBManager();
					this.mealsManager = new MealManager();
					this.userManager = new UserManager();
				}
			}
		}
	}
	
	public void destruct() {
		synchronized (this.DBMANAGERLOCK) {
			synchronized (this.MEALMANAGERLOCK) {
				synchronized (this.USERMANAGERLOCK) {
					this.dbManager = null;
					this.mealsManager = null;
					this.userManager = null;
				}
			}
		}
	}
}
