package lecker.model.data;

public class User {
	private String name;
	private Boolean commentRight;
	private Boolean tagRight;
	private Boolean changeRight;
	
	
	
	public User(String name, boolean commentRight, boolean tagRight, boolean changeRight) {
		
	}
	
	
	
	public String getName() {
		synchronized(this.name) {
			return this.name;
		}
	}
	
	public boolean hasCommentRight() {
		synchronized(this.commentRight) {
			return this.commentRight;
		}
	}
	
	public boolean hasTagRight() {
		synchronized(this.tagRight) {
			return this.tagRight;
		}
	}
	
	public boolean hasChangeRight() {
		synchronized(this.changeRight) {
			return this.changeRight;
		}
	}
	
	
	
	public void setCommentRight(Boolean commentRight) {
		synchronized(this.commentRight) {
			this.commentRight = commentRight;
		}
	}
	
	public void setTagRight(Boolean tagRight) {
		synchronized(this.tagRight) {
			this.tagRight = tagRight;
		}
	}
	
	public void setChangeRight(Boolean changeRight) {
		synchronized(this.changeRight) {
			this.changeRight = changeRight;
		}
	}
}
