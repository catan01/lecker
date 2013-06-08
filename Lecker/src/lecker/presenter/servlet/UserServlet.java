package lecker.presenter.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lecker.presenter.AbstractServlet;



@WebServlet("/User")
public class UserServlet extends AbstractServlet {
	private static final long serialVersionUID = -9119892559537497519L;
	
	public final static String PARAM_USER_NAME="Name";
	public final static String PARAM_USER_PW = "Password";
	public final static String PARAM_USER_MODE = "Mode";
	public final static String PARAM_MODE_NORMAL = "Normal";

	
	
	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO
	}
}
