package lecker.model.data;



import java.util.ArrayList;
import java.util.Arrays;



public class User {
	private final ArrayList<String> FAVOURITES;
	private final String NAME;
	
	
	
	public User(String name) {
		this.NAME = name;
		this.FAVOURITES = new ArrayList<String>();
	}
	
	public User(String name, String[] favourites) {
		this(name);
		this.FAVOURITES.addAll(Arrays.asList(favourites));
	}
	
	
	
	public String[] getFavourites() {
		synchronized(this.FAVOURITES) {
			return this.FAVOURITES.toArray(new String[0]);
		}
	}
	
	public String getName() {
		synchronized(this.NAME) {
			return this.NAME;
		}
	}
	
	
	
	public void addFavourite(String mealName) {
		synchronized(this.FAVOURITES) {
			this.FAVOURITES.add(mealName);
		}
	}
	
	public void removeFavourite(String mealName) {
		synchronized(this.FAVOURITES) {
			this.FAVOURITES.remove(mealName);
		}
	}
}
