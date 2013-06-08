package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lecker.model.data.User;
import lecker.presenter.Handler;



public class LoginDBStatement implements DBStatement<User> {
	PreparedStatement statement;
	

	
	
	public LoginDBStatement(String name, String password) {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("SELECT * FROM " + DBManager.TITLE_USER + " WHERE " + DBManager.TITLE_USER_NAME + "='" + name + "' AND " + DBManager.TITLE_USER_PW + "='" + password + "';");
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
	}
	
	
	
	@Override
	public User postQuery() {
		try {
			ResultSet set = statement.executeQuery();
			if (set.next()) {
				return new User(set.getString("name"));
			}
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
		return null;
	}
}
