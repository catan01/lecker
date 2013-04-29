package lecker.model;



import java.util.HashMap;

import lecker.model.data.User;



public class UserManager {
	private static HashMap<String, User> users;
	
	
	
	public static synchronized void init() {
		users = new HashMap<String, User>();
	}
	
	
	
	public static User getUser(String remoteAddr) {
		synchronized(users) {
			return users.get(remoteAddr);
		}
	}
	
	
	
	public static User login(String remoteAddr, String name, String passwordMD5) {
		synchronized(users) {
			//TODO
			return null;
		}
	}
	
	public static void logout(String remoteAddr, String name) {
		synchronized(users) {
			//TODO
		}
	}
}
