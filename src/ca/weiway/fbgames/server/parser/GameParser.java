package ca.weiway.fbgames.server.parser;

import java.io.IOException;
import java.util.Map;

import ca.weiway.fbgames.shared.model.Game;

public interface GameParser {
	Game parse(String url) throws IOException;
	Map<String, String> parseGameLinks(String url) throws IOException;
}
