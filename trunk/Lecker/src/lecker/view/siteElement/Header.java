package lecker.view.siteElement;



import lecker.presenter.AbstractServlet;
import lecker.model.data.User;
import lecker.presenter.Handler;
import lecker.presenter.servlet.UserServlet;
import lecker.view.SiteElement;



public class Header implements SiteElement {
	@Override
	public String getCode(String remoteAddr, boolean isMobile) {
		if (!isMobile) {
			StringBuilder builder = new StringBuilder();
			
			builder.append(
					"<div id='fb-root'></div>" +
							"<script>" +
							  "window.fbAsyncInit = function() {" +
							    "FB.init({appId: '511627772223820', status: true, cookie: true," +
							             "xfbml: true});" +
							  "};" +
							  "(function() {" +
							    "var e = document.createElement('script'); e.async = true;" +
							    "e.src = document.location.protocol +" +
							      "'//connect.facebook.net/de_DE/all.js';" +
							    "document.getElementById('fb-root').appendChild(e);" +
							  "}());" +
							"</script>" +

					"<script>" +
					  "window.___gcfg = {" +
					  "lang: 'de'" +
					"}" +
					"(function() {" +
					  "var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;" +
					  "po.src = 'https://apis.google.com/js/client:plusone.js?onload=OnLoadCallback';" +
					  "var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);" +
					"})();" +
					"</script>" +
					
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
									"Benutzername: <input class='login_name' id='" + UserServlet.PARAM_USER_NAME + "'>" +
								"</div>" +
								"<div class='login_password'>" +
									"Passwort: <input type='password' class='login_password' id='" + UserServlet.PARAM_USER_PW + "'>" +
								"</div>" +
								"<div class='login_submit'>" +
									"<input type='submit' id='submit' value='Anmelden'>" +
								"</div>" +
							"</form>" + 
							"<div id='login_failure'>" +
							"</div>" +
							
							"<div class='login_facebook'>" +
								"<fb:login-button autologoutlink='true';>Mit Facebook einloggen</fb:login-button>" +
							"</div>" +
							
							"<span id='signinButton'>" +
							"<span" +
							  "class='g-signin'" +
							  "data-callback='signinCallback'" +
							  "data-clientid='864440246543.apps.googleusercontent.com'" +
							  "data-cookiepolicy='single_host_origin'" +
							  "data-requestvisibleactions='http://schemas.google.com/AddActivity'" +
							  "data-scope='https://www.googleapis.com/auth/plus.login'>" +
							"</span>" +
							"</span>" +
							  
							"<div class='login_lostpassword'>" +
								"<a>Passwort vergessen?</a>" +
							"</div>" +
						"</div>" +
					"</div>" +
						
					"<div id='lightBox3'>" +
						"<div id='overlay_fav'>" +
							"<div class='fav_title'>" +
								"Favoriten" + 
							"</div>" +
							"<div class='overlay_close'>" +
								"<button onclick='overlayFavorites()'>X</button>" +
							"</div>" +
							"<div id='replace_fav'>" +
							"</div>" +
						"</div>" +
					"</div>" +
					
					
					"<div id='header'>" +
						"<div id='banner'>" +
							"<a href='.'><canvas id='logo' width='400' height='70'><span id='title'>" +
							"Lecker!</span></canvas></a>" +
							getLogoSkript() +
						"</div>" +
						"<div id='menu'>" +
						"</div>" +
					"</div>" +
					"<hr/>" +
					"<div id='search'>" +
						"<form id='searchForm' action='.' method='GET'>" +
							"<input type='text' id='searchMeal' maxlength='32' name='" + AbstractServlet.PARAM_SEARCH +"' placeholder='Gericht suchen' />" +
							"<input type='submit' hidden='true' />" +
						"</form>" +
					"</div>");
			
			return builder.toString();
		}
		else {
			//mobile
			return "";
		}
	}

