package lecker.view.siteElement;



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import lecker.model.data.Label;
import lecker.model.data.Meal;
import lecker.model.data.Outlay;
import lecker.model.data.comments.Comment;
import lecker.presenter.AbstractServlet;
import lecker.presenter.Handler;
import lecker.view.MainSiteElement;



/**
 * The normal landing page.
 * 
 * @author LWagner
 *
 */
public class IndexHtml implements MainSiteElement {
	
	public static final int MAX_NAME_LENGTH = 40;
	
	private final GregorianCalendar DATE;
	private final String CHOSEN_OUTLAY;



public IndexHtml(String chosenOutlay) {
		DATE = new GregorianCalendar();
		if( chosenOutlay.contains("Ofener")) {
			CHOSEN_OUTLAY = "Ofener Straße";
		} else {
		CHOSEN_OUTLAY = chosenOutlay;
		}
		
	}
	


	public IndexHtml(String date, String chosenOutlay) {
		this(chosenOutlay);
		DATE.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
		DATE.set(Calendar.MONTH, Integer.parseInt(date.split("-")[1]) - 1);
		DATE.set(Calendar.DATE, Integer.parseInt(date.split("-")[2]));
	}


	@Override
	public String getCode(String remoteAddr, boolean isMobile) {
		if (!isMobile) {
			return showRaster();
		}else if(isMobile) {
			if(CHOSEN_OUTLAY.equals("")) {
				return showChooseOutlay();
			} else {
				return showMobile(CHOSEN_OUTLAY); 
			}
		}
		return "";
	}

	@Override
	public String getSkript(String remoteAddr, boolean isMobile) {
		if(!isMobile) {
			StringBuilder builder = new StringBuilder();
			
			//NextDay
			builder.append(
					"function changeDay(days) {" +
						"var $picker = $('#datepicker');" +
						"var date=new Date($picker.datepicker('getDate'));" +
						"date.setDate(date.getDate()+ days);" +
						"window.location.href='?Day=' + date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();" +
						
					"};");
			
			builder.append(
					"$(function(){" +
						"$('#datepicker').datepicker({" +
							"dateFormat: 'yy-mm-dd'," +
							"onSelect : function(dateText) {" +
								"$('#datepicker').hide();" +
								"window.location.href='?Day=' + dateText;" +
							"}" +
						"});" +
						"$('#datepicker').hide();" +
						"$('#datepicker').datepicker('setDate', '" + DATE.get(Calendar.YEAR) + "-" + (DATE.get(Calendar.MONTH) + 1) + "-" + DATE.get(Calendar.DAY_OF_MONTH) + "');" +
						
						//clickHandler
						"$('#datepickerImage').click(function() {" +
							"var visible = $('#datepicker').is(':visible');" +
							"visible ? $('#datepicker').hide() : $('#datepicker').show();" +
						"});" +					
						"$('#date_right').click(function(){changeDay(1);});" +
						"$('#date_left').click(function(){changeDay(-1);});" +
					"});");
			

			
			
			return builder.toString();
		}else if(isMobile) {
StringBuilder builder = new StringBuilder();
			
			//NextDay
			builder.append(
					"function changeDay(days) {" +

						"var date = new Date('" + DATE.get(Calendar.YEAR) + "-" + (DATE.get(Calendar.MONTH) + 1) + "-" + DATE.get(Calendar.DAY_OF_MONTH) + "');" +
						"date.setDate(date.getDate()+ days);" +
						"window.location.href='?O=" + CHOSEN_OUTLAY + "&Day=' + date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();" +
						
					"};");
			
			builder.append(
					"$(function(){" +
						//clickHandler
						"$('#date_right').click(function(){changeDay(1);});" +
						"$('#date_left').click(function(){changeDay(-1);});" +
					"});");
			

			
			
			return builder.toString();
		}
		
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
		"</div></br></br>");
		
