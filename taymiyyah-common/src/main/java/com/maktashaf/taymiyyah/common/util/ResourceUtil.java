package com.maktashaf.taymiyyah.common.util;

import java.io.InputStream;
import java.util.Properties;

import com.maktashaf.taymiyyah.common.ProjectConstant;

/**
 * Utility class to access System resources. Like properties file etc.
 *
 * @author Haroon Anwar Padhyar.
 */
public class ResourceUtil {

  /**
   * Load properties from properties file with provided file path.
   *
   * @param filePath
   * @return
   */
  public static Properties loadProperties(String filePath){
    Properties properties = new Properties();

    try{
      InputStream is = ProjectConstant.class.getClassLoader().getResourceAsStream(filePath);
      properties.load(is);
      is.close();
    }catch(Exception e){
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    return properties;
  }
}
