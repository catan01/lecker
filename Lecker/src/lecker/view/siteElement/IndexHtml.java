package lecker.view.siteElement;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Arrays;
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
		StringBuilder builder = new StringBuilder();
		
		builder.append(
		"<div id='date'>"+
			"<span id='datetitle'>Angebot vom " + DATE.get(Calendar.DAY_OF_MONTH) + "." + (DATE.get(Calendar.MONTH) + 1) + "." + DATE.get(Calendar.YEAR) + "</span><br>" +
				"<img id='date_left' class='pointer' src='images/arrow_left.png'>" +
				"&nbsp;&nbsp;&nbsp;" +
				"<img id='datepickerImage' class='pointer' src='images/calendar.png'>" +
				"&nbsp;&nbsp;&nbsp;" +
				"<img id='date_right' class='pointer' src='images/arrow_right.png'>" +
				"<div id='datepicker'></div>" +
		"</div>" +
	"</div>");
		
		String[] outlayNames = new String[] {"Hauptgericht", "Beilage"}; // TODO

		for (Outlay outlay: Handler.getInstance().getMealManager().getOutlays()) {
			builder.append("<div class='mealcontainer'>");
			builder.append("<div class='ausgabe'>" + outlay.getName() + "</div>");
			for (String outlayName: outlayNames) {
				String[] names = Handler.getInstance().getMealManager().getPlan(outlay, DATE).getMeals(Handler.getInstance().getMealManager().getKategorie(outlayName));
				Arrays.sort(names);
				for (String mealName: names) {
					Meal meal = Handler.getInstance().getMealManager().getMeal(mealName);
					try {
						mealName = URLEncoder.encode(mealName, "UTF8");
					} catch (UnsupportedEncodingException e) {
						//do nothing
					}
					builder.append(
							"<div class='meal pointer' onclick=\"window.location.href='Page?Meal=" + mealName + "'\">" +
								"<div class='mealheader'>" +
									"<div class='mealpicture'>" +
										"<img src='images/meals/Salzkartoffeln/1_small.jpg'>" +
									"</div>" +
									"<div class='mealtitle'>" +
										"<b>" + meal.getName() + "</b> " + loadLabel(meal) + 
										"<br>" +
										(meal.getPrice() / 100) + "." + (meal.getPrice() % 100) + " &#8364" +
									"</div>" +
								"</div>" +
								"<div class='mealrating'>" +
									loadRating(meal) +
								"</div>" +
								"<div class='mealcomments'>" +
									"" + meal.getComments().get().length +
								"</div>" +
							"</div>" );
				}
				if (!outlayName.equals(outlayNames[outlayNames.length - 1])) {
					builder.append("<hr/>");
				}
			}
			builder.append("</div>");
		}


		return builder.toString();
	}



	private String loadRating(Meal meal) {
		StringBuilder builder = new StringBuilder();

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
					builder.append("<img src='images/star_green_small.png'>");
				} else {
					builder.append("<img src='images/star_gray_small.png'>");
				}
			}
			builder.append("<b>Ø " + new DecimalFormat("0.00").format(1.0 * rate / count) + "</b>");
		} else {
			builder.append("Keine Bewertungen vorhanden");
		}

		return builder.toString();
	}

	private String loadLabel(Meal meal) {
		StringBuilder builder = new StringBuilder();

		for (Label label: meal.getLabels()) {
			builder.append("<img title='" + label.getName() + "' src='images/mealinfo/" + label.getName() + "_small.png'>  ");
		}

		return builder.toString();
	}
}
