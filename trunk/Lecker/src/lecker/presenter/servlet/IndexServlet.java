package lecker.presenter.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lecker.presenter.AbstractServlet;
import lecker.view.side.ExceptionHtml;
import lecker.view.side.IndexHtml;



@WebServlet("/IndexServlet")
public class IndexServlet extends AbstractServlet {
	private static final long serialVersionUID = 4437194099333577090L;

	
	
	@Override
	protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.getOutputStream().print(constructor.getSide(request.getRemoteAddr(), new IndexHtml()));
		} catch (Exception exc) {
			response.getOutputStream().print(constructor.getSide(request.getRemoteAddr(), new ExceptionHtml()));
		} finally {
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}
