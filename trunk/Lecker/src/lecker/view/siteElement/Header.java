package lecker.view.siteElement;



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
						"<button>Registrieren</button>" +
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
		
		buffer.append(
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
