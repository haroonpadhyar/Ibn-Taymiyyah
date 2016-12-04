package com.maktashaf.taymiyyah.analysis.generator.translation.urdu;

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
 * * @author Haroon Anwar Padhyar
 */
public class AhmedAliIndexGenerator extends IndexGenerator{
  private QuranSearchService quranSearchService = new QuranSearchSearchServiceImpl();
  private SpellAdviser spellAdviser = new SpellAdviserImpl();

  @Test
  public void createIndex(){
    createIndex(Optional.of(Translator.Urdu_AhmedAli), "./data/translation/urdu/ur.ahmedali.txt");
  }

  @Test
  @Ignore
  public void searchIndex(){
    try {
      String term = "قلوبنا";
      term = "محمد";
//      term = "علي";
//      term = "على";

      SearchParam searchParam = SearchParam.builder()
          .withTerm(term)
          .withLocale(LocaleEnum.Urdu)
          .withTranslator(Translator.Urdu_AhmedAli)
          .withOriginal(false)
          .withPageNo(3)
          .withPageSize(32)
          .build();
      SearchResult searchResult = quranSearchService.doFullTextSearch(searchParam);

      System.out.println("Total: " + searchResult.getQuranList().size());
      for (Quran quran : searchResult.getQuranList()) {
        System.out.println(quran.getAyahTranslationText());
        System.out.println("-------------------");
      }
//      assertEquals(100, search.size());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @Ignore
  public void doSpellCheck(){
    String term = "مُحَمَّدٌ";
//    term = "ہارون";
    term = "ممد";
//    term = "هارون";
//    term = "هار";
//    term = "محد";
//    term = "صدری";
//    term = "محمد صدری";
//    term = "OR";
    try {
      Optional<Translator> translatorOptional = Optional.of(Translator.Urdu_AhmedAli);
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
