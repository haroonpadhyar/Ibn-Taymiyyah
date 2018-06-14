package com.maktashaf.taymiyyah.analysis.generator.Quran;

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
import org.junit.Test;
import org.junit.Ignore;

/**
 * * @author Haroon Anwar Padhyar
 */
public class QuranIndexGenerator extends IndexGenerator{
  private QuranSearchService quranSearchService = new QuranSearchSearchServiceImpl();
  private SpellAdviser spellAdviser = new SpellAdviserImpl();

  @Test
  public void createIndex(){
    createIndex(Optional.<Translator>absent(), "./data/Quran/quran-simple.txt");
  }

  @Test
  @Ignore
  public void searchIndex(){
    try {
      String term = "قلوبنا";
//      term = "محمد";
//      term = "علي";
//      term = "على";

      SearchParam searchParam = SearchParam.builder()
          .withTerm(term)
          .withTranslator(Translator.Urdu_Maududi)
          .withOriginal(true)
          .withPageNo(1)
          .withPageSize(12)
          .build();
      SearchResult searchResult = quranSearchService.doFullTextSearch(searchParam);

      for (Quran quran : searchResult.getQuranList()) {
        System.out.println(quran.getAyahText());
        System.out.println("-------------------");
      }
//      assertEquals(6, search.size());
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  @Ignore
  public void searchByAccumId(){
    try {
      Quran quran = quranSearchService.findByAccumId(4, Translator.Urdu_Maududi);

        System.out.println(quran.getAyahText());
        System.out.println("-------------------");
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  @Ignore
  public void searchByAyahId(){
    try {
      Quran quran = quranSearchService.findByAyahId(1, 5, Translator.Urdu_Maududi);

      System.out.println(quran.getAyahText());
      System.out.println("-------------------");
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  @Ignore
  public void searchNextByAccumId(){
    try {
      SearchResult searchResult = quranSearchService.findNextByAccumId(5, Translator.Urdu_Maududi, 10, true);

      for (Quran quran : searchResult.getQuranList()) {
        System.out.println(quran.getAyahText());
        System.out.println("-------------------");
      }
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
//    term = "ممد";
//    term = "هارون";
//    term = "هار";
//    term = "محد";
//    term = "صدری";
//    term = "محمد صدری";
//    term = "OR";
    try {
      String suggestion = spellAdviser.suggest(
          term, PathResolver.resolveSpellIndexPath(Optional.<Translator>absent()),
          AnalyzerRegistry.getAnalyzer(LocaleEnum.Arabic)
      );

      System.out.println(suggestion);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

}
