package lecker.model.data;



import java.util.ArrayList;
import java.util.HashMap;



/**
 * Inherits all meals for one weak and one outlay
 * 
 * @author LWagner
 *
 */
public class Weekplan {
	public static enum Day {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday}
	
	private final HashMap<Day, ArrayList<Integer>> PLAN;
	


	public Weekplan() {
		PLAN = new HashMap<Day, ArrayList<Integer>>();
	}
	
	
	
	public Integer[] getMeals(Day day) {
		synchronized (this.PLAN) {
			try {
				return this.PLAN.get(day).toArray(new Integer[0]);
			} catch (NullPointerException exc) {
				return null;
			}
		}
	}
	
	
	
	public void addMeal(Day day, int mealID) {
		synchronized (this.PLAN) {
			if (!this.PLAN.containsKey(day)) {
				this.PLAN.put(day, new ArrayList<Integer>());
			}
			this.PLAN.get(day).add(mealID);
		}
	}
	
	public void removeMeal(Day day, int mealID) {
		synchronized (this.PLAN) {
			try {
				this.PLAN.get(day).remove(mealID);
				if (this.PLAN.get(day).isEmpty()) {
					this.PLAN.remove(day);
				}
			} catch (NullPointerException exc) { }
		}
	}
}
