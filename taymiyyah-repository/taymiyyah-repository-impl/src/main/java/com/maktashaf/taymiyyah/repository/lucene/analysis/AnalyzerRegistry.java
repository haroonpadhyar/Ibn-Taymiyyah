package com.maktashaf.taymiyyah.repository.lucene.analysis;

import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ar.ArabicCustomizedAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.en.EnglishPhoneticAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ur.UrduAnalyzer;
import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.Translator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;

/**
 * @author: Haroon Anwar Padhyar.
 */
public class AnalyzerRegistry {
  private static Map<LocaleEnum, Analyzer> analyzerTable = Maps.newEnumMap(LocaleEnum.class);

  static {
    ArabicCustomizedAnalyzer arabicCustomizedAnalyzer = new ArabicCustomizedAnalyzer(Version.LUCENE_46);
    analyzerTable.put(LocaleEnum.Ar, arabicCustomizedAnalyzer);
    analyzerTable.put(LocaleEnum.Ur, new UrduAnalyzer(Version.LUCENE_46));
    analyzerTable.put(LocaleEnum.En, new EnglishPhoneticAnalyzer(Version.LUCENE_46));
  }

  public static Analyzer getAnalyzer(LocaleEnum localeEnum){
    return analyzerTable.get(localeEnum);
  }

}
