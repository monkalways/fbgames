package ca.weiway.fbgames.client.ui.popup;

import java.util.Map;

public interface IBatchImportDialog {
	
	void show();
	
	void hide();
	
	void setBusy(boolean isBusy);
	
	void setPresenter(Presenter presenter);
	
	interface Presenter {
		void importBatchGame(String gameLink);

		void parseGames(String batchImportLink);

		void importComplete();

		void importCancel();
	}

	void setParsingResult(Map<String, String> games);

	void init();

	void importNext();
}
