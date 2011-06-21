package ca.weiway.fbgames.shared.action;

import net.customware.gwt.dispatch.shared.Action;
import ca.weiway.fbgames.shared.model.Game;

public class SaveGameAction implements Action<SaveGameResult> {

	private static final long serialVersionUID = -1716760883016361503L;

	private Game game;

	public SaveGameAction() {
	}

	public SaveGameAction(final Game type) {
		this.game = type;
	}

	public void setGame(final Game type) {
		this.game = type;
	}

	public Game getGame() {
		return game;
	}

}

