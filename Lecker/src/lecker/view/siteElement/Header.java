package lecker.view.siteElement;



import lecker.presenter.Handler;
import lecker.presenter.servlet.UserServlet;
import lecker.view.SiteElement;



public class Header implements SiteElement {
	private final static String COOKIENAMETITLE = "LeckerName";
	
	
	
	@Override
	public String getCode(String remoteAddr, boolean isMobile) {
		if (!isMobile) {
			StringBuilder builder = new StringBuilder();
			
			builder.append(
					"<div id='fb-root'></div>" +
					"<script>(function(d, s, id) {" +
						"var js, fjs = d.getElementsByTagName(s)[0];" +
						"if (d.getElementById(id)) return;" +
						"js = d.createElement(s); js.id = id;" +
						"js.src = '//connect.facebook.net/de_DE/all.js#xfbml=1';" +
						"fjs.parentNode.insertBefore(js, fjs);" +
					"}(document, 'script', 'facebook-jssdk'));</script>" +
					
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
							"<div class='login_password' id='login_failure'>" +
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
						"</div>" +
					"</div>" +
					"<hr/>" +
					"<div id='breadcrumb'>" +
						"<a href='Page'>Startseite</a>" + //TODO: Breadcrumb
					"</div>" +
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
			  			"var textF = document.getElementById('" + UserServlet.PARAM_USER_NAME + "');" +
			  			"textF.value = getCookie('" + COOKIENAMETITLE + "');" +
					"});");
			
		  	// login
			builder.append(
		  			"function overlayLogin(mode) {" +
						"if (mode == 'display') {" +
							"if (document.getElementById('overlay') === null) {" +
								"div = document.createElement('div');" +
								"div.setAttribute('id', 'overlay');" +
								"div.setAttribute('onclick', 'overlayLogin();');" +
								"document.getElementsByTagName('body')[0].appendChild(div);" +
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
											"if(response != '') {" +
												"name = response;" +
												"document.cookie='" + COOKIENAMETITLE + "=' + name;" +
												"showLogout();" +
												"overlayLogin();" +
											"} else {" +
												"$('#login_failure').html('Es ist ein Fehler aufgetreten.<br/>Bitte überprüfen Sie ihre eingegebnen Daten und versuchen Sie es erneut.');" +
											"}" +
										"}" +
									"});" +
									"return false;" +
								"});" +
							"}" +
						"} else {" +
							"document.getElementsByTagName('body')[0].removeChild(document.getElementById('overlay'));" +
							"$('#lightBox2').hide();" +
						"}" +
					"};");
			
		  	// showLogin
			builder.append(
					"function showLogin() {" +
						"$('#menu').html('<button onclick=\\'overlayLogin(\"display\");\\'>Anmelden</button><button>Registrieren</button><br/>');" +
						"document.getElementById('onLogin').disabled = true;" +
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
								"showLogin();" +
							"}" +
						"});" +
					"};");
			
		  	// showLogout
			builder.append(
					"function showLogout() {" +
							"$('#menu').html(name+' <button onclick=\\'logout();\\'>Abmelden</button><button>Favoriten</button><br/>');" +
							"document.getElementById('onLogin').disabled = false;" +
					"};");
			
			// Cookie
			builder.append(
					"function getCookie(c_name) {" +
						"var c_value = document.cookie;" +
						"var c_start = c_value.indexOf(' ' + c_name + '=');" +
						"if (c_start == -1) {" +
							"c_start = c_value.indexOf(c_name + '=');" +
						"}" +
						"if (c_start == -1) {" +
							"c_value = null;" +
						"} else {" +
							"c_start = c_value.indexOf('=', c_start) + 1;" +
							"var c_end = c_value.indexOf(';', c_start);" +
							"if (c_end == -1) {" +
								"c_end = c_value.length;" +
							"}" +
							"c_value = unescape(c_value.substring(c_start,c_end));" +
						"}" +
						"return c_value;" +
					"}");
			
		  	return builder.toString();
		}
		return "";
	}
}
