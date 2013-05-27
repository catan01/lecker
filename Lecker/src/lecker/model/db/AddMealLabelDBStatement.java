package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import lecker.model.data.Label;
import lecker.presenter.Handler;



public class AddMealLabelDBStatement implements DBStatement<Boolean> {
	PreparedStatement statement;
	
	
	
	public AddMealLabelDBStatement(String mealName, Label label) {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("INSERT INTO " + DBManager.TITLE_LABELING + " (" + DBManager.TITLE_LABELING_MEAL + "," + DBManager.TITLE_LABELING_LABEL  + ") " +
					"VALUES ('" + mealName + "','" + label.getName() + "');");
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
