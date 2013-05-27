package lecker.presenter.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lecker.presenter.AbstractServlet;



public class CommentServlet extends AbstractServlet {
	private static final long serialVersionUID = -138956197643698542L;
	
	
	
	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO
	}
}
