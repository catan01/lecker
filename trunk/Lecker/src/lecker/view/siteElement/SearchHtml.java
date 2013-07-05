package lecker.view.siteElement;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Pattern;

import lecker.model.data.Meal;
import lecker.presenter.Handler;
import lecker.view.MainSiteElement;

public class SearchHtml implements MainSiteElement {

	private String search;
	
	public SearchHtml(String search) {
		this.search = search;
	}
	
	@Override
	public String getCode(String remoteAddr, boolean isMobile) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(
				"<div id='searchHeader'>" +
						"<span id='searchTitle'>Suchergebnisse für: <i>" + search + "</i></span>" +
						"<hr/>" +
				"</div>" +
			"</div>");
		
		String[] mealNames = Handler.getInstance().getMealManager().getMealNames();
		ArrayList<String> foundMeals = new ArrayList<String>();
		
		for(String mealName : mealNames) {
			if(Pattern.compile(Pattern.quote(search), Pattern.CASE_INSENSITIVE).matcher(mealName).find()) {
				foundMeals.add(mealName);
			}
		}
		
		if(foundMeals.size() > 0) {
			for(String mealName : foundMeals) {
				Meal meal = Handler.getInstance().getMealManager().getMeal(mealName);
				try {
					mealName = URLEncoder.encode(mealName, "UTF8");
				} catch (UnsupportedEncodingException e) {
					//do nothing
				}
				builder.append(
						"<div class='meal pointer searchResult' onclick=\"window.location.href='?Meal=" + mealName + "'\"" +
										(meal.getName().length() > IndexHtml.MAX_NAME_LENGTH ? " title='" + meal.getName() + "'>" : ">") +
							"<div class='mealpicture'>" +
								"<img src='images/meals/" + (meal.hasPicture() ? meal.getName() + "/1_small.jpg'>" : "template_small.png'>") +
							"</div>" +
							"<div class='mealtitle'>" +
								"<b>" + IndexHtml.shortenMealName(meal.getName()) + "</b> " + IndexHtml.loadLabel(meal) + 
								"<br>" +
								(meal.getPrice() / 100) + "." + (meal.getPrice() % 100) + " &#8364" +
							"</div>" +
							"<div class='mealrating'>" +
								IndexHtml.loadRating(meal) +
							"</div>" +
							"<div class='mealcomments'>" +
								"" + meal.getComments().get().length +
							"</div>" +
						"</div>" );
			}
		} else {
			builder.append("<span id='searchTitle'>Keine Gerichte gefunden</span>");
		}
		
		return builder.toString();
	}

	@Override
	public String getSkript(String remoteAddr, boolean isMobile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

}
