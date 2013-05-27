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
	private ArrayList<Kategorie> kategories;
	private ArrayList<Label> labels;
	private HashMap<String, Meal> meals;
	private HashMap<Outlay, HashMap<Calendar, Plan>> plans;
	
	private final Object KATEGORIESLOCK = new Object();
	private final Object LABELSLOCK = new Object();
	private final Object MEALSLOCK = new Object();
	private final Object PLANSLOCK = new Object();
	
	
	
	public void init() {
		synchronized (this.KATEGORIESLOCK) {
			synchronized(this.LABELSLOCK) {
				synchronized (this.MEALSLOCK) {
					synchronized (this.PLANSLOCK) {
						if (this.kategories != null) {
							this.kategories = new ArrayList<Kategorie>();
							this.labels = new ArrayList<Label>();
							this.meals = new HashMap<String, Meal>();
							this.plans = new HashMap<Outlay, HashMap<Calendar, Plan>>();
	
							kategories.addAll(Arrays.asList(new GetKategoriesDBStatement().postQuery()));
							labels.addAll(Arrays.asList(new GetLabelsDBStatement().postQuery()));
							for (String name: Arrays.asList(new GetMealNamesDBStatement().postQuery())) {
								this.meals.put(name, null);
							}
							for (Outlay outlay: Arrays.asList(new GetOutlaysDBStatement().postQuery())) {
								this.plans.put(outlay, new HashMap<Calendar, Plan>());
							}
						}
					}
				}
			}
		}
	}
	
	public void destruct() {
		synchronized (this.KATEGORIESLOCK) {
			synchronized(this.LABELSLOCK) {
				synchronized (this.MEALSLOCK) {
					synchronized (this.PLANSLOCK) {
						if (this.kategories == null) {
							this.kategories = null;
							this.labels = null;
							this.meals = null;
							this.plans = null;
						}
					}
				}
			}
		}
	}
	

	
	public Kategorie[] getKategories() {
		synchronized (this.KATEGORIESLOCK) {
			return this.kategories.toArray(new Kategorie[0]);
		}
	}
	
	public Kategorie getKategorie(String name) {
		synchronized (this.KATEGORIESLOCK) {
			for (Kategorie kategorie: this.kategories) {
				if (kategorie.getName().equals(name)) {
					return kategorie;
				}
			}
			return null;
		}
	}
	
	public Label[] getLabels() {
		synchronized (this.LABELSLOCK) {
			return this.labels.toArray(new Label[0]);
		}
	}
	
	public Label getLabel(String name) {
		synchronized (this.LABELSLOCK) {
			for (Label label: this.labels) {
				if (label.getName().equals(name)) {
					return label;
				}
			}
			return null;
		}
	}
	
	public String[] getMealNames() {
		synchronized (this.MEALSLOCK) {
			return this.meals.keySet().toArray(new String[0]);
		}
	}
	
	public Meal getMeal(String name) {
		synchronized (this.MEALSLOCK) {
			if (this.meals.containsKey(name)? this.meals.get(name) != null : false) {
				return this.meals.get(name);
			}
			Meal meal = new GetMealDBStatement(name).postQuery();
			this.meals.put(meal.getName(), meal);
			return meal;
		}
	}
	
	public String[] getOutlays() {
		synchronized (this.PLANSLOCK) {
			return this.plans.keySet().toArray(new String[0]);
		}
	}
	
	public Outlay getOutlay(String name) {
		synchronized (this.PLANSLOCK) {
			for (Outlay outlay: this.plans.keySet()) {
				if (outlay.getName().equals(name)) {
					return outlay;
				}
			}
			return null;
		}
	}
	
	public Plan getPlan(Outlay outlay, Calendar date) {
		synchronized (this.PLANSLOCK) {
			if (this.plans.containsKey(outlay)? this.plans.get(outlay).containsKey(date) : false) {
				return this.plans.get(outlay).get(date);
			}
			Plan plan = new GetPlanDBStatement(outlay, date).postQuery();
			if (plan == null) {
				return null;
			}
			if (!this.plans.containsKey(outlay)) {
				this.plans.put(outlay, new HashMap<Calendar, Plan>());
			}
			this.plans.get(outlay).put(date, plan);
			return plan;
		}
	}
	
	
	
	public void addDate(String meal, Outlay outlay, Calendar date) {
		synchronized (this.MEALSLOCK) {
			synchronized (this.PLANSLOCK) {
				if ((this.meals.containsKey(meal) && this.plans.containsKey(outlay))? new AddMealDateDBStatement(meal, outlay, date).postQuery() : false) {
					if (meals.containsKey(meal)) {
						meals.get(meal).addDate(date);
					}
					if (plans.containsKey(outlay)? plans.get(outlay).containsKey(date) : false) {
						plans.get(outlay).get(date).addMeal(meals.get(meal));
					}
				}
			}
		}
	}
	
	public void removeDate(String meal, Outlay outlay, Calendar date) {
		synchronized (this.MEALSLOCK) {
			synchronized (this.PLANSLOCK) {
				if ((this.meals.containsKey(meal) && this.plans.containsKey(outlay))? new RemoveMealDateDBStatement(meal, outlay, date).postQuery() : false) {
					if (this.meals.containsKey(meal)) {
						this.meals.get(meal).removeDate(date);
					}
					if (this.plans.containsKey(outlay)? this.plans.get(outlay).containsKey(date) : false) {
						this.plans.get(outlay).get(date).removeMeal(meals.get(meal));
					}
				}
			}
		}
	}
	
	public void addLabel(String meal, Label label) {
		synchronized (this.MEALSLOCK) {
			synchronized (this.LABELSLOCK) {
				if ((this.meals.containsKey(meal) && this.labels.contains(label))? new AddMealLabelDBStatement(meal, label).postQuery() : false) {
					if (this.meals.containsKey(meal)) {
						this.meals.get(meal).addLabel(label);
					}
					if (!this.labels.contains(label)) {
						this.labels.add(label);
					}
				}
			}
		}
	}
	
	public void removeLabel(String meal, Label label) {
		synchronized (this.MEALSLOCK) {
			synchronized (this.LABELSLOCK) {
				if ((this.meals.containsKey(meal) && this.labels.contains(label))? new RemoveMealLabelDBStatement(meal, label).postQuery() : false) {
					if (meals.containsKey(meal)) {
						meals.get(meal).removeLabel(label);
					}
				}
			}
		}
	}
}
