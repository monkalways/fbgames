package ca.weiway.fbgames.client.ui;

import ca.weiway.fbgames.client.place.HomePlace;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditGameViewImpl extends Composite implements EditGameView {

	private static EditGameViewImplUiBinder uiBinder = GWT
			.create(EditGameViewImplUiBinder.class);

	interface EditGameViewImplUiBinder extends
			UiBinder<Widget, EditGameViewImpl> {
	}

	public EditGameViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private Presenter presenter;
	
	@UiField 
	Button btnSave;
	
	@UiField 
	Button btnCancel;
	
	@UiField 
	TextBox txtName;
	
	@UiField 
	TextBox txtPrice;

	@UiField 
	TextBox txtImageLink;
	
	@UiField 
	Label pageTitle;

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("btnSave")
	void btnSaveClicked(ClickEvent event) {
		presenter.doSave();
	}
	
	@UiHandler("btnCancel")
	void btnCancelClicked(ClickEvent event) {
		presenter.goTo(new HomePlace());
	}

	@Override
	public String getName() {
		return txtName.getValue();
	}

	@Override
	public Double getPrice() {
		return Double.parseDouble(txtPrice.getValue());
	}

	@Override
	public String getImageLink() {
		return txtImageLink.getValue();
	}

}
