package lecker.model.data.comments;




/**
 * An abstract to let other classes be commentable.
 * 
 * @author LWagner
 *
 */
public abstract class Commentable {
	private CommentList comments;
	
	
	
	public Commentable() {
		this.comments = null;
	}
	
	
	
	/**
	 * Returns a copy of the list with comments
	 * 
	 * @return a copied CommentList
	 * 
	 * !WARNING!
	 * returns only a copy
	 * !WARNING!
	 */
	public CommentList getComments() {
		synchronized (this.comments) {
			return this.comments.copy();
		}
	}
	
	
	
	public void addComment(Comment comment) {
		synchronized (this.comments) {
			this.comments.addComment(comment);
		}
	}
}
