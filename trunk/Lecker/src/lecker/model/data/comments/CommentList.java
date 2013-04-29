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
	
	
	
	public CommentList() {
		this.first = null;
		this.current = null;
	}
	
	
	
	public Comment get(int id) {
		synchronized (this.first) {
			synchronized (this.current) {
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
	}
	
	public Comment next() {
		synchronized (this.current) {
			return (this.current = this.current.getNext()).get();
		}
	}
	
	
	
	public void addComment(Comment comment) {
		synchronized (this.first) {
			CommentRep rep = new CommentRep(comment);
			rep.setNext(this.first);
			this.first = rep;
		}
	}
	
	// To copy
	private void setFirst(CommentRep rep) {
		synchronized (this.first) {
			this.first = rep;
		}
	}
	
	
	
	public CommentList copy() {
		CommentList list = new CommentList();
		list.setFirst(this.first);
		return list;
	}
}





class CommentRep {
	private Comment element;
	private CommentRep next;
	
	
	
	public CommentRep(Comment element) {
		this.element = element;
		this.next = null;
	}
	
	
	
	public Comment get()  {
		return this.element;
	}
	
	public CommentRep getNext() {
		synchronized (next) {
			return this.next;
		}
	}
	
	
	
	public void setNext(CommentRep next) {
		synchronized (next) {
			this.next = next;
		}
	}
}
