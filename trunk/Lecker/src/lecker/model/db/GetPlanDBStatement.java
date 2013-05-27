package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import lecker.model.data.Outlay;
import lecker.model.data.Plan;
import lecker.presenter.Handler;



public class GetPlanDBStatement implements DBStatement<Plan> {
	PreparedStatement statement;
	

	
	
	public GetPlanDBStatement(Outlay outlay, Calendar date) {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("SELECT " + DBManager.TITLE_PLAN_MEAL + " FROM " + DBManager.TITLE_PLAN + " WHERE " + DBManager.TITLE_PLAN_OUTLAY + "='" + outlay
					+  "' AND " + DBManager.TITLE_PLAN_DAY + "='" + date.getTime() + "';");
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
	}
	
	
	
	@Override
	public Plan postQuery() {
		try {
			ResultSet set = statement.executeQuery();
			Plan plan = new Plan();
			
			while (set.next()) {
				plan.addMeal(Handler.getInstance().getMealManager().getMeal(set.getString(DBManager.TITLE_PLAN_MEAL)));
			}
			
			return plan;
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
		return null;
	}
}
