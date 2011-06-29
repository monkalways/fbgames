package ca.weiway.fbgames.client.ui.popup;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;

public class ImportGameDialog extends Dialog implements IImportGameDialog {

	private TextField<String> gameLink;
	private Button btnReset;
	private Button btnImport;
	private Status status;
	private Presenter presenter;

	public ImportGameDialog() {
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(90);
		layout.setDefaultWidth(155);
		setLayout(layout);

		setButtonAlign(HorizontalAlignment.LEFT);
		setButtons("");
		setIcon(IconHelper.createStyle("user"));
		setHeading("Import game");
		setModal(true);
		setBodyBorder(true);
		setBodyStyle("padding: 8px;background: none");
		setWidth(300);
		setResizable(false);

		KeyListener keyListener = new KeyListener() {
			public void componentKeyUp(ComponentEvent event) {
				validate();
			}

		};

		gameLink = new TextField<String>();
		gameLink.setMinLength(4);
		gameLink.setFieldLabel("Game link");
		gameLink.addKeyListener(keyListener);
		add(gameLink);

		setFocusWidget(gameLink);
	}

	@Override
	protected void createButtons() {
		super.createButtons();

		super.createButtons();
		status = new Status();
		status.setBusy("please wait...");
		status.hide();
		status.setAutoWidth(true);
		getButtonBar().add(status);

		getButtonBar().add(new FillToolItem());

		btnReset = new Button("Reset");
		btnReset.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				gameLink.reset();
				validate();
				gameLink.focus();
			}

		});

		btnImport = new Button("Import");
		btnImport.disable();
		btnImport.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				onSubmit();
			}
		});

		addButton(btnReset);
		addButton(btnImport);
	}

	protected void onSubmit() {
		presenter.importGame(gameLink.getValue());
	}

	protected void validate() {
		btnImport.setEnabled(hasValue(gameLink));
	}

	protected boolean hasValue(TextField<String> field) {
		return field.getValue() != null && field.getValue().length() > 0;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
