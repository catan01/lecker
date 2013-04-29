package lecker.model.db.Statement;



import lecker.model.db.DBStatement;



public class AddMealDateDBStatement implements DBStatement {
	private long timestamp;
	private String name;
	
	
	
	public AddMealDateDBStatement(long timestamp, String name) {
		this.timestamp = timestamp;
		this.name = name;
	}
	
	
	
	@Override
	public void postStatement() {
		// TODO
	}

	/*
	 * not often used, so there is no need to prepare it.
	 */
	@Override
	public String[] getPrepareStatements() {
		return null;
	}
}
