package lecker.view.siteElement;



import lecker.presenter.Handler;
import lecker.view.MainSiteElement;



/**
 * Shown whenever something seems not right.
 * 
 * @author LWagner
 *
 */
public class ExceptionHtml implements MainSiteElement {
	public ExceptionHtml(Exception exc) {
		Handler.getInstance().getExceptionHandler().handle(exc);
	}
	
	
	
	@Override
	public String getCode(String remoteAddr, boolean isMobile) {
		return "";
	}
	
	@Override
	public String getSkript(String remoteAddr, boolean isMobile) {
		return "";
	}

	
	
	@Override
	public String getTitle() {
		return "Fehler";
	}
}