	@Override
	public String getSkript(String remoteAddr, boolean isMobile) {
		User user = Handler.getInstance().getUserManager().getUser(remoteAddr);
		StringBuilder builder = new StringBuilder();
			
		// Start

		builder.append(
				"var name = '" + (user != null? user.getName() : "") + "';" +
				"$(function(){" +
					"if(name != '') {" +
						"showLogout();" +
					"} else {" +
						"showLogin();" +
					"}" +
		  			"var textF = document.getElementById('" + UserServlet.PARAM_USER_NAME + "');" +
		  			"textF.value = localStorage.getItem('" + UserServlet.COOKIENAMETITLE + "');" +
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
											"name = response.split('|')[0];" +
											"favorites = response.split('|')[1].split(':');" +
											"localStorage.setItem('" + UserServlet.COOKIENAMETITLE + "', name);" +
											"showLogout();" +
											"overlayLogin();" +
										"} else {" +
											"$('#login_failure').html(\" <div class ='login_failure'>Fehlerhafte Eingabe! </div>\");" +
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
					"$('#menu').html('<button onclick=\\'overlayLogin(\"display\");\\'>Anmelden</button>" + (isMobile? "<a href=\".\"><button>Start</button></a>" : "<button>Registrieren</button>") + "<br/>');" +
					"var commentButton = document.getElementById('onLogin');" +
					"if(commentButton) {" +
						"commentButton.disabled = true;" +
					"}" +
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
						"$('#menu').html(" + (isMobile? "" : "name + ") + "' <button onclick=\\'logout();\\'>Abmelden</button>" + (isMobile? "<a href=\".\"><button>Start</button></a>" : "<button onclick=\\'overlayFavorites(\"display\");\\'>Favoriten</button>") + "<br/>');" +
						"var commentButton = document.getElementById('onLogin');" +
						"if(commentButton) {" +
							"commentButton.disabled = false;" +
						"}" +
				"};");
		
		if (!isMobile) {
			StringBuilder favorites = new StringBuilder();
			
			if (user != null) {
				for ( int i = 0; i < user.getFavorites().length; ++i) {
					if (i != 0) {
						favorites.append(",");
					}
					favorites.append("'" + user.getFavorites()[i] + "'");
				}
			}
				
			// show the favorites
			builder.append(
					"var favorites = new Array(" + favorites + ");" +
					"function overlayFavorites(mode) {" +
						"if (mode == 'display') {" +
							"var fav = '<div class=\"favs\"><table width=\"75%\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" bordercolor=\"#0A8104\">';" +
							"var stringReplace = '';" +
							"var tableColor = '';" +
							"for (i=0; i < favorites.length ; i++ ) {" +
							"if (i%2 == 0) {" +
								"tableColor = '#D3D3D3 ';" +
							"} else {" +
								"tableColor = '#C0C0C0';" +
							"}" +
								"stringReplace = favorites[i].replace(' ', '+');" +
								"fav += '<tr><td bgcolor=\"' + tableColor + '\"><a href=\"?Meal='+ stringReplace +'\">' + favorites[i] + '</a></td></tr>';" +
							"}" +
							"fav += '</table></div>';" +
							
							"document.getElementById('replace_fav').innerHTML = fav;" +
							
							"if (document.getElementById('overlay') === null) {" +
								"div = document.createElement('div');" +
								"div.setAttribute('id', 'overlay');" +
								"div.setAttribute('onclick', 'overlayFavorites();');" +
								"document.getElementsByTagName('body')[0].appendChild(div);" +
								"$('#lightBox3').show();" +
							"}" +
						"} else {" +
							"document.getElementsByTagName('body')[0].removeChild(document.getElementById('overlay'));" +
							"$('#lightBox3').hide();" +
						"}" +
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
					"};");
		}
		return builder.toString();
	}
	
	private String getLogoSkript() {
		return  "<script>var c=document.getElementById('logo');" +
				"var ctx=c.getContext('2d');" +
				"ctx.font='64pt verdana, sans-serif, helvetica';" +
				"ctx.fillStyle='#0A8104';" +
				"ctx.fillText('Lecker!',90,64);" +
				"ctx.lineWidth = 4;" +
				"ctx.strokeStyle='#0A8104';" +
				"ctx.moveTo(2,25);" +
				"ctx.lineTo(86,25);" +
				"ctx.stroke();" +
				"ctx.beginPath();" +
				"ctx.arc(44,25,40,0,Math.PI);" +
				"ctx.stroke();" +
				"ctx.beginPath();" +
				"ctx.save();" +
				"ctx.arc(40,25,20,Math.PI,-Math.PI/2);" +
				"ctx.scale(1.5,0.1);" +
				"ctx.stroke();" +
				"ctx.beginPath();" +
				"ctx.restore();" +
				"ctx.save();" +
				"ctx.arc(60,25,20,Math.PI,-Math.PI/2);" +
				"ctx.scale(1.5,0.1);" +
				"ctx.stroke();" +
				"ctx.beginPath();" +
				"ctx.restore();" +
				"ctx.arc(80,25,20,Math.PI,-Math.PI/2);" +
				"ctx.scale(1.5,0.1);" +
				"ctx.stroke();</script>";
	}
}
