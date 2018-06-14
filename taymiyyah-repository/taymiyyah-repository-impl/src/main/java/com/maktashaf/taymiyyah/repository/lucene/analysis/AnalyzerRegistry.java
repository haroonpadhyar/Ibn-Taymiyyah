package com.maktashaf.taymiyyah.repository.lucene.analysis;

import java.util.Map;

import com.google.common.collect.Maps;
import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ar.ArabicCustomizedAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.en.EnglishPhoneticAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ur.UrduAnalyzer;
import org.apache.lucene.analysis.Analyzer;

/**
 * Registry for all available language analyzers in system.
 *
 * @author Haroon Anwar Padhyar.
 */
public class AnalyzerRegistry {
  private static Map<LocaleEnum, Analyzer> analyzerMap = Maps.newEnumMap(LocaleEnum.class);
  private static Map<LocaleEnum, Analyzer> dictionaryAnalyzerMap = Maps.newEnumMap(LocaleEnum.class);

  static {
    analyzerMap.put(LocaleEnum.Arabic, new ArabicCustomizedAnalyzer());
    analyzerMap.put(LocaleEnum.Urdu, new UrduAnalyzer());
    analyzerMap.put(LocaleEnum.English, new EnglishPhoneticAnalyzer());

    /**
     * Required different analyzer for dictionary index since for better spell checking we need to index original
     * words without applying stem and phonetic
     */
    dictionaryAnalyzerMap.put(LocaleEnum.Arabic, new ArabicCustomizedAnalyzer(true));
    dictionaryAnalyzerMap.put(LocaleEnum.Urdu, new UrduAnalyzer(true));
    dictionaryAnalyzerMap.put(LocaleEnum.English, new EnglishPhoneticAnalyzer(true));
  }

  public static Analyzer getAnalyzer(LocaleEnum localeEnum){
    return analyzerMap.get(localeEnum);
  }

  public static Analyzer getDictionaryAnalyzer(LocaleEnum localeEnum){
    return dictionaryAnalyzerMap.get(localeEnum);
  }

}
