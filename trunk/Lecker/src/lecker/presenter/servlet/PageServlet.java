package lecker.presenter.servlet;



import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lecker.presenter.AbstractServlet;
import lecker.view.siteElement.ExceptionHtml;
import lecker.view.siteElement.IndexHtml;
import lecker.view.siteElement.MealHtml;
import lecker.view.siteElement.SearchHtml;



@WebServlet("/")
public class PageServlet extends AbstractServlet {
	private static final long serialVersionUID = 4437194099333577090L;

	
	
	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userAgent = request.getHeader("User-Agent");
		Boolean isMobile = userAgent.contains("iPhone") || userAgent.contains("iPad") || userAgent.contains("Opera Mini") || userAgent.contains("Opera Mobi") || userAgent.contains("BlackBerry") || userAgent.contains("Android");
		try {
			response.setContentType("text/html");
			Object attr;
			String chosenOutlay = (request.getParameter(PARAM_OUTLAY) != null ? request.getParameter(PARAM_OUTLAY) : "");
			if ((attr = request.getParameter(PARAM_MEAL)) != null) {
				attr = URLDecoder.decode((String)attr, "UTF8" );
				response.getOutputStream().print(constructor.getSite(request.getRemoteAddr(), new MealHtml((String) attr), isMobile));
			} else if ((attr = request.getParameter(PARAM_DAY)) != null) {
					response.getOutputStream().print(constructor.getSite(request.getRemoteAddr(), new IndexHtml((String) attr, chosenOutlay), isMobile));
			} else if ((attr = request.getParameter(PARAM_SEARCH)) != null){
				response.getOutputStream().print(constructor.getSite(request.getRemoteAddr(), new SearchHtml((String) attr), isMobile));
			} else {
					response.getOutputStream().print(constructor.getSite(request.getRemoteAddr(), new IndexHtml(chosenOutlay), isMobile));
			}
		} catch (Exception exc) {
			response.getOutputStream().print(constructor.getSite(request.getRemoteAddr(), new ExceptionHtml(exc), isMobile));
		} finally {
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
	@Override
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
