package lecker.view.siteElement;



import lecker.view.SiteElement;



public class Footer implements SiteElement {
	@Override
	public String getCode(String remoteAddr, boolean isMobile) {
		if(!isMobile) {
			StringBuilder builder = new StringBuilder();
			
			//FB-Like Button
			builder.append("<hr><div class='g-plusone' data-size='medium'></div> <script type='text/javascript'> window.___gcfg = {lang: 'de'}; (function() { var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true; po.src = 'https://apis.google.com/js/plusone.js'; var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s); })(); </script>" + "&emsp;" + "<a href='https://twitter.com/share' class='twitter-share-button' data-lang='de' data-hashtags='lecker!'>Twittern</a> <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');</script>" + "&emsp;" + "<div class='fb-like' data-href='http://ems.informatik.uni-oldenburg.de:1313/LeckerServlet/' data-send='false' data-layout='button_count' data-width='450' data-show-faces='true' data-font='verdana'></div>");
			
			return builder.toString();
		}else if(isMobile) {
			StringBuilder builder = new StringBuilder();
			
			//Menubar
			builder.append("<div class='menu'>" +
					"<table style='width:99%'>" +
					"<tr>" +
					"<td><div class='menu_button_checked' onclick=\"window.location.href='?.'\"> <b>Start</b></div></td>" +
					"<td><div class='menu_button_checked' onclick='anmelden'><b>Anmelden</b></div></td>" +
					"</tr>" +
					"</table>" +
					"</div>");
			
			return builder.toString();
		}	
		return "";
	}
	
	@Override
	public String getSkript(String remoteAddr, boolean isMobile) {
		return "";
	}

}
