package com.maktashaf.taymiyyah.analysis.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.QuranField;
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
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

/**
 * @author: Haroon
 */
public class IndexGenerator {
  private QuranSearchService quranSearchService = new QuranSearchSearchServiceImpl();
  private SpellAdviser spellAdviser = new SpellAdviserImpl();
  private static final String QURAN_METADATA_FILE_PATH = "./data/Quran_metadata.txt";
  private static final int QURAN_AYAH_COUNT = 6236;

  // -- Quran
  @Test
  public void createIndexForQuran(){
    createIndex(Optional.<Translator>absent(), "./data/Quran/quran-simple.txt");
  }

  @Test
  public void searchIndexForQuran(){
    try {
      String term = "قلوبنا";
//      term = "محمد";
//      term = "علي";
//      term = "على";

      SearchParam searchParam = SearchParam.builder()
          .withTerm(term)
          .withLocale(LocaleEnum.Arabic)
          .withTranslator(Translator.Maududi)
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
  public void doSpellCheckForQuran(){
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

  // -- Maududi
  @Test
  public void createIndexForMaududiTranslation(){
    createIndex(Optional.of(Translator.Maududi), "./data/translation/urdu/ur.maududi.txt");
  }

  @Test
  public void searchIndexMaududiTranslation(){
    try {
      String term = "قلوبنا";
      term = "محمد";
//      term = "علي";
//      term = "على";

      SearchParam searchParam = SearchParam.builder()
          .withTerm(term)
          .withLocale(LocaleEnum.Urdu)
          .withTranslator(Translator.Maududi)
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
  public void doSpellCheckForMaududiTranslation(){
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
      Optional<Translator> translatorOptional = Optional.of(Translator.Maududi);
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


  // -- Yousuf Ali
  @Test
  public void createIndexForYousufAliTranslation(){
    createIndex(Optional.of(Translator.YousufAli), "./data/translation/english/en.yusufali.txt");
  }

  @Test
  public void searchIndexForYousufAliTranslation(){
    try {
      String term = "Mohamad";
//      term = "MHMT";
      SearchParam searchParam = SearchParam.builder()
          .withTerm(term)
          .withLocale(LocaleEnum.English)
          .withTranslator(Translator.YousufAli)
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
  public void doSpellCheckForYousufAliTranslation(){//TODO Egnlish spell check
    String term = "hamad";
    try {
//      PathResolver.resolveSpellIndexPath(Optional.of(Translator.YousufAli))));
      Optional<Translator> translatorOptional = Optional.of(Translator.YousufAli);
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

  // Private world
  private void createIndex(Optional<Translator> translatorOptional, String sourceFilePath){
    try {
      List<Quran> quranList = parseTextForQuran(sourceFilePath);

      Directory dir = FSDirectory.open(new File(PathResolver.resolveIndexPath(translatorOptional)));
      Analyzer analyzer = AnalyzerRegistry.getAnalyzer(LocaleEnum.Arabic);// For Original Quran.
      if (translatorOptional.isPresent()) {
        analyzer = AnalyzerRegistry.getAnalyzer(translatorOptional.get().getLocaleEnum());
      }

      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46, analyzer);
      iwc.setOpenMode(OpenMode.CREATE);
      IndexWriter writer = new IndexWriter(dir, iwc);

      for (Quran quran : quranList) {
        System.out.println("Indexing... "+quran.getAccmId()+" : "+quran.getAyahText());
        Document doc = new Document();
        doc.add(new TextField(QuranField.accumId.value(), String.valueOf(quran.getAccmId()), Store.YES)  );
        doc.add(new TextField(QuranField.ayahId.value(), String.valueOf(quran.getAyahId()), Store.YES) );
        doc.add(new TextField(QuranField.surahId.value(), String.valueOf(quran.getSurahId()), Store.YES) );
        doc.add(new TextField(QuranField.juzId.value(), String.valueOf(quran.getJuzId()), Store.YES) );
        doc.add(new TextField(QuranField.surahName.value(), quran.getSurahName(), Store.YES) );
        doc.add(new TextField(QuranField.juzName.value(), quran.getJuzName(), Store.YES));
        doc.add(new TextField(QuranField.ayahText.value(), quran.getAyahText(), Store.YES));
        writer.addDocument(doc);
      }
      writer.close();
      dir.close();

      //Generate spell check dictionary
      createSpellCheckIndex(translatorOptional);
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  private void createSpellCheckIndex(Optional<Translator> translatorOptional){
    try {
      Directory dir = FSDirectory.open(new File(PathResolver.resolveSpellIndexPath(translatorOptional)));
      SpellChecker spell = new SpellChecker(dir);
      IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(PathResolver.resolveIndexPath(
              translatorOptional
          ))));
      LuceneDictionary luceneDictionary = new LuceneDictionary(reader, QuranField.ayahText.value());
      Analyzer analyzer = AnalyzerRegistry.getAnalyzer(LocaleEnum.Arabic);// For Original Quran.
      if (translatorOptional.isPresent()) {
        analyzer = AnalyzerRegistry.getAnalyzer(translatorOptional.get().getLocaleEnum());
      }
      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46, analyzer);
      spell.indexDictionary(luceneDictionary, iwc, false);

      reader.close();
      dir.close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

  private List<Quran> parseTextForQuran(String filePath){
    List<Quran> quranList = Lists.newArrayList();

    try {
      ImmutableTable<Integer, Integer, Quran> quranMetadataTable = parseQuranMetadata();
      int surahId, ayahId;
      String ayahText;
      BufferedReader reader = new BufferedReader(new FileReader(filePath));
      String line = reader.readLine();

      int numberOfLineRead = 1;
      while (line != null && numberOfLineRead <= QURAN_AYAH_COUNT) {
        String[] split = line.split("\\|");
//        System.out.println("***Split***: "+line+"   -> :"+split);
        surahId = Integer.valueOf(split[0].trim());
        ayahId = Integer.valueOf(split[1].trim());
        ayahText = split[2].trim();
//        System.out.println(surahId+" : "+ayahId+" : "+ayahText);

        Quran quranMetadata = quranMetadataTable.get(surahId, ayahId);
        Quran quran = new Quran();
        quran.setAccmId(quranMetadata.getAccmId());
        quran.setSurahId(surahId);
        quran.setAyahId(ayahId);
        quran.setJuzId(quranMetadata.getJuzId());
        quran.setAyahText(ayahText);
        quran.setSurahName(quranMetadata.getSurahName());
        quran.setJuzName(quranMetadata.getJuzName());
        quranList.add(quran);

        line = reader.readLine();
        numberOfLineRead++;
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return quranList;
  }

  private ImmutableTable<Integer, Integer, Quran> parseQuranMetadata(){
    ImmutableTable.Builder<Integer, Integer, Quran> tableBuilder = ImmutableTable.builder();

    try {
      String accumId, ayahId, surahId,juzId, surahName, juzName;
      BufferedReader reader = new BufferedReader(new FileReader(QURAN_METADATA_FILE_PATH));
      String line = reader.readLine();

      while (line != null) {
        String[] split = line.split("\\|");
        surahId = split[0].trim();
        ayahId = split[1].trim();
        accumId = split[2].trim();
        surahName = split[3].trim();
        juzId = split[4].trim();
        juzName = split[5].trim();
//        System.out.println(surahId+" : "+ayahId+" : "+accumId+" : "+surahName+" : "+juzId+" : "+juzName);

        Quran quran = new Quran();
        quran.setAccmId(Integer.valueOf(accumId));
        quran.setAyahId(Integer.valueOf(ayahId));
        quran.setSurahId(Integer.valueOf(surahId));
        quran.setJuzId(Integer.valueOf(juzId));
        quran.setSurahName(surahName);
        quran.setJuzName(juzName);
        tableBuilder.put(quran.getSurahId(), quran.getAyahId(), quran);

        line = reader.readLine();
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tableBuilder.build();
  }

  @Test
  public void testParser(){
    List<Quran> quranList = parseTextForQuran("./db_scripts/quran_en_yousufali.txt");
    System.out.println(quranList.size());
  }
}
