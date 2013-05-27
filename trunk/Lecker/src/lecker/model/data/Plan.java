package lecker.model.data;



import java.util.ArrayList;
import java.util.HashMap;

import lecker.presenter.Handler;



/**
 * Inherits all meals for one weak and one outlay
 * 
 * @author LWagner
 *
 */
public class Plan {
	private final HashMap<Kategorie, ArrayList<String>> PLAN;
	


	public Plan() {
		PLAN = new HashMap<Kategorie, ArrayList<String>>();
		for (Kategorie kategorie: Handler.getInstance().getMealManager().getKategories()) {
			PLAN.put(kategorie, new ArrayList<String>());
		}
	}
	
	
	
	public String[] getMeals(Kategorie kategorie) {
		synchronized (this.PLAN) {
			try {
				return this.PLAN.get(kategorie).toArray(new String[0]);
			} catch (NullPointerException exc) {
				return null;
			}
		}
	}
	
	
	
	public void addMeal(Meal meal) {
		synchronized (this.PLAN) {
			if (!this.PLAN.containsKey(meal.getKategorie())) {
				this.PLAN.put(meal.getKategorie(), new ArrayList<String>());
			}
			this.PLAN.get(meal.getKategorie()).add(meal.getName());
		}
	}
	
	public void removeMeal(Meal meal) {
		synchronized (this.PLAN) {
			try {
				this.PLAN.get(meal.getKategorie()).remove(meal.getName());
				if (this.PLAN.get(meal.getKategorie()).isEmpty()) {
					this.PLAN.remove(meal.getKategorie());
				}
			} catch (NullPointerException exc) { }
		}
	}
}
