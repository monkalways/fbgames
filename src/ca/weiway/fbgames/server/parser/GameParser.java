package ca.weiway.fbgames.server.parser;

import java.io.IOException;
import java.util.List;

import ca.weiway.fbgames.shared.model.Game;

public interface GameParser {
	Game parse(String url) throws IOException;
	List<String> parseGameLinks(String url) throws IOException;
}
