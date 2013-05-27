package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import lecker.model.data.Kategorie;
import lecker.model.data.Label;
import lecker.model.data.Meal;
import lecker.presenter.Handler;



/**
 * DBStatement to add a new meal.
 * 
 * @author LWagner
 *
 */
public class AddMealDBStatement implements DBStatement<Meal> {
	PreparedStatement statement;
	

	
	
	public AddMealDBStatement(String name, int preis, Kategorie kategorie) {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("INSERT INTO " + DBManager.TITLE_MEAL + " (" + DBManager.TITLE_MEAL_NAME + "," + DBManager.TITLE_MEAL_PRICE + "," + DBManager.TITLE_MEAL_KATEGORIE + ") " +
					"VALUES ('" + name + "'," + preis + ",'" + kategorie + "');");
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
	}
	
	
	
	@Override
	public Meal postQuery() {
		try {
			statement.executeUpdate();
			ResultSet set = statement.executeQuery();
			if (set.next()) {
				return new Meal(set.getString(DBManager.TITLE_MEAL_NAME), set.getInt(DBManager.TITLE_MEAL_PRICE), Handler.getInstance().getMealManager().getKategorie(set.getString(DBManager.TITLE_MEAL_KATEGORIE)), new Calendar[0], new Label[0]);
			}
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
		return null;
	}
}
