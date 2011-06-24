package ca.weiway.fbgames.client.ui;

import java.util.Date;

import ca.weiway.fbgames.client.place.HomePlace;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.Element;

public class EditGameViewImpl extends LayoutContainer implements EditGameView {

	private Presenter presenter;
	
	private VerticalPanel vp;  
	  
	private FormData formData;
	
	private String heading;
	
	private TextField<String> txtName;
	private TextField<String> txtImageLink;
	private DateField txtReleaseDate;

	public void setHeading(String heading) {
		this.heading = heading;
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		formData = new FormData("-20");
		vp = new VerticalPanel();
		vp.setSpacing(10);
		createForm();
		add(vp);
	}
	
	private void createForm() {  
	    FormPanel simple = new FormPanel();  
	    simple.setHeading(heading);  
	    simple.setFrame(true);  
	    simple.setWidth(350);  
	    
	    txtName = new TextField<String>();
	    txtName.setFieldLabel("Name");  
	    txtName.setAllowBlank(false);
	    txtName.getFocusSupport().setPreviousId(simple.getButtonBar().getId());  
	    simple.add(txtName, formData);
	    
	    txtImageLink = new TextField<String>();  
	    txtImageLink.setFieldLabel("Image link");
	    txtImageLink.setAllowBlank(false);  
	    simple.add(txtImageLink, formData);  
	    
	    txtReleaseDate = new DateField();  
	    txtReleaseDate.setFieldLabel("Release Date");  
	    simple.add(txtReleaseDate, formData);
	    
	    Button btnSubmit = new Button("Submit");
	    Button btnCancel = new Button("Cancel"); 
	    simple.addButton(btnSubmit);
	    simple.addButton(btnCancel);
	    
	    btnSubmit.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				presenter.doSave();
				
			}
		});
	    
	    btnCancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				presenter.goTo(new HomePlace());
				
			}
		});
	    
	    simple.setButtonAlign(HorizontalAlignment.CENTER); 
	  
	    FormButtonBinding binding = new FormButtonBinding(simple);  
	    binding.addButton(btnSubmit);  
	  
	    vp.add(simple);  
	  }  

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public String getName() {
		return txtName.getValue();
	}

	@Override
	public Double getPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImageLink() {
		// TODO Auto-generated method stub
		return txtImageLink.getValue();
	}

	@Override
	public Date getReleaseDate() {
		return txtReleaseDate.getValue();
	}

}
