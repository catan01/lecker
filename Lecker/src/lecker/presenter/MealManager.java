package lecker.presenter;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import lecker.model.data.Kategorie;
import lecker.model.data.Label;
import lecker.model.data.Meal;
import lecker.model.data.Outlay;
import lecker.model.data.Plan;
import lecker.model.db.AddMealDateDBStatement;
import lecker.model.db.AddMealLabelDBStatement;
import lecker.model.db.GetKategoriesDBStatement;
import lecker.model.db.GetLabelsDBStatement;
import lecker.model.db.GetMealDBStatement;
import lecker.model.db.GetMealNamesDBStatement;
import lecker.model.db.GetOutlaysDBStatement;
import lecker.model.db.GetPlanDBStatement;
import lecker.model.db.RemoveMealDateDBStatement;
import lecker.model.db.RemoveMealLabelDBStatement;



public class MealManager {
	private final ArrayList<Kategorie> KATEGORIES;
	private final ArrayList<Label> LABELS;
	private final HashMap<String, Meal> MEALS;
	private final HashMap<Outlay, HashMap<Calendar, Plan>> PLANS;
	
	
	
	public MealManager() {
		this.KATEGORIES = new ArrayList<Kategorie>();
		this.LABELS = new ArrayList<Label>();
		this.MEALS = new HashMap<String, Meal>();
		this.PLANS = new HashMap<Outlay, HashMap<Calendar, Plan>>();

		KATEGORIES.addAll(Arrays.asList(new GetKategoriesDBStatement().postQuery()));
		LABELS.addAll(Arrays.asList(new GetLabelsDBStatement().postQuery()));
		for (String name: Arrays.asList(new GetMealNamesDBStatement().postQuery())) {
			this.MEALS.put(name, null);
		}
		for (Outlay outlay: Arrays.asList(new GetOutlaysDBStatement().postQuery())) {
			this.PLANS.put(outlay, new HashMap<Calendar, Plan>());
		}
	}
	

	
	public Kategorie[] getKategories() {
		synchronized (this.KATEGORIES) {
			return this.KATEGORIES.toArray(new Kategorie[0]);
		}
	}
	
	public Kategorie getKategorie(String name) {
		synchronized (this.KATEGORIES) {
			for (Kategorie kategorie: this.KATEGORIES) {
				if (kategorie.getName().equals(name)) {
					return kategorie;
				}
			}
			return null;
		}
	}
	
	public Label[] getLabels() {
		synchronized (this.LABELS) {
			return this.LABELS.toArray(new Label[0]);
		}
	}
	
	public Label getLabel(String name) {
		synchronized (this.LABELS) {
			for (Label label: this.LABELS) {
				if (label.getName().equals(name)) {
					return label;
				}
			}
			return null;
		}
	}
	
	public String[] getMealNames() {
		synchronized (this.MEALS) {
			return this.MEALS.keySet().toArray(new String[0]);
		}
	}
	
	public Meal getMeal(String name) {
		synchronized (this.MEALS) {
			if (this.MEALS.containsKey(name)? this.MEALS.get(name) != null : false) {
				return this.MEALS.get(name);
			}
			Meal meal = new GetMealDBStatement(name).postQuery();
			this.MEALS.put(meal.getName(), meal);
			return meal;
		}
	}
	
	public Outlay[] getOutlays() {
		synchronized (this.PLANS) {
			Outlay[] outlays = this.PLANS.keySet().toArray(new Outlay[0]);
			Arrays.sort(outlays);
			return outlays;
		}
	}
	
	public Outlay getOutlay(String name) {
		synchronized (this.PLANS) {
			for (Outlay outlay: this.PLANS.keySet()) {
				if (outlay.getName().equals(name)) {
					return outlay;
				}
			}
			return null;
		}
	}
	
	public Plan getPlan(Outlay outlay, Calendar date) {
		synchronized (this.PLANS) {
			if (this.PLANS.containsKey(outlay)? this.PLANS.get(outlay).containsKey(date) : false) {
				return this.PLANS.get(outlay).get(date);
			}
			Plan plan = new GetPlanDBStatement(outlay, date).postQuery();
			if (plan == null) {
				return null;
			}
			if (!this.PLANS.containsKey(outlay)) {
				this.PLANS.put(outlay, new HashMap<Calendar, Plan>());
			}
			this.PLANS.get(outlay).put(date, plan);
			return plan;
		}
	}
	
	
	
	public void addDate(String meal, Outlay outlay, Calendar date) {
		synchronized (this.MEALS) {
			synchronized (this.PLANS) {
				if ((this.MEALS.containsKey(meal) && this.PLANS.containsKey(outlay))? new AddMealDateDBStatement(meal, outlay, date).postQuery() : false) {
					if (MEALS.containsKey(meal)) {
						MEALS.get(meal).addDate(date);
					}
					if (PLANS.containsKey(outlay)? PLANS.get(outlay).containsKey(date) : false) {
						PLANS.get(outlay).get(date).addMeal(MEALS.get(meal));
					}
				}
			}
		}
	}
	
	public void removeDate(String meal, Outlay outlay, Calendar date) {
		synchronized (this.MEALS) {
			synchronized (this.PLANS) {
				if ((this.MEALS.containsKey(meal) && this.PLANS.containsKey(outlay))? new RemoveMealDateDBStatement(meal, outlay, date).postQuery() : false) {
					if (this.MEALS.containsKey(meal)) {
						this.MEALS.get(meal).removeDate(date);
					}
					if (this.PLANS.containsKey(outlay)? this.PLANS.get(outlay).containsKey(date) : false) {
						this.PLANS.get(outlay).get(date).removeMeal(MEALS.get(meal));
					}
				}
			}
		}
	}
	
	public void addLabel(String meal, Label label) {
		synchronized (this.MEALS) {
			synchronized (this.LABELS) {
				if ((this.MEALS.containsKey(meal) && this.LABELS.contains(label))? new AddMealLabelDBStatement(meal, label).postQuery() : false) {
					if (this.MEALS.containsKey(meal)) {
						this.MEALS.get(meal).addLabel(label);
					}
					if (!this.LABELS.contains(label)) {
						this.LABELS.add(label);
					}
				}
			}
		}
	}
	
	public void removeLabel(String meal, Label label) {
		synchronized (this.MEALS) {
			synchronized (this.LABELS) {
				if ((this.MEALS.containsKey(meal) && this.LABELS.contains(label))? new RemoveMealLabelDBStatement(meal, label).postQuery() : false) {
					if (MEALS.containsKey(meal)) {
						MEALS.get(meal).removeLabel(label);
					}
				}
			}
		}
	}
}
