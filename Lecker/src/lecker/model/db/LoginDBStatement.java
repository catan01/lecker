package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import lecker.model.data.User;
import lecker.presenter.Handler;



public class LoginDBStatement implements DBStatement<User> {
	PreparedStatement statement;
	

	
	
	public LoginDBStatement(String name, String password) {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("SELECT * FROM " + DBManager.TITLE_USER + " WHERE " + DBManager.TITLE_USER_NAME + "='" + name + "', " + DBManager.TITLE_USER_PW + "='" + password + "';");
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
	}
	
	
	
	@Override
	public User postQuery() {
		try {
			return new User(statement.executeQuery().getString("name"));
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
		return null;
	}
}
