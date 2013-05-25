package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.SQLException;

import lecker.model.data.comments.Comment;
import lecker.presenter.ExceptionHandler;



public class AddMealCommentDBStatement implements DBStatement<Void> {
	PreparedStatement statement;
	
	
	
	public AddMealCommentDBStatement(int mealID, String userName , Comment comment) {
		try {
			statement = DBManager.prepareStatement("INSERT INTO " + DBManager.TITLE_COMMENT + " (" + DBManager.TITLE_COMMENT_RATING + "," + DBManager.TITLE_COMMENT_COMMENT + "," + DBManager.TITLE_COMMENT_MEAL + "," + DBManager.TITLE_COMMENT_USER + ")" +
					"VALUES (" + comment.getRating() + ", " + comment.getComment() + ", " + mealID + ", " + userName + ");");
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
	}
	
	
	
	@Override
	public Void postQuery() {
		try {
			statement.executeUpdate();
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		return null;
	}
}
