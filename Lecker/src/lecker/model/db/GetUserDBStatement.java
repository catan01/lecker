package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lecker.model.data.User;
import lecker.presenter.Handler;



public class GetUserDBStatement implements DBStatement<User> {
	protected PreparedStatement statementUser;
	private PreparedStatement statementFavorites;
	

	
	
	public GetUserDBStatement(String name, String password) {
		try {
			statementUser = Handler.getInstance().getDBManager().prepareStatement("SELECT * FROM " + DBManager.TITLE_USER + " WHERE " + DBManager.TITLE_USER_NAME + "='" + name + "' AND " + DBManager.TITLE_USER_PW + "='" + password + "';");
			statementFavorites = Handler.getInstance().getDBManager().prepareStatement("SELECT " + DBManager.TITLE_FAVORITE_MEAL + " FROM " + DBManager.TITLE_FAVORITE + " WHERE " + DBManager.TITLE_FAVORITE_USER + "='" + name + "';");
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
	}
	
	
	
	@Override
	public User postQuery() {
		try {
			ResultSet favouritesSet = statementFavorites.executeQuery();
			ResultSet userSet = statementUser.executeQuery();
			if (userSet.next()) {
				User user = new User(userSet.getString("name"));
				
				while (favouritesSet.next()) {
					user.addFavorite(favouritesSet.getString(DBManager.TITLE_FAVORITE_MEAL));
				}
				return user;
			}
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
		return null;
	}
}
