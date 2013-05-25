package lecker.presenter;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import lecker.view.Constructor;



/**
 * A simple abstract for every servlet, implements the init method
 * 
 * @author LWagner
 *
 */
public abstract class AbstractServlet  extends HttpServlet{
	private static final long serialVersionUID = -2073359112073417611L;
	
	protected Constructor constructor;

	
	
	@Override
	public synchronized void init() throws ServletException {
		super.init();
		Loader.getInstance().init();
		constructor = new Constructor();
	}
	
	@Override
	public synchronized void destroy() {
		super.destroy();
		Loader.getInstance().destruct();
		constructor = null;
	}
}
