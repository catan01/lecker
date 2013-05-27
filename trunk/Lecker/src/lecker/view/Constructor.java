package lecker.view;



import lecker.view.siteElement.Footer;
import lecker.view.siteElement.Header;



/**
 * Creates the General site and inherits the other elements.
 * 
 * @author LWagner
 *
 */
public class Constructor {
	private final static String title = " - Lecker";
	
	private final Header HEADER;
	private final Footer FOOTER;
	
	
	
	public Constructor() {
		this.HEADER = new Header();
		this.FOOTER = new Footer();
	}
	
	
	
	public String getSite(String remoteAddr, MainSiteElement site, boolean isMobile) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(
				"<html>" +
					"<head>" +
						"<title>" + site.getTitle() + title + "</title>" +
						"<link rel='icon' href='images/favicon.png' type='image/png'>" +
						"<link href='css/style.css' rel='stylesheet'>" +
						"<link href='css/south-street/jquery-ui-1.10.2.custom.min.css' rel='stylesheet'>" +
						"<script src='js/jquery-1.9.1.js'></script>" +
						"<script src='js/jquery-ui-1.10.2.custom.js'></script>" +
						"<script>" + HEADER.getNormalSkript(remoteAddr) + site.getNormalSkript(remoteAddr) + FOOTER.getNormalSkript(remoteAddr) + "</script>" +
					"</header>" +
					"<body>" + HEADER.getNormalCode(remoteAddr) + site.getNormalCode(remoteAddr) + FOOTER.getNormalCode(remoteAddr) + "</body>" +
				"</html>");
						
		return buffer.toString();
	}
}
