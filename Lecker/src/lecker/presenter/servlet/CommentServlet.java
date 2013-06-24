package lecker.presenter.servlet;



import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lecker.model.data.comments.Comment;
import lecker.model.db.AddMealCommentDBStatement;
import lecker.presenter.AbstractServlet;
import lecker.presenter.Handler;



@WebServlet("/Comment")
public class CommentServlet extends AbstractServlet {
	private static final long serialVersionUID = -138956197643698542L;

	public final static String PARAM_MEAL = "Meal";
	public final static String PARAM_COMMENT = "Comment";
	public final static String PARAM_RATING = "Rating";
	
	
	
	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	public synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String username = Handler.getInstance().getUserManager().getUser(request.getRemoteAddr()).getName();
			Map<String, String[]> map = request.getParameterMap();
			if (username != "" && map.containsKey(PARAM_MEAL) && map.containsKey(PARAM_COMMENT) && map.containsKey(PARAM_RATING)) {
				Comment comment = new Comment(username, Integer.parseInt(map.get(PARAM_RATING)[0]), map.get(PARAM_COMMENT)[0]);
				new AddMealCommentDBStatement(map.get(PARAM_MEAL)[0], username, comment).postQuery();
				Handler.getInstance().getMealManager().getMeal(map.get(PARAM_MEAL)[0]).addComment(comment);
				response.getOutputStream().print("");
			}
		} catch (Exception exc) {
			response.getOutputStream().print("Es ist ein Fehler aufgetreten");
		} finally {
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}
