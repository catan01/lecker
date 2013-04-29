package lecker.model.db.Statement;



import lecker.model.data.comments.Comment;
import lecker.model.db.DBStatement;



public class AddMealCommentDBStatement implements DBStatement {
	private String mealName;
	private Comment comment;

	public AddMealCommentDBStatement(String mealName, Comment comment) {
		this.mealName = mealName;
		this.comment = comment;
	}
	
	
	
	@Override
	public void postStatement() {
		//TODO
	}

	@Override
	public String[] getPrepareStatements() {
		//TODO
		return null;
	}
}
