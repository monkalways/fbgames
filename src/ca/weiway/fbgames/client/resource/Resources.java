/*
 * Ext GWT 2.2.4 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package ca.weiway.fbgames.client.resource;

import ca.weiway.fbgames.client.resource.icons.ExampleIcons;
import ca.weiway.fbgames.client.resource.images.ExampleImages;

import com.google.gwt.core.client.GWT;

public class Resources {

  public static final ExampleImages IMAGES = GWT.create(ExampleImages.class);
  public static final ExampleIcons ICONS = GWT.create(ExampleIcons.class);

}
