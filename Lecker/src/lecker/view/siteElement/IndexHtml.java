package lecker.view.siteElement;



import java.util.Calendar;

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
	public String getNormalCode(String remoteAddr) {
		return "";
	}

	@Override
	public String getMobileCode(String remoteAddr) {
		return "";
	}

	@Override
	public String getNormalSkript(String remoteAddr) {
		return "";
	}

	@Override
	public String getMobileSkript(String remoteAddr) {
		return "";
	}

	@Override
	public String getTitle() {
		return "Startseite";
	}
}
