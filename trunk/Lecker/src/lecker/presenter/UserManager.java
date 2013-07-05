package lecker.presenter;



import java.util.HashMap;

import lecker.model.data.User;
import lecker.model.db.AddUserFavoriteDBStatement;
import lecker.model.db.GetUserDBStatement;
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
	
	
	
	public void addFavorite(String userName, String mealName) { // Unchecked!!!
		synchronized(USERSLOCK) {
			if (new AddUserFavoriteDBStatement(mealName, userName).postQuery()) {
				for (User user: this.users.values()) {
					if (user != null? user.getName().equals(userName) : false) {
						this.users.get(userName).addFavorite(mealName);
						return;
					}
				}
			}
		}
	}
	
	public void removeFavorite(String userName, String mealName) { // Unchecked!!!
		synchronized(USERSLOCK) {
			if (new RemoveUserFavouriteDBStatement(mealName, userName).postQuery()) {
				for (User user: this.users.values()) {
					if (user != null? user.getName().equals(userName) : false) {
						this.users.get(userName).removeFavorite(mealName);
						return;
					}
				}
			}
		}
	}
	
	
	
	public User login(String remoteAddr, String name, String passwordMD5) {
		synchronized(this.USERSLOCK) {
			User user = new GetUserDBStatement(name, passwordMD5).postQuery();
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
