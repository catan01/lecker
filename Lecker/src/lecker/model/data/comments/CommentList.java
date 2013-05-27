package lecker.model.data.comments;




/**
 * Class to split Commentable from comments
 * 
 * @author LWagner
 *
 */
public class CommentList {
	private CommentRep first;
	private CommentRep current;
	
	private final Object LOCK = new Object();
	
	
	
	public CommentList() {
		this.first = null;
		this.current = null;
	}
	
	
	
	public Comment get(int id) {
		synchronized (this.LOCK) {
			try {
				this.current = this.first;
				for (int i = 0; i < id; ++i) {
					this.current = this.current.getNext();
				}
				return this.current.get();
			} catch (NullPointerException exc) {
				this.current = null;
				return null;
			}
		}
	}
	
	public Comment next() {
		synchronized (this.LOCK) {
			return (this.current = this.current.getNext()).get();
		}
	}
	
	
	
	public void addComment(Comment comment) {
		synchronized (this.LOCK) {
			CommentRep rep = new CommentRep(comment);
			rep.setNext(this.first);
			this.first = rep;
		}
	}
	
	// To copy
	private void setFirst(CommentRep rep) {
		synchronized (this.LOCK) {
			this.first = rep;
		}
	}
	
	
	
	public CommentList copy() {
		synchronized(this.LOCK) {
			CommentList list = new CommentList();
			list.setFirst(this.first);
			return list;
		}
	}
}





class CommentRep {
	private Comment element;
	private CommentRep next;

	private final Object LOCK = new Object();
	
	
	
	public CommentRep(Comment element) {
		this.element = element;
		this.next = null;
	}
	
	
	
	public Comment get()  {
		synchronized (this.LOCK) {
			return this.element;
		}
	}
	
	public CommentRep getNext() {
		synchronized (this.LOCK) {
			return this.next;
		}
	}
	
	
	
	public void setNext(CommentRep next) {
		synchronized (this.LOCK) {
			this.next = next;
		}
	}
}
