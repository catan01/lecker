package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lecker.presenter.ExceptionHandler;



public class GetMealNamesDBStatement implements DBStatement<String[]> {
	private PreparedStatement statement;
	
	
	
	public GetMealNamesDBStatement() {
		try {
			statement = DBManager.prepareStatement("SELECT " + DBManager.TITLE_MEAL_NAME + " FROM " + DBManager.TITLE_MEAL + ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			ExceptionHandler.handle(e);
		}
		return null;
	}

}
