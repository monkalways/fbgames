package ca.weiway.fbgames.client.ui.popup;

import java.util.Map;

import ca.weiway.fbgames.client.resource.Resources;
import ca.weiway.fbgames.client.util.StringUtils;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.WidgetComponent;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;

public class BatchImportDialog extends Dialog implements IBatchImportDialog {
	
	private TextField<String> batchImportLink;
	private Button btnCancel;
	private Button btnImport;
	private Button btnParse;
	private Status status;
	private Presenter presenter;
	private ContentPanel contentPanel;
	private Map<String, String> games;
	private int currentGameIndex;

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	protected void onRender(Element parent, int pos) {
		
		super.onRender(parent, pos);
		
		this.setModal(true);
		
		this.setBodyBorder(false);  
	    this.setIcon(Resources.ICONS.side_list());  
	    this.setHeading("Import games");  
	    this.setWidth(400);
	    this.setHeight(400);
	    this.setHideOnButtonClick(true);  
	    
	    BorderLayout layout = new BorderLayout();  
	    this.setLayout(layout);  
	  
	    // North  
	    FormPanel formPanel = new FormPanel();
	    formPanel.setHeaderVisible(false);
	    formPanel.setFrame(true);  
	    formPanel.setWidth(400); 
	    BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 90);  
	    data.setMargins(new Margins(0, 0, 0, 0));  
	    data.setSplit(false);
	    data.setCollapsible(false);
	    populateNorthPanel(formPanel);
	    this.add(formPanel, data);
	  
	    // center  
	    contentPanel = new ContentPanel();  
	    contentPanel.setHeading("Games");
	    contentPanel.setLayout(new FitLayout());
	    data = new BorderLayoutData(LayoutRegion.CENTER);
	    this.add(contentPanel, data);
	}

	private void populateNorthPanel(FormPanel panel) {
		
		FormData formData = new FormData("0");
		
		KeyListener keyListener = new KeyListener() {
			public void componentKeyUp(ComponentEvent event) {
				validate();
			}
		};
		
		batchImportLink = new TextField<String>();
		batchImportLink.setMinLength(4);
		batchImportLink.setWidth(310);
		batchImportLink.setFieldLabel("Games link");
		batchImportLink.addKeyListener(keyListener);
		panel.add(batchImportLink, formData);

		setFocusWidget(batchImportLink);
		
		btnParse = new Button("Parse");
		btnParse.disable();
		btnParse.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				onParse();
			}
		});
		panel.addButton(btnParse);
	}
	
	@Override
	protected void createButtons() {
		
		status = new Status();
		status.setBusy("please wait...");
		status.hide();
		status.setAutoWidth(true);
		getButtonBar().add(status);

		getButtonBar().add(new FillToolItem());

		btnCancel = new Button("Cancel");
		btnCancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				if("Cancel".equals(btnCancel.getText())) {
					presenter.importCancel();
				} else {
					presenter.importComplete();
				}
			}

		});

		btnImport = new Button("Import");
		btnImport.disable();
		btnImport.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				onSubmit();
			}
		});

		addButton(btnCancel);
		addButton(btnImport);
	}
	
	protected void onSubmit() {
		btnImport.disable();
		btnCancel.disable();
		btnParse.disable();
		
		currentGameIndex = 0;
		String gameName = replaceImage(currentGameIndex, Resources.ICONS.loading());
		
		presenter.importBatchGame(games.get(gameName));
	}
	
	private String replaceImage(int index, AbstractImagePrototype imgPrototype) {
		String returnValue = null;
		if(this.contentPanel != null && this.contentPanel.getItems().size() != 0) {
			VerticalPanel vp = (VerticalPanel)contentPanel.getItem(0);
			if(vp != null) {
				HorizontalPanel hp = (HorizontalPanel)vp.getItem(index);
				Text text = (Text)hp.getItem(0);
				returnValue = text.getToolTip().getToolTipConfig().getText();
				Image image = (Image)((WidgetComponent)hp.getItem(1)).getWidget();
				imgPrototype.applyTo(image);
			}
		}
		return returnValue;
	}
	
	protected void onParse() {
		presenter.parseGames(batchImportLink.getValue());
	}
	
	private void validate() {
		btnParse.setEnabled(hasValue(batchImportLink));
	}
	
	protected boolean hasValue(TextField<String> field) {
		return field.getValue() != null && field.getValue().length() > 0;
	}

	@Override
	public void setBusy(boolean isBusy) {
		if(isBusy) {
			this.mask("Parsing");
		} else {
			this.unmask();
		}
	}

	@Override
	public void setParsingResult(Map<String, String> games) {
		this.games = games;
		
		contentPanel.removeAll();
		btnImport.show();
		
		VerticalPanel vp = new VerticalPanel();
		vp.setScrollMode(Scroll.AUTOY);
		vp.setSpacing(0);
		vp.setWidth("100%");
		
		int counter = 0;
		
		for(String gameName : games.keySet()) {
			counter++;
			
			HorizontalPanel hp = new HorizontalPanel();
			hp.setWidth("100%");
			hp.setSpacing(5);
			Text txtGameName = new Text(StringUtils.getSubtractedGameName(gameName, 50));
			txtGameName.setWidth(330);
			txtGameName.setToolTip(gameName);
			hp.add(txtGameName);
			
			Image toImport = new Image(); 
			Resources.ICONS.toImport().applyTo(toImport);
			hp.add(toImport);
			
			if(counter < games.size()) {
				txtGameName.addStyleName("parseGameHpStyle");
			} else {
				txtGameName.addStyleName("parseGameHpStyleLast");
			}
			
			vp.add(hp);
		}
		
		if(!games.isEmpty()) {
			btnImport.enable();
		}
		
		contentPanel.add(vp);
		contentPanel.layout();
	}

	@Override
	public void init() {
		if(batchImportLink != null) {
			batchImportLink.clear();
		}
		if(contentPanel != null) {
			contentPanel.removeAll();
		}
	}

	@Override
	public void importNext() {
		currentGameIndex++;
		if(currentGameIndex < games.size()) {
			replaceImage(currentGameIndex-1, Resources.ICONS.imported());
			String gameName = replaceImage(currentGameIndex, Resources.ICONS.loading());
			presenter.importBatchGame(games.get(gameName));
		} else {
			replaceImage(currentGameIndex-1, Resources.ICONS.imported());
			btnImport.hide();
			btnCancel.setText("Close");
			btnCancel.enable();
		}
	}
	
	
}
