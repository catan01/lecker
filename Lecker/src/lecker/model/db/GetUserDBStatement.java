package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lecker.model.data.User;
import lecker.presenter.Handler;



public class GetUserDBStatement implements DBStatement<User> {
	private PreparedStatement statementUser;
	private PreparedStatement statementFavourites;
	

	
	
	public GetUserDBStatement(String name) {
		try {
			statementUser = Handler.getInstance().getDBManager().prepareStatement("SELECT * FROM " + DBManager.TITLE_USER + " WHERE " + DBManager.TITLE_USER_NAME + "='" + name + "';");
			statementFavourites = Handler.getInstance().getDBManager().prepareStatement("SELECT " + DBManager.TITLE_FAVOURITE_MEAL + " FROM " + DBManager.TITLE_FAVOURITE + " WHERE " + DBManager.TITLE_FAVOURITE_USER + "='" + name + "';");
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
	}
	
	
	
	@Override
	public User postQuery() {
		try {
			ResultSet favouritesSet = statementFavourites.executeQuery();
			ResultSet userSet = statementUser.executeQuery();
			if (userSet.next()) {
				User user = new User(userSet.getString("name"));
				
				while (favouritesSet.next()) {
					user.addFavourite(favouritesSet.getString(DBManager.TITLE_FAVOURITE_MEAL));
				}
				
				return user;
			}
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
		return null;
	}
}
