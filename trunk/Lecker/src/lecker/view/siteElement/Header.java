package lecker.view.siteElement;



import lecker.presenter.Handler;
import lecker.presenter.servlet.UserServlet;
import lecker.view.SiteElement;



public class Header implements SiteElement {
	@Override
	public String getCode(String remoteAddr, boolean isMobile) {
		if (!isMobile) {
			StringBuilder builder = new StringBuilder();
			
			builder.append(
					"<div id='lightBox2'>" +
						"<div id='overlay_login'>" +
							"<div class='login_title'>" +
								"Anmeldung" + 
							"</div>" +
							"<div class='overlay_close'>" +
								"<button onclick='overlayLogin()'>X</button>" +
							"</div>" +
							"<form id='login' action='Page' type='POST'>" +
								"<div class='login_name'>" +
									"Benutzername: <input class='login_name' name='" + UserServlet.PARAM_USER_NAME + "'>" +
								"</div>" +
								"<div class='login_password'>" +
									"Passwort: <input class='login_password'name='" + UserServlet.PARAM_USER_PW + "'>" +
									"<input type='submit' name='submit' value='Anmelden'/>" +
								"</div>" +
							"</form>" + 
							"<div class='login_lostpassword'>" +
								"<a>Passwort vergessen?</a>" +
							"</div>" +
						"</div>" +
					"</div>" +
					"<div id='header'>" +
						"<div id='banner'>" +
							"<span id='title'>Lecker!</span>" +
						"</div>" +
						"<div id='menu'>" +
							"<button onclick=\"overlayLogin('display');\">Anmelden</button>" +
							"<button>Registrieren</button><br/>" +
						"</div>" +
					"</div>" +
					"<hr/>" +
					"Startseite" + //TODO: Breadcrumb
					"<div id='search'>" +
						"<input id='autocomplete' placeholder='Gericht suchen'></br>" +
					"</div></br><br/>");
			
			return builder.toString();
		}
		return "";
	}

	@Override
	public String getSkript(String remoteAddr, boolean isMobile) {
		if (!isMobile) {
			StringBuilder autoComp = new StringBuilder();
			for (String name: Handler.getInstance().getMealManager().getMealNames()) {
				autoComp.append(",'" + name + "'");
			}
			autoComp.deleteCharAt(0);
			
			return
					// Autocomplete
					"$(function() { var availableTags = [" + autoComp.toString() + "];" +
					  			"$('#autocomplete').autocomplete({ source : availableTags });});" +
		  				
		  			// Login Overlay
					"function overlayLogin(mode) {" +
						"if (mode == 'display') {" +
							"if (document.getElementById('overlay') === null) {" +
								"div = document.createElement('div');" +
								"div.setAttribute('id', 'overlay');" +
								"div.setAttribute('onclick', 'overlayLogin();');" +
								"document.getElementsByTagName('body')[0]" +
										".appendChild(div);" +
								"$('#lightBox2').show();" +
								
								// Login
								"$('#login').submit(function() {" +
								"$.ajax({" +
									"type:'POST'," +
								    "cache:false," +
									"url:'User'," +
									"data:$('#login').serialize()," +
									"success:function(response){" +
										"alert('TEST');" +
										//TODO
										"overlayLogin()" +
									"}" +
								"});" +
								"return false;" +
								"});" +
							"}" +
						"} else {" +
							"document.getElementsByTagName('body')[0]" +
									".removeChild(document.getElementById('overlay'));" +
							"$('#lightBox2').hide();" +
						"}" +
					"}";
		}
		return "";
	}
}
