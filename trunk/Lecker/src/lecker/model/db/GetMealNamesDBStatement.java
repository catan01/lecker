package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lecker.presenter.Handler;



public class GetMealNamesDBStatement implements DBStatement<String[]> {
	private PreparedStatement statement;
	
	
	
	public GetMealNamesDBStatement() {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("SELECT " + DBManager.TITLE_MEAL_NAME + " FROM " + DBManager.TITLE_MEAL + ";");
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
	}
	
	
	
	@Override
	public String[] postQuery() {
		try {
			ArrayList<String> names = new ArrayList<String>();
			ResultSet set = statement.executeQuery();
		
			while (set.next()) {
				names.add(set.getString(DBManager.TITLE_MEAL_NAME));
			}
			
			return names.toArray(new String[0]);
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
		return null;
	}
}
