package lecker.model.data;



import java.util.ArrayList;
import java.util.Arrays;



public class User {
	private final ArrayList<String> FAVORITES;
	private final String NAME;
	
	
	
	public User(String name) {
		this.NAME = name;
		this.FAVORITES = new ArrayList<String>();
	}
	
	public User(String name, String[] favorites) {
		this(name);
		this.FAVORITES.addAll(Arrays.asList(favorites));
	}
	
	
	
	public String[] getFavorites() {
		synchronized(this.FAVORITES) {
			return this.FAVORITES.toArray(new String[0]);
		}
	}
	
	public String getName() {
		synchronized(this.NAME) {
			return this.NAME;
		}
	}
	
	
	
	public void addFavorite(String mealName) {
		synchronized(this.FAVORITES) {
			this.FAVORITES.add(mealName);
		}
	}
	
	public void removeFavorite(String mealName) {
		synchronized(this.FAVORITES) {
			this.FAVORITES.remove(mealName);
		}
	}
}
