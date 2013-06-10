package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import lecker.model.data.Kategorie;
import lecker.presenter.Handler;



/**
 * DBStatement to add a new meal.
 * 
 * @author LWagner
 *
 */
public class AddMealDBStatement implements DBStatement<Boolean> {
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
	public Boolean postQuery() {
		try {
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
		return false;
	}
}
