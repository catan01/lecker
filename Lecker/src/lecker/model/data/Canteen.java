package lecker.model.data;



import java.util.HashMap;



/**
 * Represents one canteen. Could have more than one outlay.
 * 
 * @author LWagner
 *
 */
public class Canteen {
	final private HashMap<Integer, HashMap<String, Weekplan>> plans = new HashMap<Integer, HashMap<String, Weekplan>>();
	
	
	
	public Weekplan getWeekplan(int week, String outlet) {
		synchronized (plans) {
			return this.plans.get(week).get(outlet);
		}
	}
}
