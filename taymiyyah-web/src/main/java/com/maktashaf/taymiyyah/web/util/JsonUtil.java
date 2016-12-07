package com.maktashaf.taymiyyah.web.util;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

/**
 * Utility class for JSON data.
 *
 * @author Haroon Anwar Padhyar.
 */
public class JsonUtil {

  /**
   * Make java object to json string.
   *
   * @param json
   * @param classOfT
   * @param <T>
   * @return Object of type T.
   */
  public static <T> T fromJson(String json, Class<T> classOfT){
    Preconditions.checkArgument(json != null , "json string cannot be null.");
    Gson gson = new Gson();
    T target = (T) gson.fromJson(json, classOfT);
    return target;
  }

  /**
   * Make json string from a java object.
   * @param object
   * @return json string.
   */
  public static String toJson(Object object){
    Gson gson = new Gson();
    return gson.toJson(object);
  }
}
