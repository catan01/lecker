package lecker.view.siteElement;



import lecker.view.MainSiteElement;



/**
 * Shown whenever something seems not right.
 * 
 * @author LWagner
 *
 */
public class ExceptionHtml implements MainSiteElement {
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
		return "Fehler";
	}
}
