package lecker.model.data.comments;

import java.util.ArrayList;




/**
 * Class to split Commentable from comments
 * 
 * @author LWagner
 *
 */
public class CommentList {
	private ArrayList<Comment> comments;
	
	private final Object LOCK = new Object();
	
	
	
	public CommentList() {
		this.comments = new ArrayList<Comment>();
	}
	
	
	
	public Comment[] get() {
		return this.comments.toArray(new Comment[0]);
	}
	
	public Comment get(int id) {
		synchronized (this.LOCK) {
			return this.comments.get(id);
		}
	}
	
	
	
	public void addComment(Comment comment) {
		synchronized (this.LOCK) {
			this.comments.add(0, comment);
		}
	}
	
	
	
	public CommentList copy() {
		synchronized(this.LOCK) {
			CommentList list = new CommentList();
			
			for (int i = this.comments.size() - 1; i >= 0; --i) {
				list.addComment(this.get(i));
			}
			
			return list;
		}
	}
}