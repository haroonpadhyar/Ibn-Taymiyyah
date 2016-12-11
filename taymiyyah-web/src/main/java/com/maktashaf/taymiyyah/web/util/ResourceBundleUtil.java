package com.maktashaf.taymiyyah.web.util;

import java.util.Locale;
import java.util.ResourceBundle;

import com.maktashaf.taymiyyah.common.ProjectConstant;

/**
 *  Utility class for Resource Bundle.
 *
 * @author Haroon Anwar Padhyar.
 */
public class ResourceBundleUtil {

  public static String getMessage(String key, Locale locale){
    ResourceBundle resourceBundle = ResourceBundle.getBundle(ProjectConstant.RESOURCE_BUNDLE_PATH, locale);
    return resourceBundle.getString(key);
  }
}
