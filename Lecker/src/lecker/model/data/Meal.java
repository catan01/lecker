package lecker.model.data;



import lecker.model.data.comments.Commentable;



/**
 * One complete meal.
 * 
 * @author LWagner
 *
 */
public class Meal extends Commentable {
	private final Integer ID;
	private String name;
	private Integer price;
	private Kategorie kategorie;
	
	
	
	public Meal(int id, String name, int price, Kategorie kategorie) {
		this.ID = id;
		this.name = name;
		this.price = price;
		this.kategorie = kategorie;
	}
	
	
	
	public int getID() {
		synchronized (ID) {
			return this.ID;
		}
	}
	
	public String getName() {
		synchronized (this.name) {
			return this.name;
		}
	}
	
	public int getPrice() {
		synchronized(this.price) {
			return price;
		}
	}
	
	public Kategorie getkategorie() {
		synchronized(this.kategorie) {
			return kategorie;
		}
	}
	
	public void setPrice(int price) {
		synchronized(this.price) {
			this.price = price;
		}
	}
	
	public void setKategorie(Kategorie kategorie) {
		synchronized(this.kategorie) {
			this.kategorie = kategorie;
		}
	}
}
