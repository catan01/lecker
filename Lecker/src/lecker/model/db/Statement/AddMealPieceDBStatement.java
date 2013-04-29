package lecker.model.db.Statement;



import lecker.model.data.MealPiece;
import lecker.model.db.DBStatement;



/**
 * DBStatement to add a mealPiece.
 * 
 * @author LWagner
 *
 */
public class AddMealPieceDBStatement implements DBStatement {
	private MealPiece mealPiece;

	
	
	public AddMealPieceDBStatement(MealPiece mealPiece) {
		this.mealPiece = mealPiece;
	}
	
	
	
	@Override
	public void postStatement() {
		//TODO
	}

	/*
	 * not often used, so there is no need to prepare it.
	 */
	@Override
	public String[] getPrepareStatements() {
		return null;
	}
}
