package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lecker.model.data.Label;
import lecker.presenter.Handler;



public class GetLabelsDBStatement implements DBStatement<Label[]> {
	private PreparedStatement statement;
	
	
	
	public GetLabelsDBStatement() {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("SELECT " + DBManager.TITLE_LABEL_NAME + " FROM " + DBManager.TITLE_LABEL + ";");
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
	}
	
	
	
	@Override
	public Label[] postQuery() {
		try {
			ResultSet set = statement.executeQuery();
			ArrayList<Label> labels = new ArrayList<Label>();
			
			while (set.next()) {
				labels.add(new Label(set.getString(DBManager.TITLE_KATEGORIE_NAME)));
			}
			
			return labels.toArray(new Label[0]);
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
		return null;
	}
}