		// Weekend
		if (DATE.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY || DATE.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY) {
			return builder.toString() + "<div class='noPlan'>An Wochenenden hat die Mensa geschlossen.</div></br></br></br>";
		}
		
		// No Plan
		{
			Calendar border = Calendar.getInstance();
			border.add(Calendar.WEEK_OF_YEAR, 2);
			if (DATE.after(border)) {
				return builder.toString() + "<div class='noPlan'>Die Gerichte der Mensa stehen nur für die nächsten zwei Wochen fest</div></br></br></br>";
			}
		}
		
		// Normal
		String[] categoryNames = new String[] {"Hauptgericht", "Pasta", "Alternativ", "Beilage"};
		
		for (Outlay outlay: Handler.getInstance().getMealManager().getOutlays()) {
			builder.append("<div class='mealcontainer'>");
			builder.append("<div class='ausgabe'>" + outlay.getName() + "</div>");
			for (String outlayName: categoryNames) {
				String[] names = Handler.getInstance().getMealManager().getPlan(outlay, DATE).getMeals(Handler.getInstance().getMealManager().getKategorie(outlayName));
				Arrays.sort(names);
				if (names.length > 0) {
					builder.append("<div class='mealtype'>" + outlayName + "</div>");
					for (String mealName: names) {
						Meal meal = Handler.getInstance().getMealManager().getMeal(mealName);
						Integer priceDec = meal.getPrice() % 100;
						String price = "";

						if ((meal.getPrice() / 100) > 0 || priceDec > 0) {
							price = (meal.getPrice() / 100) + "." + ((priceDec < 10) ? "0" + priceDec : priceDec) + " &#8364";
						} else {
							price = "<span title='Kein Preis bekannt'>? &#8364</span>";
						}
						
						try {
							mealName = URLEncoder.encode(mealName, "UTF8");
						} catch (UnsupportedEncodingException e) {
							//do nothing
						}
						builder.append(
								"<div class='meal pointer' onclick=\"window.location.href='?Meal=" + mealName + "'\"" +
												(meal.getName().length() > MAX_NAME_LENGTH ? " title='" + meal.getName() + "'>" : ">") +
									"<div class='mealpicture'>" +
										"<img src='images/meals/" + (meal.hasPicture() ? meal.getName() + "/1_small.jpg'>" : "template_small.png'>") +
									"</div>" +
									"<div class='mealtitle'>" +
										"<b>" + shortenMealName(meal.getName()) + "</b> " + loadLabel(meal) + 
										"<br>" +
										price +
									"</div>" +
									"<div class='mealrating'>" +
										loadRating(meal, false) +
									"</div>" +
									"<div class='mealcomments'>" +
										"" + meal.getComments().get().length +
									"</div>" +
								"</div>" );
					}
					if (!outlayName.equals(categoryNames[categoryNames.length - 1])) {
						builder.append("<hr/>");
					} 
				}
			}
			builder.append("</div>");
		}


