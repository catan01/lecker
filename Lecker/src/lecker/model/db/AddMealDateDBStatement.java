package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import lecker.presenter.ExceptionHandler;




public class AddMealDateDBStatement implements DBStatement<Void> {
	PreparedStatement statement;
	
	
	
	public AddMealDateDBStatement(long timestamp, int mealID, String outlet) {
		try {
			statement = DBManager.prepareStatement("INSERT INTO " + DBManager.TITLE_WEAKPLAN + " (" + DBManager.TITLE_WEAKPLAN_MEAL + ", " + DBManager.TITLE_WEAKPLAN_OUTLAY + "," + DBManager.TITLE_WEAKPLAN_DAY + ") " +
					"VALUES (" + mealID + ", " + outlet + ", " + timestamp + ");");
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
	}
	
	
	
	@Override
	public Void postQuery() {
		try {
			statement.executeUpdate();
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		return null;
	}
}
