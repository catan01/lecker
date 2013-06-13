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
									"Benutzername: <input class='login_name' id='" + UserServlet.PARAM_USER_NAME + "'>" +
								"</div>" +
								"<div class='login_password'>" +
									"Passwort: <input type='password' class='login_password'id='" + UserServlet.PARAM_USER_PW + "'>" +
									"<input type='submit' id='submit' value='Anmelden'/>" +
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
			StringBuilder builder = new StringBuilder();
			StringBuilder autoComp = new StringBuilder();
			for (String name: Handler.getInstance().getMealManager().getMealNames()) {
				autoComp.append(",'" + name + "'");
			}
			autoComp.deleteCharAt(0);
			
			// Start
			builder.append(
					"var name = '" + (Handler.getInstance().getUserManager().getUser(remoteAddr) != null? Handler.getInstance().getUserManager().getUser(remoteAddr).getName() : "") + "';" +
					"$(function(){" +
						"if(name != '') {" +
							"showLogout();" +
						"} else {" +
							"showLogin();" +
						"}" +
						"var availableTags = [" + autoComp.toString() + "];" +
			  			"$('#autocomplete').autocomplete({" +
			  				"source : availableTags" +
			  			"});" +
					"});");
			
		  	// login
			builder.append(
		  			"function overlayLogin(mode) {" +
						"if (mode == 'display') {" +
							"if (document.getElementById('overlay') === null) {" +
								"div = document.createElement('div');" +
								"div.setAttribute('id', 'overlay');" +
								"div.setAttribute('onclick', 'overlayLogin();');" +
								"document.getElementsByTagName('body')[0]" +
										".appendChild(div);" +
								"$('#lightBox2').show();" +
								
								// Login Send
								"$('#login').submit(function() {" +
									"var formName = $('#" + UserServlet.PARAM_USER_NAME + "').val();" +
									"var formPW = $('#" + UserServlet.PARAM_USER_PW + "').val();" +
									"$.ajax({" +
										"type:'POST'," +
									    "cache:false," +
										"url:'User'," +
										"data:{" + UserServlet.PARAM_USER_MODE + ":'" + UserServlet.PARAM_MODE_NORMAL + "', " + UserServlet.PARAM_USER_NAME + ":formName, " + UserServlet.PARAM_USER_PW + ":formPW}," +
										"success:function(response){" +
											//TODO
											"name = response;" +
											"showLogout();" +
											"overlayLogin();" +
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
					"};");
			
		  	// showLogin
			builder.append(
					"function showLogin() {" +
						"$('#menu').html('<button onclick=\\'overlayLogin(\"display\");\\'>Anmelden</button><button>Registrieren</button><br/>');" +
					"};");
			
		  	// logout
			builder.append(
					"function logout() {" +
						"$.ajax({" +
							"type:'POST'," +
						    "cache:false," +
							"url:'User'," +
							"data: {" + UserServlet.PARAM_USER_MODE + ": '" + UserServlet.PARAM_MODE_LOGOUT + "', " + UserServlet.PARAM_USER_NAME + ": name}," +
							"success:function(response){" +
								//TODO
								"showLogin();" +
							"}" +
						"});" +
					"};");
			
		  	// showLogout
			builder.append(
					"function showLogout() {" +
							"$('#menu').html(name+' <button onclick=\\'logout();\\'>Abmelden</button><button>Favoriten</button><br/>');" +
					"};");
			
		  	return builder.toString();
		}
		return "";
	}
}
