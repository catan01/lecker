package lecker.view.siteElement;



import java.awt.Image;
import java.text.DecimalFormat;
import java.util.ArrayList;

import lecker.model.data.Label;
import lecker.model.data.Meal;
import lecker.model.data.comments.Comment;
import lecker.presenter.Handler;
import lecker.presenter.servlet.CommentServlet;
import lecker.presenter.servlet.FavoriteServlet;
import lecker.view.MainSiteElement;



public class MealHtml implements MainSiteElement {
	private final Meal MEAL;
	private final Comment[] comments;
	
	
	
	public MealHtml(String mealName) {
		this.MEAL = Handler.getInstance().getMealManager().getMeal(mealName);
		this.comments = MEAL.getComments().get();
	}
	
	
	
	@Override
	public String getCode(String remoteAddr, boolean isMobile) {
		
		//Variables
		StringBuilder builder = new StringBuilder();
		ArrayList<Image> images = new ArrayList<Image>();
		int[] ratings = new int[Comment.MAX_RATING + 1];
		for (Comment com: comments) {
			if (com.getImage() != null) {
				images.add(com.getImage());
			}
			try {
				ratings[com.getRating()]++;
			} catch (Exception exc) {
				ratings[0]++;
			}
		}
		if (!isMobile) {
		builder.append("<br/><br/>");
		
		builder.append("<div class='meal_left'>" +
			this.showImage(images.toArray(new Image[0])) + "<br/>" +
			this.showDescription(remoteAddr) + "<br/>" +
			this.showRating(ratings) + "<br/>" +
			"</div><div class='meal_right'>" +
			"<div class='comment' id='insertAnker'>" +
				"<form id='comment' action='.' type='POST'>" +
					"<textarea rows='4' cols='100'  class='comment_comment' id='" + CommentServlet.PARAM_COMMENT + "'></textarea></br>" +
					"<input type='radio' name='" + CommentServlet.PARAM_RATING + "' value='1'>" +
					"<input type='radio' name='" + CommentServlet.PARAM_RATING + "' value='2'>" +
					"<input type='radio' name='" + CommentServlet.PARAM_RATING + "' value='3'>" +
					"<input type='radio' name='" + CommentServlet.PARAM_RATING + "' value='4'>" +
					"<input type='radio' name='" + CommentServlet.PARAM_RATING + "' value='5'>" +
					"<input type='submit' id='onLogin' value='Senden'/>" +
					"<div id='comment_response'></div>" +
				"</form>" +
			"</div>");
		for (Comment comment: comments) {
			builder.append(showComment(comment, isMobile));
		}
		builder.append("<div/>");
		
		} else if (isMobile) {
			return showMobileMeal(remoteAddr);
		}
		
		return builder.toString();
	}

	@Override
	public String getSkript(String remoteAddr, boolean isMobile) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("$(function(){" +
					"$('#comment').submit(function() {" +
						"var formComment = $('#" + CommentServlet.PARAM_COMMENT + "').val();" +
						"var formRating = 0;" +
						"var ratingsCb = document.getElementsByName('" + CommentServlet.PARAM_RATING + "');" +
						"for ( var i = 0; i < ratingsCb.length; i++) {" +
							"if(ratingsCb[i].checked) {" +
								"formRating = i + 1;" +
							"}" +
						"}" +
						"$.ajax({" +
							"type:'POST'," +
						    "cache:false," +
							"url:'Comment'," +
							"data:{" + CommentServlet.PARAM_MEAL + ":'" + MEAL.getName() + "', " + CommentServlet.PARAM_COMMENT + ":formComment, " + CommentServlet.PARAM_RATING + ":formRating}," +
							"success:function(response){" +
								"if (response == '') {" +
									"document.getElementById('comment_response').innerHTML = 'Kommentar gespeichert';" +
								"} else {" +
									"document.getElementById('comment_response').innerHTML = response;" +
								"}" +
							"}" +
						"});" +
						"return false;" +
					"});" +
				"});");
	
