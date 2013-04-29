package lecker.model.data.comments;

import java.awt.Image;



/**
 * One single Comment
 * 
 * @author LWagner
 *
 */
public class Comment {
	private int rating;
	private String comment;
	private Image image;
	
	
	
	public Comment(int rating, String comment) {
		this.rating = rating;
		this.comment = comment;
		this.image = null;
	}
	
	public Comment(int rating, String comment, Image image) {
		this(rating, comment);
		setImage(image);
	}
	
	
	
	public int getRating() {
		return rating;
	}
	
	public String getComment() {
		return comment;
	}
	
	public Image getImage() {
		return image;
	}
	
	
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
}
