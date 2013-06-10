package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import lecker.model.data.Outlay;
import lecker.presenter.Handler;



public class AddMealDateDBStatement implements DBStatement<Boolean>{
	PreparedStatement statement;
	
	
	
	public AddMealDateDBStatement(String mealName, Outlay outlay, Calendar date) {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("INSERT INTO " + DBManager.TITLE_PLAN + " (" + DBManager.TITLE_PLAN_MEAL + "," + DBManager.TITLE_PLAN_OUTLAY + "," + DBManager.TITLE_PLAN_DAY + ") " +
					"VALUES ('" + mealName + "','" + outlay.getName() + "','" + new java.sql.Date(date.getTimeInMillis()) + "');");
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
