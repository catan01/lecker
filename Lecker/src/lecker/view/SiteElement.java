package lecker.view;



/**
 * Abstract for specific side content.
 * 
 * @author LWagner
 *
 */
public interface SiteElement {
	public String getCode(String remoteAddr, boolean isMobile);
	public String getSkript(String remoteAddr, boolean isMobile);
}
