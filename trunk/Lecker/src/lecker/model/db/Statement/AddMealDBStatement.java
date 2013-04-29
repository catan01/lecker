package lecker.model.db.Statement;



import lecker.model.data.Meal;
import lecker.model.db.DBStatement;



/**
 * DBStatement to add a new meal.
 * 
 * @author LWagner
 *
 */
public class AddMealDBStatement implements DBStatement {
	private Meal meal;

	
	
	public AddMealDBStatement(Meal meal) {
		this.meal = meal;
	}
	
	
	
	@Override
	public void postStatement() {
		//TODO
	}

	/*
	 * not often used, so there is no need to prepare it.
	 */
	@Override
	public String[] getPrepareStatements() {
		return null;
	}
}
