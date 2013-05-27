package lecker.presenter;



import java.util.HashMap;

import lecker.model.data.User;
import lecker.model.db.AddUserFavouriteDBStatement;
import lecker.model.db.LoginDBStatement;
import lecker.model.db.RemoveUserFavouriteDBStatement;



public class UserManager {
	private HashMap<String, User> users;
	
	
	
	public void init() {
		synchronized(users) {
			users = new HashMap<String, User>();
		}
	}
	
	public synchronized void destruct() {
		synchronized(users) {
			users = null;
		}
	}
	
	
	
	public User getUser(String remoteAddr) {
		synchronized(users) {
			return users.get(remoteAddr);
		}
	}
	
	
	
	public void addFavourite(String userName, String mealName) { // Unchecked!!!
		if (new AddUserFavouriteDBStatement(mealName, userName).postQuery()) {
			for (User user: users.values()) {
				if (user != null? user.getName().equals(userName) : false) {
					users.get(userName).addFavourite(mealName);
					return;
				}
			}
		}
	}
	
	public void removeFavourite(String userName, String mealName) { // Unchecked!!!
		if (new RemoveUserFavouriteDBStatement(mealName, userName).postQuery()) {
			for (User user: users.values()) {
				if (user != null? user.getName().equals(userName) : false) {
					users.get(userName).removeFavourite(mealName);
					return;
				}
			}
		}
	}
	
	
	
	public User login(String remoteAddr, String name, String passwordMD5) {
		synchronized(users) {
			User user = new LoginDBStatement(name, passwordMD5).postQuery();
			users.put(remoteAddr, user);
			return user;
		}
	}
	
	public void logout(String remoteAddr, String name) {
		synchronized(users) {
			if (users.get(remoteAddr).getName().equals(name)) {
				users.remove(remoteAddr);
			}
		}
	}
}
