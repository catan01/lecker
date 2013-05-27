package lecker.presenter.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lecker.presenter.AbstractServlet;
import lecker.view.siteElement.ExceptionHtml;
import lecker.view.siteElement.IndexHtml;
import lecker.view.siteElement.MealHtml;



@WebServlet("/Page")
public class PageServlet extends AbstractServlet {
	private static final long serialVersionUID = 4437194099333577090L;

	
	
	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userAgent = request.getHeader("User-Agent");
		Boolean isMobile = userAgent.contains("iphone") || userAgent.contains("opera mini") || userAgent.contains("blackberry") || userAgent.contains("android");
		try {
			response.setContentType("text/html");
			Object attr;
			if ((attr = request.getAttribute(PARAM_MEAL)) != null) {
				response.getOutputStream().print(constructor.getSite(request.getRemoteAddr(), new MealHtml(attr), isMobile));
			} else {
				response.getOutputStream().print(constructor.getSite(request.getRemoteAddr(), new IndexHtml(), isMobile));
			}
		} catch (Exception exc) {
			response.getOutputStream().print(constructor.getSite(request.getRemoteAddr(), new ExceptionHtml(), isMobile));
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
