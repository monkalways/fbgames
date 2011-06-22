package ca.weiway.fbgames.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.ClassName;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class HeaderViewImpl extends Composite implements HeaderView {

	private static HeaderViewImplUiBinder uiBinder = GWT
			.create(HeaderViewImplUiBinder.class);

	interface HeaderViewImplUiBinder extends UiBinder<Widget, HeaderViewImpl> {
	}

    public interface Style extends CssResource {
        @ClassName("sign-in")
        String signIn();

        @ClassName("sign-out")
        String signOut();

        String loading();
    }

	public HeaderViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
