package ca.weiway.fbgames.server.guice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.customware.gwt.dispatch.shared.DispatchException;

import ca.weiway.fbgames.server.handler.ImportGameHandler;
import ca.weiway.fbgames.server.handler.ParseGamesHandler;
import ca.weiway.fbgames.server.parser.GameParser;
import ca.weiway.fbgames.shared.action.ImportGameAction;
import ca.weiway.fbgames.shared.action.ParseGamesAction;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GamesParsingServlet extends HttpServlet {
	
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
		
		String url = "http://www.bestbuy.ca/catalog/category.aspx?lang=en-CA&category=24358&Page=1&PageSize=15";
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<title>Games Imported</title>" +
	       "<body bgcolor=FFFFFF>");

		out.println("<h2>URL: " + url + "</h2>");
		
		out.println("<p>Games imported:</p><ul>");
		
		ParseGamesAction action = new ParseGamesAction(url);
		
		try {
			Map<String, String> games = parseGamesHandler.execute(action, null).getGames();
			int counter = 0;
			for(String game : games.keySet()) {
				System.out.println("Parsing: " + game);
				ImportGameAction importAction = new ImportGameAction(games.get(game));
				importGameHandler.execute(importAction, null);
				counter++;
				out.println("<li>" + game + "</li>");
			}
			out.println("</ul><body>");
			
			out.close();
			
			
		} catch (DispatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
