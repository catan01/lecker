package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lecker.model.data.Meal;
import lecker.model.data.comments.Comment;
import lecker.presenter.ExceptionHandler;
import lecker.presenter.MealManager;



public class GetMealDBStatement implements DBStatement<Meal> {
	private PreparedStatement statementMeal;
	private PreparedStatement statementComments;
	
	
	
	public GetMealDBStatement(String name) {
		try {
			statementMeal = DBManager.prepareStatement("SELECT * FROM " + DBManager.TITLE_MEAL + " WHERE " + DBManager.TITLE_MEAL_NAME + "=" + name + ");");
			statementComments = DBManager.prepareStatement("SELECT * FROM " + DBManager.TITLE_COMMENT + " JOIN " + DBManager.TITLE_MEAL + " " +
					"ON " + DBManager.TITLE_MEAL + "." + DBManager.TITLE_MEAL + "=" + DBManager.TITLE_COMMENT + "." + DBManager.TITLE_COMMENT_MEAL + " WHERE " + DBManager.TITLE_COMMENT + "." + DBManager.TITLE_COMMENT_MEAL + "=" + name + ");"); // Richtig?
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
	}
	
	public GetMealDBStatement(int id) {
		try {
			statementMeal = DBManager.prepareStatement("SELECT * FROM " + DBManager.TITLE_MEAL + " WHERE " + DBManager.TITLE_MEAL_ID + "=" + id + ");");
			statementComments = DBManager.prepareStatement("SELECT * FROM " + DBManager.TITLE_COMMENT + " WHERE " + DBManager.TITLE_COMMENT_MEAL + "=" + id + ");");
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
	}
	
	
	
	@Override
	public Meal postQuery() {
		try {
			ResultSet mealSet = statementMeal.executeQuery();
			ResultSet commentsSet = statementComments.executeQuery();
			
			Meal meal = new Meal(mealSet.getInt(DBManager.TITLE_MEAL_ID), mealSet.getString(DBManager.TITLE_MEAL_NAME), mealSet.getInt(DBManager.TITLE_MEAL_PRICE), MealManager.getKategorie(mealSet.getString(DBManager.TITLE_MEAL_KATEGORIE)));
			while (commentsSet.next()) {
				meal.addComment(new Comment(commentsSet.getInt(DBManager.TITLE_COMMENT_RATING), commentsSet.getString(DBManager.TITLE_COMMENT_COMMENT)));
			}
			
			return meal;
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
		}
		return null;
	}
}