	  	// add a Favourite
		builder.append(
	  			"function addFavorite() {" +
	  				"$.ajax({" +
						"type:'POST'," +
					    "cache:false," +
						"url:'Favorite'," +
						"data:{" + FavoriteServlet.PARAM_MEAL + ":'" + MEAL.getName() + "'}," +
						"success:function(response){" +
							"if(response != '') {" +
								"alert(response);" +
							"} else {" +
								"alert('Die Speise wurde ihren Favoriten hinzugefügt.');" +
							"}" +
						"}" +
					"});" +
	  			"}");
		
		return builder.toString();
	}



	@Override
	public String getTitle() {
		return MEAL.getName();
	}
	
	
	
	
	
	private String showImage(Image[] images) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<div class='meal_image'><img src='images/meals/" + (MEAL.hasPicture() ? MEAL.getName() + "/1.jpg'>" : "template.png'>") + "</div>");

		return builder.toString();
	}
	
	private String showDescription(String remoteAddr) {
		StringBuilder builder = new StringBuilder();
		Integer priceDec = MEAL.getPrice() % 100;
		String price = "";
		
		if ((MEAL.getPrice() / 100) > 0 || priceDec > 0) {
			price = (MEAL.getPrice() / 100) + "." + ((priceDec < 10) ? "0" + priceDec : priceDec) + " &#8364";
		} else {
			price = "<span title='Kein Preis bekannt'>? &#8364</span>";
		}
		
		builder.append(
				"<div class='meal_name'>" +
					MEAL.getName() +
				"</div>" +
				"<div class='meal_labels'>");
		for (Label label: MEAL.getLabels()) {
			builder.append("<img title='" + label.getName() + "' src='images/mealinfo/" + label.getName() + "_small.png'>  ");
		}
		builder.append(
				"</div>" +
				"<div class='meal_price'>" + 
					price +
				"</div>" +
				"<div id='meal_favorite'>" +
					"<button type='button' id='favorite' onclick='addFavorite();'>Favorit hinzufügen</button>" +
				"</div>");

		return builder.toString();
	}
	
	private String showRating(int[] ratings) {
		StringBuilder builder = new StringBuilder();
		
		builder.append("<div class='meal_rating'>");
		int count = 0, rate = 0;
		for (int i = ratings.length - 1; i >= 0; --i) {
			count += ratings[i];
			rate += ratings[i] * i;
			for (int j = 0; j < ratings.length - 1; ++j) {
				if (j < i) {
					builder.append("<img src='images/star_green_small.png'>");
				} else {
					builder.append("<img src='images/star_gray_small.png'>");
				}
			}
			builder.append(" " + ratings[i] + "<br />");
		}
		if(count > 0) {
			builder.append("<div class='meal_rating_solution'> Ø " + new DecimalFormat("0.00").format(1.0 * rate / count) + "</div>");
		}
		builder.append("</div>");
		
		return builder.toString();
	}
	
	private String showComment(Comment comment, boolean isMobile) {
		StringBuilder builder = new StringBuilder();
		if(!isMobile){
		builder.append(
				"<div class='meal_comment'>" +
					"<div class='meal_comment_image'>" +
					"</div>" +
					"<div class='meal_comment_content'>" +
						"<div class='meal_comment_content_header'>" +
							"<div class='meal_comment_content_header_user'>" +
								comment.getUserName() +
							"</div>" +
						"</div>" +
						"<div class='meal_comment_content_body'>" +
							"<div class='meal_comment_content_body_message'>" +
								comment.getComment() +
							"</div>" +
							"<div class='meal_comment_content_body_rating'>");
		for (int i = 1; i <= 5; ++i) {
			if (i <= comment.getRating()) {
				builder.append("<img src='images/star_green_small.png'>");
			} else {
				builder.append("<img src='images/star_gray_small.png'>");
			}
		}
		builder.append(
							"</div>" +
						"</div>" +
					"</div>" +
				"</div>");
		} else if(isMobile){
			builder.append(
					"<div class='box'>" +
						"<div class='meal_comment_content'>" +
							"<div class='meal_comment_content_header'>" +
								"<div class='meal_comment_content_header_user'><b>" +
									comment.getUserName() +
								"</b></div>" +
							"</div>" +
							"<div class='meal_comment_content_body'>" +
								"<div class='meal_comment_content_body_message'>" +
									comment.getComment() +
								"</div>" +
								"<div class='meal_comment_content_body_rating'>");
			for (int i = 1; i <= 5; ++i) {
				if (i <= comment.getRating()) {
					builder.append("<img src='images/star_green_medium.png'>");
				} else {
					builder.append("<img src='images/star_gray_medium.png'>");
				}
			}
			builder.append(
								"</div>" +
							"</div>" +
						"</div>" +
					"</div>");
		}
		return builder.toString();
	}
	
	String showMobileMeal(String remoteAddr) {	
		//Variables
		StringBuilder builder = new StringBuilder();
		ArrayList<Image> images = new ArrayList<Image>();
		Integer priceDec = MEAL.getPrice() % 100;
		int[] ratings = new int[Comment.MAX_RATING + 1];
		for (Comment com: comments) {
			if (com.getImage() != null) {
				images.add(com.getImage());
			}
			try {
				ratings[com.getRating()]++;
			} catch (Exception exc) {
				ratings[0]++;
			}
		}
		//header
		builder.append(				
			"<div class='header'>" +
				"<b>" + IndexHtml.shortenMealName(MEAL.getName()) + "</b> " + IndexHtml.loadLabel(MEAL) + 
			"</div>" +	
			"<div class='meal'>" +
				this.showImage(images.toArray(new Image[0])) + "<br/>" +
			"</div>" + 
	//price and favorite
	"<div class='meal'>" +
		"<b> Preis: ");
		if ((MEAL.getPrice() / 100) > 0 || priceDec > 0) {
			builder.append((MEAL.getPrice() / 100) + "." + ((priceDec < 10) ? "0" + priceDec : priceDec) + " &#8364");
		} else {
			builder.append("<span title='Kein Preis bekannt'>? &#8364</span>");
		}
		builder.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
		//Bewertung
		IndexHtml.loadRating(MEAL, true) +
		"</b>" +
		"<div id='meal_favorite'>" +
			"<button type='button' id='favorite' onclick='addFavorite();'>Favorit hinzufügen</button>" +
		"</div>" +
	"</div>" +
	//Kommentare	
	"<div class='header'>" +
		"<b> Kommentare </b>" +
	"</div>" +
	//schreiben
"<div class='box'>" +
	"<form id='comment' action='.' type='POST'>" +
	"<textarea  class='comment_comment' id='" + CommentServlet.PARAM_COMMENT + "'></textarea></br>" +
		"<input type='radio' name='" + CommentServlet.PARAM_RATING + "' value='1'>" +
		"<input type='radio' name='" + CommentServlet.PARAM_RATING + "' value='2'>" +
		"<input type='radio' name='" + CommentServlet.PARAM_RATING + "' value='3'>" +
		"<input type='radio' name='" + CommentServlet.PARAM_RATING + "' value='4'>" +
		"<input type='radio' name='" + CommentServlet.PARAM_RATING + "' value='5'>" +
		"<input type='submit' id='onLogin' value='Senden'/>" +
	"<div id='comment_response'></div>" +
"</form></div>");
		//anzeigen
for (Comment comment: comments) {
	builder.append(showComment(comment, true));
}
builder.append("</div>" +
		"<br/><br/><br/>");
				
				
				
				
				
				
				
				
		

		return builder.toString();
	}
	
	
	
}
