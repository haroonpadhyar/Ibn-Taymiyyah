package com.maktashaf.taymiyyah.web.util;

import java.util.Locale;
import java.util.ResourceBundle;

import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.ProjectConstant;

/**
 *  Utility class for Resource Bundle.
 *
 * @author Haroon Anwar Padhyar.
 */
public class ResourceBundleUtil {

  public static String getMessage(String key, Locale locale){
    if(locale == null){//Upon expired session local will be null. TODO take it from client in each request.
      locale = LocaleEnum.Urdu.value();
    }
    ResourceBundle resourceBundle = ResourceBundle.getBundle(ProjectConstant.RESOURCE_BUNDLE_PATH, locale);
    return resourceBundle.getString(key);
  }
}
