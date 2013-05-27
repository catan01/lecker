package lecker.model.db;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import lecker.presenter.Handler;




/**
 * This class connects to the DB
 * 
 * @author LWagner
 *
 */
public class DBManager {
	final static String TITLE_COMMENT = "Kommentar";
	final static String TITLE_COMMENT_ID = "Bewertung_ID";
	final static String TITLE_COMMENT_RATING = "Note";
	final static String TITLE_COMMENT_COMMENT = "Kommentar";
	final static String TITLE_COMMENT_MEAL = "Speise_Name";
	final static String TITLE_COMMENT_USER = "Benutzer";

	final static String TITLE_FAVOURITE = "Favorit";
	final static String TITLE_FAVOURITE_MEAL= "Speise_Name";
	final static String TITLE_FAVOURITE_USER= "Benutzer";

	final static String TITLE_KATEGORIE = "Kategorie";
	final static String TITLE_KATEGORIE_NAME= "Name";

	final static String TITLE_LABEL = "Inhaltsstoff";
	final static String TITLE_LABEL_NAME= "Name";

	final static String TITLE_LABELING = "Kennzeichnung";
	final static String TITLE_LABELING_MEAL= "Speise_Name";
	final static String TITLE_LABELING_LABEL= "Inhaltsstoff";

	final static String TITLE_MEAL = "Speise";
	final static String TITLE_MEAL_NAME = "Name";
	final static String TITLE_MEAL_PRICE = "Preis";
	final static String TITLE_MEAL_KATEGORIE = "Kategorie";

	final static String TITLE_OUTLAY = "Ausgabe";
	final static String TITLE_OUTLAY_NAME = "Name";

	final static String TITLE_PLAN = "Speiseplan";
	final static String TITLE_PLAN_MEAL= "Speise_Name";
	final static String TITLE_PLAN_OUTLAY = "Ausgabe";
	final static String TITLE_PLAN_DAY = "Tag";

	final static String TITLE_USER = "Benutzer";
	final static String TITLE_USER_NAME = "Name";
	final static String TITLE_USER_PW = "Passwort";
	
	private static String url = "jdbc:mysql://localhost:1300/it13g13?autoReconnect=true";
	private static String username = "it13g13";
	private static String password = "bgfqnc4";
	private static Connection connection = null;
	
	
	
	public static void init() {
		synchronized (connection) {
			if (connection == null) {
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					connection = DriverManager.getConnection(url, username, password);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
					Handler.getInstance().getExceptionHandler().handle(e);
				}
			}
		}
	}
	
	public static void destruct() {
		synchronized (connection) {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					Handler.getInstance().getExceptionHandler().handle(e);
				} finally {
					connection = null;
				}
			}
		}
	}
	
	PreparedStatement prepareStatement(String query) throws SQLException {
		synchronized (connection) {
			if (connection != null) {
				return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			}
			return null;
		}
	}
}
