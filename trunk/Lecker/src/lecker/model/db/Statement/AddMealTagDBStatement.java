package lecker.model.db.Statement;



import lecker.model.db.DBStatement;



public class AddMealTagDBStatement implements DBStatement {
	private String mealName, tag;
	
	
	
	public AddMealTagDBStatement(String mealName, String tag) {
		this.mealName = mealName;
		this.tag = tag;
	}

	
	
	@Override
	public void postStatement() {
		// TODO
	}

	@Override
	public String[] getPrepareStatements() {
		// TODO
		return null;
	}

}
