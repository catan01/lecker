package lecker.model;



import java.util.HashMap;

import lecker.model.data.Canteen;



public class CanteenManager {
	private static HashMap<String, Canteen> canteens;
	
	
	
	public static synchronized void init() {
		//TODO
	}
	
	
	
	public static Canteen getCanteen(String name) {
		synchronized(canteens) {
			return canteens.get(name);
		}
	}
}
