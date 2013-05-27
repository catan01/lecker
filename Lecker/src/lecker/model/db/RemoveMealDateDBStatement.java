package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import lecker.model.data.Outlay;
import lecker.presenter.Handler;



public class RemoveMealDateDBStatement implements DBStatement<Boolean> {
	PreparedStatement statement;
	
	
	
	public RemoveMealDateDBStatement(String mealName, Outlay outlay, Calendar date) {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("DELETE FROM " + DBManager.TITLE_PLAN + " WHERE " + DBManager.TITLE_PLAN_MEAL + "='" + mealName + "' AND "
		+ DBManager.TITLE_PLAN_OUTLAY + "='" + outlay.getName() + "' AND " + DBManager.TITLE_PLAN_DAY + "='" + date.getTime() + "';");
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
