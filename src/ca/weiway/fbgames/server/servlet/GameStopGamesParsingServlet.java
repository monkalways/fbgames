package ca.weiway.fbgames.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.customware.gwt.dispatch.shared.DispatchException;
import ca.weiway.fbgames.server.handler.ImportGameHandler;
import ca.weiway.fbgames.server.handler.ParseGamesHandler;
import ca.weiway.fbgames.shared.action.ImportGameAction;
import ca.weiway.fbgames.shared.action.ParseGamesAction;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GameStopGamesParsingServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(GameStopGamesParsingServlet.class.getName());
	
	@Inject
	private ImportGameHandler importGameHandler;
	
	@Inject
	private ParseGamesHandler parseGamesHandler;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8682176575271517008L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
//		String url = "http://www.bestbuy.ca/catalog/category.aspx?lang=en-CA&category=24358&Page=1&PageSize=15";
	    String url = req.getParameter("url");
	    
	    if(req.getParameter("start") == null 
	    		|| req.getParameter("end") == null) {
	    	return;
	    }
	    
	    Integer start = Integer.parseInt(req.getParameter("start"));
	    Integer end = Integer.parseInt(req.getParameter("end"));
	    
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<title>Games Imported</title>" +
	       "<body bgcolor=FFFFFF>");
		if (url == null) {
			out.println("<h2>No URL passed in</h2>");
	    } else {
	    	
	    	for(int i=start; i<end; i++) {
	    		String pagerInfo = "2b" + (i*12) + ",";
	    		int navIndex = url.indexOf("nav=");
	    		String navInfo = url.substring(navIndex);
	    		navInfo = navInfo.substring(0, 4) + pagerInfo + navInfo.substring(4);
	    		String currentUrl = url.substring(0, navIndex) + navInfo;
	    		
	    		out.println("<h2>URL: " + currentUrl + "</h2>");
				out.println("<p>Games imported:</p><ul>");
				ParseGamesAction action = new ParseGamesAction(currentUrl);
				
				try {
					Map<String, String> games = parseGamesHandler.execute(action, null).getGames();
					int counter = 0;
					for(String game : games.keySet()) {
						System.out.println("Parsing: " + game);
						ImportGameAction importAction = new ImportGameAction(games.get(game));
						try {
							importGameHandler.execute(importAction, null);
							counter++;
							out.println("<li>" + game + "</li>");
						} catch(Exception ex) {
							log.severe("Error occurred when parsing " + game + "[" + games.get(game) + "]:" + ex.getMessage());
							out.println("<li>[Error] " + game + "</li>");
						}
					}
				} catch (DispatchException e) {
					e.printStackTrace();
				}
				
				out.println("</ul>");
	    	}
	    }

		out.println("</body>");
		out.close();
	}

}
