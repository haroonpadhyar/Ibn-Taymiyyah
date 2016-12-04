package com.maktashaf.taymiyyah.analysis.generator.translation.english;

import com.google.common.base.Optional;
import com.maktashaf.taymiyyah.analysis.generator.IndexGenerator;
import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.util.PathResolver;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.repository.lucene.analysis.AnalyzerRegistry;
import com.maktashaf.taymiyyah.repository.lucene.spellcheck.SpellAdviser;
import com.maktashaf.taymiyyah.repository.lucene.spellcheck.SpellAdviserImpl;
import com.maktashaf.taymiyyah.search.service.QuranSearchSearchServiceImpl;
import com.maktashaf.taymiyyah.search.service.QuranSearchService;
import com.maktashaf.taymiyyah.vo.SearchResult;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Haroon Anwar Padhyar
 */
public class AhmedRazaKhanIndexGenerator extends IndexGenerator{
  private QuranSearchService quranSearchService = new QuranSearchSearchServiceImpl();
  private SpellAdviser spellAdviser = new SpellAdviserImpl();

  @Test
  public void createIndex(){
    createIndex(Optional.of(Translator.English_AhmedRazaKhanEn), "./data/translation/english/en.ahmedraza.txt");
  }

  @Test
  @Ignore
  public void searchIndex(){
    try {
      String term = "Mohamad";
//      term = "MHMT";
      SearchParam searchParam = SearchParam.builder()
          .withTerm(term)
          .withLocale(LocaleEnum.English)
          .withTranslator(Translator.English_AhmedRazaKhanEn)
          .withOriginal(false)
          .withPageNo(1)
          .withPageSize(12)
          .build();

      SearchResult searchResult = quranSearchService.doFullTextSearch(searchParam);

      for (Quran quran : searchResult.getQuranList()) {
        System.out.println(quran.getAyahTranslationText());
        System.out.println("-------------------");
      }
//      assertEquals(4, search.size());

    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @Ignore
  public void doSpellCheck(){//TODO Egnlish spell check
    String term = "hamad";
    try {
//      PathResolver.resolveSpellIndexPath(Optional.of(Translator.English_YousufAli))));
      Optional<Translator> translatorOptional = Optional.of(Translator.English_AhmedRazaKhanEn);
      String suggestion = spellAdviser.suggest(
          term, PathResolver.resolveSpellIndexPath(translatorOptional),
          AnalyzerRegistry.getAnalyzer(translatorOptional.get().getLocaleEnum())
      );

      System.out.println(suggestion);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
}
