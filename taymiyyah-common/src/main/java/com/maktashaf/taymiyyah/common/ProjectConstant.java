package com.maktashaf.taymiyyah.common;

import java.util.Properties;

import com.maktashaf.taymiyyah.common.util.ResourceUtil;

/**
 * Class to hold project level constants.
 *
 * @author Haroon Anwar Padhyar.
 */
public abstract class ProjectConstant {
  public static final String QURANN_DIR = "Quraan";
  public static final String TRANSLATION_DIR = "Translation";
  public static final String SPELL_CHECK_DIR = "SpellCheck";
  public static final String SYSTEM_PROPERTIES_FILE_PATH = "system.properties";
  public static final String LUCENE_INDEX_PATH;
  public static final int    RESULT_PAGE_SIZE;

  static {
    Properties properties = ResourceUtil.loadProperties(SYSTEM_PROPERTIES_FILE_PATH);
    LUCENE_INDEX_PATH = properties.getProperty("lucene.index.path");
    RESULT_PAGE_SIZE = Integer.valueOf(properties.getProperty("result.page.size")).intValue();
  }
}
