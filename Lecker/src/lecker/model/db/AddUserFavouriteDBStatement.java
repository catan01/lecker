package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import lecker.presenter.Handler;



public class AddUserFavouriteDBStatement implements DBStatement<Boolean> {
	PreparedStatement statement;
	

	
	
	public AddUserFavouriteDBStatement(String mealName, String userName) {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("INSERT INTO " + DBManager.TITLE_FAVOURITE + " (" + DBManager.TITLE_FAVOURITE_MEAL + "," + DBManager.TITLE_FAVOURITE_USER + ") " +
					"VALUES ('" + mealName + "','" + userName + "');");
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
