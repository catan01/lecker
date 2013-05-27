package lecker.view.siteElement;



import lecker.view.MainSiteElement;



/**
 * The normal landing page.
 * 
 * @author LWagner
 *
 */
public class IndexHtml implements MainSiteElement {
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
