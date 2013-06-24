package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import lecker.model.data.comments.Comment;
import lecker.presenter.Handler;



public class AddMealCommentDBStatement implements DBStatement<Void> {
	PreparedStatement statement;
	
	
	
	public AddMealCommentDBStatement(String mealName, String userName , Comment comment) {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("INSERT INTO " + DBManager.TITLE_COMMENT + " (" + DBManager.TITLE_COMMENT_RATING + "," + DBManager.TITLE_COMMENT_COMMENT + "," + DBManager.TITLE_COMMENT_MEAL + "," + DBManager.TITLE_COMMENT_USER + ")" +
					"VALUES ('" + comment.getRating() + "','" + comment.getComment() + "','" + mealName + "','" + userName + "');");
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
	}
	
	
	
	@Override
	public Void postQuery() {
		try {
			statement.executeUpdate();
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
		return null;
	}
}
