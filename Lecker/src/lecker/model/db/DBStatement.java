package lecker.model.db;



/**
 * Used to post statements with OOP;
 * 
 * @author LWagner
 *
 */
public interface DBStatement<T> {
	public T postQuery();
}
