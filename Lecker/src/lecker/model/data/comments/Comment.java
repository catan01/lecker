package lecker.model.data.comments;

import java.awt.Image;



/**
 * One single Comment
 * 
 * @author LWagner
 *
 */
public class Comment {
	public final static int MAX_RATING = 5;
	
	private String userName;
	private int rating;
	private String comment;
	private Image image;
	
	private final Object USERNAMELOCK = new Object();
	private final Object RATINGLOCK = new Object();
	private final Object COMMENTLOCK = new Object();
	private final Object IMAGELOCK = new Object();
	
	
	
	public Comment(String userName, int rating, String comment) {
		this.userName = userName;
		this.rating = rating;
		this.comment = comment;
		this.image = null;
	}
	
	public Comment(String userName, int rating, String comment, Image image) {
		this(userName, rating, comment);
		setImage(image);
	}
	
	
	
	public String getUserName() {
		synchronized (this.USERNAMELOCK) {
			return this.userName;
		}
	}
	
	public int getRating() {
		synchronized (this.RATINGLOCK) {
			return rating;
		}
	}
	
	public String getComment() {
		synchronized (this.COMMENTLOCK) {
			return comment;
		}
	}
	
	public Image getImage() {
		synchronized (this.IMAGELOCK) {
			return image;
		}
	}
	
	
	
	public void setRating(int rating) {
		synchronized (this.RATINGLOCK) {
			this.rating = rating;
		}
	}
	
	public void setComment(String comment) {
		synchronized (this.COMMENTLOCK) {
			this.comment = comment;
		}
	}
	
	public void setImage(Image image) {
		synchronized (this.IMAGELOCK) {
			this.image = image;
		}
	}
}
