package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import lecker.model.data.Label;
import lecker.model.data.Meal;
import lecker.model.data.comments.Comment;
import lecker.presenter.Handler;



public class GetMealDBStatement implements DBStatement<Meal> {
	private PreparedStatement statementMeal;
	private PreparedStatement statementComments;
	private PreparedStatement statementDates;
	private PreparedStatement statementLabels;
	
	
	
	public GetMealDBStatement(String name) {
		try {
			statementMeal = Handler.getInstance().getDBManager().prepareStatement("SELECT * FROM " + DBManager.TITLE_MEAL + " WHERE " + DBManager.TITLE_MEAL_NAME + "='" + name + "';");
			statementComments = Handler.getInstance().getDBManager().prepareStatement("SELECT * FROM " + DBManager.TITLE_COMMENT + " WHERE " + DBManager.TITLE_COMMENT_MEAL + "='" + name + "';");
			statementDates = Handler.getInstance().getDBManager().prepareStatement("SELECT " + DBManager.TITLE_PLAN_DAY + " FROM " + DBManager.TITLE_PLAN + " WHERE " + DBManager.TITLE_PLAN_MEAL + "='" + name + "';");
			statementLabels = Handler.getInstance().getDBManager().prepareStatement("SELECT " + DBManager.TITLE_LABELING_LABEL + " FROM " + DBManager.TITLE_LABELING + " WHERE " + DBManager.TITLE_LABELING_MEAL + "='" + name + "';");
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
	}
	
	
	
	@Override
	public Meal postQuery() {
		try {
			ResultSet mealSet = statementMeal.executeQuery();
			ResultSet commentsSet = statementComments.executeQuery();
			ResultSet datesSet = statementDates.executeQuery();
			ResultSet labelsSet = statementLabels.executeQuery();
			if (mealSet.next()) {
				Meal meal = new Meal(mealSet.getString(DBManager.TITLE_MEAL_NAME), mealSet.getInt(DBManager.TITLE_MEAL_PRICE), Handler.getInstance().getMealManager().getKategorie(mealSet.getString(DBManager.TITLE_MEAL_KATEGORIE)), new Calendar[0], new Label[0], mealSet.getBoolean(DBManager.TITLE_MEAL_PICTURE));
				while (commentsSet.next()) {
					meal.addComment(new Comment(commentsSet.getString(DBManager.TITLE_COMMENT_USER), commentsSet.getInt(DBManager.TITLE_COMMENT_RATING), commentsSet.getString(DBManager.TITLE_COMMENT_COMMENT)));
				}
				while (datesSet.next()) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(datesSet.getDate(DBManager.TITLE_PLAN_DAY));
					meal.addDate(cal);
				}
				while (labelsSet.next()) {
					meal.addLabel(Handler.getInstance().getMealManager().getLabel(labelsSet.getString(DBManager.TITLE_LABELING_LABEL)));
				}
				
				return meal;
			}
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
		return null;
	}
}