		return builder.toString();
	}



	public static String loadRating(Meal meal, boolean isMobile) {
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
					builder.append("<img src='images/star_green_" + (isMobile ? "medium" : "small") + ".png'>");
				} else {
					builder.append("<img src='images/star_gray_"+ (isMobile ? "medium" : "small") + ".png'>");
				}
			}
			builder.append("<b> Ø " + new DecimalFormat("0.00").format(1.0 * rate / count) + "</b>");
		} else {
			builder.append("Keine Bewertung");
		}

		return builder.toString();
	}

	public static String loadLabel(Meal meal) {
		StringBuilder builder = new StringBuilder();

		for (Label label: meal.getLabels()) {
			builder.append("<img title='" + label.getName() + "' src='images/mealinfo/" + label.getName() + "_small.png'>  ");
		}

		return builder.toString();
	}
	
	public static String shortenMealName(String name) {
		if(name.length() > MAX_NAME_LENGTH) {
			return (name.substring(0, MAX_NAME_LENGTH) + "...");
		}
		return name;
	}
	
	
	private String showMobile(String chosenOutlay) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(
				"<div id='date'>" +
						"<span id='date_left'>" +
							"<img class='arrow' src='images/arrow_left.png'>" +
						"</span>" +
						"<span id='date_middle'>" +
							"<b>Angebot vom " + DATE.get(Calendar.DAY_OF_MONTH) + "." + (DATE.get(Calendar.MONTH) + 1) + "." + DATE.get(Calendar.YEAR) + "</b></span>" +
						"<span id='date_right'>" +
							"<img class='arrow' src='images/arrow_right.png'>" +
						"</span>" +
				"</div>");
		
		String[] categoryNames = new String[] {"Hauptgericht", "Pasta", "Alternativ", "Beilage"};

		for (Outlay outlay: Handler.getInstance().getMealManager().getOutlays()) {
		if (outlay.getName().equals(chosenOutlay)) {
			builder.append("<div class='header'><b>" + outlay.getName() + "</b></div>");
			for (String categoryName: categoryNames) {
				String[] names = Handler.getInstance().getMealManager().getPlan(outlay, DATE).getMeals(Handler.getInstance().getMealManager().getKategorie(categoryName));
				Arrays.sort(names);
				if (names.length > 0) {
					builder.append("<div class='meal_category'><b>" + categoryName + "</b></div>");
					for (String mealName: names) {
						Meal meal = Handler.getInstance().getMealManager().getMeal(mealName);
						Integer priceDec = meal.getPrice() % 100;
						try {
							mealName = URLEncoder.encode(mealName, "UTF8");
						} catch (UnsupportedEncodingException e) {
							//do nothing
						}
						builder.append(
								"<div class='meal' onclick=\"window.location.href='?Meal=" + mealName + "'\"" +
										(meal.getName().length() > MAX_NAME_LENGTH ? " title='" + meal.getName() + "'>" : ">") +
											"<b>" + shortenMealName(meal.getName()) + "</b> " + loadLabel(meal) + 
											"<br>");
											if ((meal.getPrice() / 100) > 0 || priceDec > 0) {
												builder.append((meal.getPrice() / 100) + "." + ((priceDec < 10) ? "0" + priceDec : priceDec) + " &#8364");
											} else {
												builder.append("<span title='Kein Preis bekannt'>? &#8364</span>");
											}
											builder.append("<div class='mealrating'>" +
											loadRating(meal, true) +
											"</div>" +
								"</div>" );
					}
				}
			}
			builder.append("</div><br/><br/>");
		}

		}
		return builder.toString();
	}

	private String showChooseOutlay() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(
				"<div class='header'>" +
						"<b>Mensaauswahl</b>" +
				"</div>" +
						
				"<div class='canteen' onclick=\"window.location.href='?"+ AbstractServlet.PARAM_OUTLAY + "=A'\">" +
					"<b>Mensa Ulhornsweg Ausgabe A</b>" +
				"</div>" +
				"<div class='canteen' onclick=\"window.location.href='?"+ AbstractServlet.PARAM_OUTLAY + "=B'\">" +
					"<b>Mensa Ulhornsweg Ausgabe B</b>" +
				"</div>" +
				"<div class='canteen' onclick=\"window.location.href='?"+ AbstractServlet.PARAM_OUTLAY + "=Culinarium'\">" +
					"<b>Mensa Ulhornsweg Culinarium</b>" +
				"</div>" +
				"<div class='canteen' onclick=\"window.location.href='?"+ AbstractServlet.PARAM_OUTLAY + "=Wechloy'\">" +
					"<b>Mensa Wechloy</b>" +
				"</div>" +
				"<div class='canteen' onclick=\"window.location.href='?"+ AbstractServlet.PARAM_OUTLAY + "=Ofener Straße'\">" +
					"<b>Mensa Ofener Straße</b>" +
				"</div><br/><br/><br/>"
			);
			
			return builder.toString();
		}

		
	
	
	
}
