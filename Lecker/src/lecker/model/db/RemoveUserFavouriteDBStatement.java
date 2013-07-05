package lecker.model.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import lecker.presenter.Handler;

public class RemoveUserFavouriteDBStatement implements DBStatement<Boolean> {
	PreparedStatement statement;
	
	
	
	public RemoveUserFavouriteDBStatement(String mealName, String userName) {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("DELETE FROM " + DBManager.TITLE_FAVORITE + " WHERE " + DBManager.TITLE_FAVORITE_MEAL + "='" + mealName + "' AND "
		+ DBManager.TITLE_FAVORITE_USER + "='" + userName + "';");
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
