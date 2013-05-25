package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import lecker.model.data.Kategorie;
import lecker.model.data.Meal;
import lecker.presenter.ExceptionHandler;



/**
 * DBStatement to add a new meal.
 * 
 * @author LWagner
 *
 */
public class AddMealDBStatement implements DBStatement<Meal> {
	PreparedStatement statementAdd;
	PreparedStatement statementRequest;
	private String name;
	private int preis;
	private Kategorie kategorie;
	

	
	
	public AddMealDBStatement(String name, int preis, Kategorie kategorie) {
		try {
			statementAdd = DBManager.prepareStatement("INSERT INTO " + DBManager.TITLE_MEAL + " (" + DBManager.TITLE_MEAL_NAME + "," + DBManager.TITLE_MEAL_PRICE + "," + DBManager.TITLE_MEAL_KATEGORIE + ") " +
					"VALUES (" + name + ", " + preis + ", " + kategorie + ");");
			statementRequest = DBManager.prepareStatement("SELECT " + DBManager.TITLE_MEAL_ID + " FROM " + DBManager.TITLE_MEAL + " WHERE " + DBManager.TITLE_MEAL_NAME + "=" + name + ";");
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
	}
	
	
	
	@Override
	public Meal postQuery() {
		try {
			statementAdd.executeUpdate();
			return new Meal(statementAdd.executeQuery().getInt("Speise_ID"), name, preis, kategorie);
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		return null;
	}
}
