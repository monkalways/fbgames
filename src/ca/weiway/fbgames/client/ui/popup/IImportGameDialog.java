package ca.weiway.fbgames.client.ui.popup;

public interface IImportGameDialog {
	
	void show();
	
	void hide();
	
	void setPresenter(Presenter presenter);
	
	interface Presenter {
		void importGame(String gameLink);
	}
}
