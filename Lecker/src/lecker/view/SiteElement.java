package lecker.view;



/**
 * Abstract for specific side content.
 * 
 * @author LWagner
 *
 */
public interface SiteElement {
	public String getNormalCode(String remoteAddr);
	public String getMobileCode(String remoteAddr);
	
	public String getNormalSkript(String remoteAddr);
	public String getMobileSkript(String remoteAddr);
}
