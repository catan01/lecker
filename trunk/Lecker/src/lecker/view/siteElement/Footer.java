package lecker.view.siteElement;



import lecker.view.SiteElement;



public class Footer implements SiteElement {
	@Override
	public String getCode(String remoteAddr, boolean isMobile) {
		if(!isMobile) {
			StringBuilder builder = new StringBuilder();
			
			//FB-Like Button
			builder.append("<hr><div class='fb-like' data-href='http://ems.informatik.uni-oldenburg.de:1313/LeckerServlet/Page' data-send='false' data-layout='button_count' data-width='450' data-show-faces='true' data-font='verdana'></div>");
			
			return builder.toString();
		}
		
		return "";
	}
	
	@Override
	public String getSkript(String remoteAddr, boolean isMobile) {
		return "";
	}

}
