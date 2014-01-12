package com.maktashaf.taymiyyah.repository.lucene.analysis;

import com.google.common.collect.HashBasedTable;
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
  private static Table<LocaleEnum, Translator, Analyzer> analyzerTable = HashBasedTable.create();

  static {
    ArabicCustomizedAnalyzer arabicCustomizedAnalyzer = new ArabicCustomizedAnalyzer(Version.LUCENE_46);
    analyzerTable.put(LocaleEnum.Original, Translator.None, arabicCustomizedAnalyzer);
    analyzerTable.put(LocaleEnum.Ar, Translator.None, arabicCustomizedAnalyzer);
    analyzerTable.put(LocaleEnum.Ur, Translator.Maududi, new UrduAnalyzer(Version.LUCENE_46));
    analyzerTable.put(LocaleEnum.En, Translator.YousufAli, new EnglishPhoneticAnalyzer(Version.LUCENE_46));
  }

  public static Analyzer getAnalyzer(LocaleEnum localeEnum, Translator translator){
    return analyzerTable.get(localeEnum, translator);
  }

}
