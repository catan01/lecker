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
	
	private HashMap<Day, ArrayList<Meal>> plan;
	


	public Weekplan() {
		plan = new HashMap<Day, ArrayList<Meal>>();
	}
	
	
	
	public Meal[] getMeals(Day day) {
		synchronized (this.plan) {
			try {
				return this.plan.get(day).toArray(new Meal[0]);
			} catch (NullPointerException exc) {
				return null;
			}
		}
	}
	
	
	
	public void addMeal(Day day, Meal meal) {
		synchronized (this.plan) {
			if (!this.plan.containsKey(day)) {
				this.plan.put(day, new ArrayList<Meal>());
			}
			this.plan.get(day).add(meal);
		}
	}
	
	public void removeMeal(Day day, Meal meal) {
		synchronized (this.plan) {
			try {
				this.plan.get(day).remove(meal);
				if (this.plan.get(day).isEmpty()) {
					this.plan.remove(day);
				}
			} catch (NullPointerException exc) { }
		}
	}
}
