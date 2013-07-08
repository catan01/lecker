package lecker.presenter.servlet;



import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lecker.model.data.User;
import lecker.model.db.AddUserFavoriteDBStatement;
import lecker.presenter.AbstractServlet;
import lecker.presenter.Handler;



@WebServlet("/Favorite")
public class FavoriteServlet extends AbstractServlet {
	private static final long serialVersionUID = -138956197643698542L;

	public final static String PARAM_MEAL = "Meal";
	
	
	
	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.println("TEST");
			Map<String, String[]> map = request.getParameterMap();
			if (map.containsKey(PARAM_MEAL)? map.get(PARAM_MEAL) != null : false) {
				User user = Handler.getInstance().getUserManager().getUser(request.getRemoteAddr());
				if (user == null) {
					response.getOutputStream().print("Sie sind nicht Eingeloggt");
					return;
				}
				if (Arrays.asList(user.getFavorites()).contains(map.get(PARAM_MEAL)[0])) {
					response.getOutputStream().print("Diese Speise gehört bereits zu ihren Favoriten.");
					return;
				}
				if (!new AddUserFavoriteDBStatement(map.get(PARAM_MEAL)[0], user.getName()).postQuery()) {
					throw new Exception();
				}
				Handler.getInstance().getUserManager().getUser(request.getRemoteAddr()).addFavorite(map.get(PARAM_MEAL)[0]);
				response.getOutputStream().print("");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			response.getOutputStream().print("Es ist ein Fehler aufgetreten");
		} finally {
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}
