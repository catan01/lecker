package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import lecker.model.data.Label;
import lecker.presenter.Handler;



public class RemoveMealLabelDBStatement implements DBStatement<Boolean> {
	PreparedStatement statement;
	
	
	
	public RemoveMealLabelDBStatement(String mealName, Label label) {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("DELETE FROM " + DBManager.TITLE_LABELING + " WHERE " + DBManager.TITLE_LABELING_MEAL + "='" + mealName + "' AND "
		+ DBManager.TITLE_LABELING_LABEL + "='" + label.getName() + "';");
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
