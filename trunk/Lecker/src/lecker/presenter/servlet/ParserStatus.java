package lecker.presenter.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lecker.model.Parser;
import lecker.presenter.AbstractServlet;

/**
 * Servlet implementation class ParserStatus
 */
@WebServlet("/ParserStatus")
public class ParserStatus extends AbstractServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParserStatus() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(Parser.getLastParsed() != null) {
			response.getWriter().println("<html><body>Last parse: " + Parser.getLastParsed().toString() + "</body></html>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
