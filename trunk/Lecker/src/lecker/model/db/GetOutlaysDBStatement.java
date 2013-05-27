package lecker.model.db;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lecker.model.data.Outlay;
import lecker.presenter.Handler;



public class GetOutlaysDBStatement implements DBStatement<Outlay[]> {
	private PreparedStatement statement;
	
	
	
	public GetOutlaysDBStatement() {
		try {
			statement = Handler.getInstance().getDBManager().prepareStatement("SELECT " + DBManager.TITLE_OUTLAY_NAME + " FROM " + DBManager.TITLE_OUTLAY + ";");
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
	}
	
	
	
	@Override
	public Outlay[] postQuery() {
		try {
			ArrayList<Outlay> outlays = new ArrayList<Outlay>();
			ResultSet set = statement.executeQuery();
		
			while (set.next()) {
				outlays.add(new Outlay(set.getString(DBManager.TITLE_MEAL_NAME)));
			}
			
			return outlays.toArray(new Outlay[0]);
		} catch (SQLException e) {
			Handler.getInstance().getExceptionHandler().handle(e);
		}
		return null;
	}
}
