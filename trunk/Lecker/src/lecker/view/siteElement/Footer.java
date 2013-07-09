package lecker.view.siteElement;


import lecker.presenter.servlet.UserServlet;
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
			
			// Login
			builder.append(
					"<div id='lightBox2'>" +
						"<div id='overlay_login'>" +
							"<div class='login_title'>" +
								"Anmeldung" + 
							"</div>" +
							"<div class='overlay_close'>" +
								"<button onclick='overlayLogin()'>X</button>" +
							"</div>" +
							"<form id='login' action='.' type='POST'>" +
								"<div class='login_name'>" +
									"Benutzername:<br/>" +
									"<input class='login_name' id='" + UserServlet.PARAM_USER_NAME + "'>" +
								"</div>" +
								"<div class='login_password'>" +
									"Passwort:<br/>"+
									"<input type='password' class='login_password' id='" + UserServlet.PARAM_USER_PW + "'>" +
								"</div>" +
								"<div class='login_submit'>" +
									"<input type='submit' id='submit' value='Anmelden'>" +
								"</div>" +
							"</form>" + 
							"<div id='login_failure'>" +
							"</div>" +
						"</div>" +
					"</div>" +
					"<div id='menu'>" +
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
