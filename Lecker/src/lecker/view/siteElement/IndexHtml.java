package lecker.view.siteElement;



import java.text.DecimalFormat;
import java.util.Calendar;

import lecker.model.data.Label;
import lecker.model.data.Meal;
import lecker.model.data.Outlay;
import lecker.model.data.comments.Comment;
import lecker.presenter.Handler;
import lecker.view.MainSiteElement;



/**
 * The normal landing page.
 * 
 * @author LWagner
 *
 */
public class IndexHtml implements MainSiteElement {
	private final Calendar DATE;
	
	
	
	public IndexHtml() {
		DATE = Calendar.getInstance();
	}
	
	public IndexHtml(String date) {
		this();
		DATE.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
		DATE.set(Calendar.MONTH, Integer.parseInt(date.split("-")[1]));
		DATE.set(Calendar.DATE, Integer.parseInt(date.split("-")[2]));
	}
	
	
	@Override
	public String getCode(String remoteAddr, boolean isMobile) {
		if (!isMobile) {
			return showRaster();
		}
		//TODO
		return "";
	}

	@Override
	public String getSkript(String remoteAddr, boolean isMobile) {
		return "";
	}

	
	
	@Override
	public String getTitle() {
		return "Startseite";
	}
	
	
	
	private String showRaster() {
		StringBuffer buffer = new StringBuffer();
		String[] outlayNames = new String[] {"Hauptgericht", "Beilage"}; // TODO
		
		for (Outlay outlay: Handler.getInstance().getMealManager().getOutlays()) {
			buffer.append("<div class='ausgabe'>" + outlay.getName() + "</div>");
			for (String outlayName: outlayNames) {
				for (String mealName: Handler.getInstance().getMealManager().getPlan(outlay, DATE).getMeals(Handler.getInstance().getMealManager().getKategorie(outlayName))) {
					Meal meal = Handler.getInstance().getMealManager().getMeal(mealName);					
					buffer.append(
							"<div class='meal pointer'>" +
								"<div class='mealpicture'>" +
									"<img src='images/kartoffeln_small.jpg'>" +
								"</div>" +
								"<div class='mealtitle'>" +
									"<b>" + meal.getName() + "</b> " + loadLabel(meal) + 
									"<br>" +
									(meal.getPrice() / 100) + "." + (meal.getPrice() % 100) + " &#8364" +
								"</div>" +
								"<div class='mealrating'>" +
									loadRating(meal) +
								"</div>" +
								"<div class='mealcomments'>" +
									"" + meal.getComments().get().length +
								"</div>" +
							"</div>");
				}
			}
		}
		
		return buffer.toString();
	}
	
	
	
	private String loadRating(Meal meal) {
		StringBuffer buffer = new StringBuffer();
		
		if (meal.getComments().get().length > 0) {
			int count = 0, rate = 0;
			for (Comment com: meal.getComments().get()) {
				if (com.getRating() <= Comment.MAX_RATING && com.getRating() >= 0) {
					++count;
					rate += (int) com.getRating();
				}
			}
			for (int i = 0; i < Comment.MAX_RATING; ++i) {
				if (i < (1.0 * rate / count)) {
					buffer.append("<img src='images/star_green_small.png'>");
				} else {
					buffer.append("<img src='images/star_gray_small.png'>");
				}
			}
			buffer.append("<b>Ø " + new DecimalFormat("0.00").format(1.0 * rate / count) + "</b>");
		} else {
			buffer.append("Keine Bewertungen vorhanden");
		}
		
		return buffer.toString();
	}
	
	private String loadLabel(Meal meal) {
		StringBuffer buffer = new StringBuffer();
		
		for (Label label: meal.getLabels()) {
			buffer.append("<img title='" + label.getName() + "' src='images/mealinfo/" + label.getName() + "_small.png'>  ");
		}
		
		return buffer.toString();
	}
}
