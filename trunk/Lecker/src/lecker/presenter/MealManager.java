package lecker.presenter;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import lecker.model.data.Canteen;
import lecker.model.data.Kategorie;
import lecker.model.data.Meal;
import lecker.model.db.GetKategoriesDBStatement;
import lecker.model.db.GetMealDBStatement;
import lecker.model.db.GetMealNamesDBStatement;



public class MealManager {
	private static HashMap<String, Canteen> canteens;
	private static HashMap<Integer, Meal> meals;
	private static ArrayList<String> names;
	private static ArrayList<Kategorie> kategories;
	
	
	
	public static synchronized void init() {
		canteens = new HashMap<String, Canteen>();
		meals = new HashMap<Integer, Meal>();
		names = new ArrayList<String>();
		kategories = new ArrayList<Kategorie>();
		
		names.addAll(Arrays.asList(new GetMealNamesDBStatement().postQuery()));
		kategories.addAll(Arrays.asList(new GetKategoriesDBStatement().postQuery()));
	}
	
	public static synchronized void destruct() {
		canteens = null;
		meals = null;
		names = null;
		kategories = null;
	}
	

	
	public static Kategorie[] getKategories() {
		return kategories.toArray(new Kategorie[0]);
	}
	
	public static Kategorie getKategorie(String name) {
		for (Kategorie kategorie: kategories) {
			if (kategorie.getName().equals(name)) {
				return kategorie;
			}
		}
		return null;
	}
	
	public static Canteen getCanteen(String name) {
		synchronized(canteens) {
			return canteens.get(name);
		}
	}
	
	public static String[] getMealNames() {
		synchronized (names) {
			return names.toArray(new String[0]);
		}
	}
	
	public static Meal getMeal(String name) {
		synchronized (meals) {
			//TODO
			return null;
		}
	}
	
	public static Meal getMeal(int id) {
		synchronized (meals) {
			if (meals.containsKey(id)) {
				return meals.get(id);
			}
			Meal meal = new GetMealDBStatement(id).postQuery();
			meals.put(meal.getID(), meal);
			if (!names.contains(meal.getName())) {
				names.add(meal.getName());
			}
			return meal;
		}
	}
}
