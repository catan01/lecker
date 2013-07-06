package lecker.presenter;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lecker.view.Constructor;



/**
 * A simple abstract for every servlet, implements the init method
 * 
 * @author LWagner
 *
 */
public abstract class AbstractServlet  extends HttpServlet{
	private static final long serialVersionUID = -2073359112073417611L;
	
	public final static String PARAM_MEAL = "Meal";
	public final static String PARAM_DAY = "Day";
	public final static String PARAM_OUTLAY = "O";
			
	public final static String PARAM_SEARCH = "s";
	
	protected Constructor constructor;

	
	
	@Override
	public synchronized void init() throws ServletException {
		super.init();
		Handler.getInstance().getLoader().init();
		constructor = new Constructor();
	}
	
	@Override
	public synchronized void destroy() {
		super.destroy();
		Handler.getInstance().getLoader().destruct();
		constructor = null;
	}
	
	
	
	@Override
	public synchronized void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
