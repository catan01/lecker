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
	private int current;
	
	private final Object LOCK = new Object();
	
	
	
	public CommentList() {
		this.comments = new ArrayList<Comment>();
		this.current = -1;
	}
	
	
	
	public Comment get(int id) {
		synchronized (this.LOCK) {
			if (id <= comments.size()) {
				current = id;
			}
			return comments.get(id);
		}
	}
	
	public Comment getFirst() {
		return get(0);
	}
	
	public Comment get() {
		synchronized (this.LOCK) {
			return comments.get(current);
		}
	}
	
	
	
	public Boolean next() {
		synchronized (this.LOCK) {
			if (this.current < this.comments.size()) {
				++this.current;
				return true;
			}
			return false;
		}
	}
	
	public void setBeforeFirst() {
		synchronized (LOCK) {
			current = -1;
		}
	}
	
	
	
	public void addComment(Comment comment) {
		synchronized (this.LOCK) {
			comments.add(0, comment);
			next();
		}
	}
	
	
	
	public CommentList copy() {
		synchronized(this.LOCK) {
			int safeCurrent = current;
			
			current = -1;
			
			CommentList list = new CommentList();
			int count = -1;
			
			while (next()) {
				++count;
			}
			while(count > 0) {
				list.addComment(get((count--) - 1));
			}
			
			current = safeCurrent;
			
			return list;
		}
	}
}