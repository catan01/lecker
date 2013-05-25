package lecker.presenter;



import java.util.HashMap;

import lecker.model.data.User;
import lecker.model.db.LoginDBStatement;



public class UserManager {
	private static HashMap<String, User> users;
	
	
	
	public static synchronized void init() {
		users = new HashMap<String, User>();
	}
	
	public static synchronized void destruct() {
		users = null;
	}
	
	
	
	public static User getUser(String remoteAddr) {
		synchronized(users) {
			return users.get(remoteAddr);
		}
	}
	
	
	
	public static User login(String remoteAddr, String name, String passwordMD5) {
		synchronized(users) {
			User user = new LoginDBStatement(name, passwordMD5).postQuery();
			users.put(remoteAddr, user);
			return user;
		}
	}
	
	public static void logout(String remoteAddr, String name) {
		synchronized(users) {
			if (users.get(remoteAddr).getName().equals(name)) {
				users.remove(remoteAddr);
			}
		}
	}
}
