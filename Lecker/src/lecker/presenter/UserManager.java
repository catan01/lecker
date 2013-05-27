package lecker.presenter;



import java.util.HashMap;

import lecker.model.data.User;
import lecker.model.db.AddUserFavouriteDBStatement;
import lecker.model.db.LoginDBStatement;
import lecker.model.db.RemoveUserFavouriteDBStatement;



public class UserManager {
	private HashMap<String, User> users;
	
	private final Object USERSLOCK = new Object();
	
	
	
	public UserManager() {
		synchronized(USERSLOCK) {
			this.users = new HashMap<String, User>();
		}
	}
	
	
	
	public User getUser(String remoteAddr) {
		synchronized(USERSLOCK) {
			return this.users.get(remoteAddr);
		}
	}
	
	
	
	public void addFavourite(String userName, String mealName) { // Unchecked!!!
		synchronized(USERSLOCK) {
			if (new AddUserFavouriteDBStatement(mealName, userName).postQuery()) {
				for (User user: this.users.values()) {
					if (user != null? user.getName().equals(userName) : false) {
						this.users.get(userName).addFavourite(mealName);
						return;
					}
				}
			}
		}
	}
	
	public void removeFavourite(String userName, String mealName) { // Unchecked!!!
		synchronized(USERSLOCK) {
			if (new RemoveUserFavouriteDBStatement(mealName, userName).postQuery()) {
				for (User user: this.users.values()) {
					if (user != null? user.getName().equals(userName) : false) {
						this.users.get(userName).removeFavourite(mealName);
						return;
					}
				}
			}
		}
	}
	
	
	
	public User login(String remoteAddr, String name, String passwordMD5) {
		synchronized(this.USERSLOCK) {
			User user = new LoginDBStatement(name, passwordMD5).postQuery();
			this.users.put(remoteAddr, user);
			return user;
		}
	}
	
	public void logout(String remoteAddr, String name) {
		synchronized(this.USERSLOCK) {
			if (this.users.get(remoteAddr).getName().equals(name)) {
				this.users.remove(remoteAddr);
			}
		}
	}
}
