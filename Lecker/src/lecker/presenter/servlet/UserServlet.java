package lecker.presenter.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lecker.model.data.User;
import lecker.presenter.AbstractServlet;
import lecker.presenter.Crypt;
import lecker.presenter.Handler;



@WebServlet("/User")
public class UserServlet extends AbstractServlet {
	private static final long serialVersionUID = -9119892559537497519L;
	
	public final static String PARAM_USER_NAME="Name";
	public final static String PARAM_USER_PW = "Password";
	public final static String PARAM_USER_MODE = "Mode";
	public final static String PARAM_MODE_NORMAL = "Normal";
	public final static String PARAM_MODE_LOGOUT = "Logout";
	
	public final static String COOKIENAMETITLE = "LeckerName";

	
	
	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (request.getParameter(PARAM_USER_MODE).equals(PARAM_MODE_LOGOUT)) {
				Handler.getInstance().getUserManager().logout(request.getRemoteAddr(), request.getParameter(PARAM_USER_NAME));
			} else if (request.getParameter(PARAM_USER_MODE).equals(PARAM_MODE_NORMAL)) {
				User user = Handler.getInstance().getUserManager().login(request.getRemoteAddr(), request.getParameter(PARAM_USER_NAME), Crypt.encrypt(request.getParameter(PARAM_USER_PW)));
				StringBuilder favorites = new StringBuilder();
				for (int i = 0; i < user.getFavorites().length; ++i) {
					if ( i != 0) {
						favorites.append(":");
					}
					favorites.append(user.getFavorites()[i]);
				}
				response.getOutputStream().print(user.getName() + "|" + favorites.toString());
			} else {
				response.getOutputStream().print("");
			}
		} catch (Exception exc) {
			response.getOutputStream().print("");
		} finally {
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}
