package lecker.model.data;



import java.util.ArrayList;
import java.util.Arrays;



/**
 * One complete meal.
 * 
 * @author LWagner
 *
 */
public class Meal {
	public static enum Type {Bla, Blubb} //TODO
	
	private String name;
	private ArrayList<MealPiece> pieces;
	private Integer price;
	private Type type;
	
	
	
	public Meal(String name, int price, Type type) {
		this.name = name;
		pieces = new ArrayList<MealPiece>();
		this.price = price;
		this.type = type;
	}
	
	public Meal(String name, MealPiece[] pieces, int price, Type type) {
		this(name, price, type);
		setPieces(pieces);
	}
	
	
	
	public String getName() {
		synchronized (this.name) {
			return this.name;
		}
	}
	
	public MealPiece[] getPieces() {
		synchronized(this.pieces) {
			return pieces.toArray(new MealPiece[0]);
		}
	}
	
	public int getPrice() {
		synchronized(this.price) {
			return price;
		}
	}
	public Type getType() {
		synchronized(this.type) {
			return type;
		}
	}
	
	public void setPieces(MealPiece[] pieces) {
		synchronized(this.pieces) {
			this.pieces.clear();
			this.pieces.addAll(Arrays.asList(pieces));
		}
	}
	
	public void setPrice(int price) {
		synchronized(this.price) {
			this.price = price;
		}
	}
	
	public void setType(Type type) {
		synchronized(this.type) {
			this.type = type;
		}
	}
}
