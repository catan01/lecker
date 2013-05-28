package lecker.view.siteElement;



import lecker.presenter.Handler;
import lecker.view.SiteElement;



public class Header implements SiteElement {
	@Override
	public String getNormalCode(String remoteAddr) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(
				"<div id='lightBox2'>" +
					"<div id='overlay_login'>" +
						"<div class='login_title'>" +
							"Anmeldung" + 
						"</div>" +
						"<div class='overlay_close'>" +
							"<button onclick='overlayLogin()'>X</button>" +
						"</div>" +
						"<div class='login_name'>" +
							"Benutzername: <input class='login_name'>" +
						"</div>" +
						"<div class='login_password'>" +
							"Passwort: <input class='login_password'> <button>Anmelden</button>" +
						"</div>" +
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
						"<button onclick='overlayLogin('display');'>Anmelden</button>" +
						"<button>Registrieren</button><br/>" +
						"<div id='search'>" +
							"<input id='autocomplete' placeholder='Gericht suchen'>" +
						"</div>" +
					"</div>" +
				"</div>" +
				"<hr/>");
		
		return buffer.toString();
	}

	@Override
	public String getMobileCode(String remoteAddr) {
		return "";
	}

	@Override
	public String getNormalSkript(String remoteAddr) {
		StringBuffer buffer = new StringBuffer();
		
		StringBuffer autoRes = new StringBuffer();
		for (String name: Handler.getInstance().getMealManager().getMealNames()) {
			autoRes.append(",'" + name + "'");
		}
		autoRes.deleteCharAt(0);
		
		buffer.append(
				"$(function() { var availableTags = [" + autoRes + "];" +
				  			"$('#autocomplete').autocomplete({ source : availableTags });});" +
				"function overlayLogin(mode) {" +
					"if (mode == 'display') {" +
						"if (document.getElementById('overlay') === null) {" +
							"div = document.createElement('div');" +
							"div.setAttribute('id', 'overlay');" +
							"div.setAttribute('onclick', 'overlayLogin();');" +
							"document.getElementsByTagName('body')[0]" +
									".appendChild(div);" +
							"$('#lightBox2').show();" +
						"}" +
					"} else {" +
						"document.getElementsByTagName('body')[0]" +
								".removeChild(document.getElementById('overlay'));" +
						"$('#lightBox2').hide();" +
					"}" +
				"};");
		
		return buffer.toString();
	}

	@Override
	public String getMobileSkript(String remoteAddr) {
		return "";
	}
}
