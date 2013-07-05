package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import lecker.presenter.Handler;



public class AddUserFavoriteDBStatement implements DBStatement<Boolean> {
	PreparedStatement statement;
	

	
	
	public AddUserFavoriteDBStatement(String mealName, String userName) {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("INSERT INTO " + DBManager.TITLE_FAVORITE + " (" + DBManager.TITLE_FAVORITE_MEAL + "," + DBManager.TITLE_FAVORITE_USER + ") " +
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
