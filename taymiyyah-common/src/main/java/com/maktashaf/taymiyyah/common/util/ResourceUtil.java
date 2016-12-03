package com.maktashaf.taymiyyah.common.util;

import java.io.InputStream;
import java.util.Properties;

import com.maktashaf.taymiyyah.common.ProjectConstant;

/**
 * @author Haroon Anwar Padhyar.
 */
public class ResourceUtil {

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
