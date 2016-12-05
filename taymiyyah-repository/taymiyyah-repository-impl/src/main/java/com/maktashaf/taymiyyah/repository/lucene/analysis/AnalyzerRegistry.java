package com.maktashaf.taymiyyah.repository.lucene.analysis;

import java.util.Map;

import com.google.common.collect.Maps;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ar.ArabicCustomizedAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.en.EnglishPhoneticAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ur.UrduAnalyzer;
import com.maktashaf.taymiyyah.common.LocaleEnum;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;

/**
 * Registry for all available language analyzers in system.
 *
 * @author Haroon Anwar Padhyar.
 */
public class AnalyzerRegistry {
  private static Map<LocaleEnum, Analyzer> analyzerTable = Maps.newEnumMap(LocaleEnum.class);

  static {
    ArabicCustomizedAnalyzer arabicCustomizedAnalyzer = new ArabicCustomizedAnalyzer(Version.LUCENE_46);
    analyzerTable.put(LocaleEnum.Arabic, arabicCustomizedAnalyzer);
    analyzerTable.put(LocaleEnum.Urdu, new UrduAnalyzer(Version.LUCENE_46));
    analyzerTable.put(LocaleEnum.English, new EnglishPhoneticAnalyzer(Version.LUCENE_46));
  }

  public static Analyzer getAnalyzer(LocaleEnum localeEnum){
    return analyzerTable.get(localeEnum);
  }

}
