package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import lecker.presenter.ExceptionHandler;



public class AddUserDBStatement implements DBStatement<Boolean> {
	private PreparedStatement statement;
	
	
	
	public AddUserDBStatement(String name, String password) {
		try {
			statement = DBManager.prepareStatement("INSERT INTO " + DBManager.TITLE_USER + " (" + DBManager.TITLE_USER_NAME + ", " + DBManager.TITLE_USER_PW + ") " +
					"VALUES (" + name + ", " + password + ");");
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
	}
	
	
	
	@Override
	public Boolean postQuery() {
		try {
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		return false;
	}

}
