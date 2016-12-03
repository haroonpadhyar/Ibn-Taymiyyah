package com.maktashaf.taymiyyah.repository.analysis.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.QuranField;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.util.PathResolver;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.repository.jdbc.QuranJDBCRepo;
import com.maktashaf.taymiyyah.repository.jdbc.QuranJDBCRepoImpl;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ar.ArabicCustomizedAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.en.EnglishPhoneticAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ur.UrduAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.search.AbstractQuranSearcher;
import com.maktashaf.taymiyyah.repository.lucene.search.QuranSearcher;
import com.maktashaf.taymiyyah.repository.lucene.search.QuranTextSearcher;
import com.maktashaf.taymiyyah.repository.lucene.search.QuranTranslationSearcher;
import com.maktashaf.taymiyyah.vo.SearchResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

/**
 * @author: Haroon
 */
public class IndexGenerator extends AbstractQuranSearcher{ //TODO need to develop path resolver API.then extend from that.
  QuranJDBCRepo quranJDBCRepo = new QuranJDBCRepoImpl();
  final String contextPath = "./index";

  @Test
  public void createIndexAr(){
    try {
      Directory dir = FSDirectory.open(new File(PathResolver.resolveIndexPath(Optional.<Translator>absent())));
      ArabicCustomizedAnalyzer arabicAnalyzer = new ArabicCustomizedAnalyzer(Version.LUCENE_46);
      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,arabicAnalyzer);
      iwc.setOpenMode(OpenMode.CREATE);
      IndexWriter writer = new IndexWriter(dir, iwc);

      // loading data from database.
//      List<Quran> qurans = quranJDBCRepo.findAll(LocaleEnum.Arabic);

      // load data from file.
      List<Quran> qurans = parseSQLData(LocaleEnum.Arabic);
      for (Quran quran : qurans) {
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
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  public void createIndexUr(){
    try {
      Directory dir = FSDirectory.open(new File(PathResolver.resolveIndexPath(Optional.of(Translator.Maududi))));
      Analyzer analyzer = new UrduAnalyzer(Version.LUCENE_46);

      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,analyzer);
      iwc.setOpenMode(OpenMode.CREATE); 
      IndexWriter writer = new IndexWriter(dir, iwc);

      // loading data from database.
//      List<Quran> qurans = quranJDBCRepo.findAll(LocaleEnum.Urdu);

      // load data from file.
      List<Quran> qurans = parseSQLData(LocaleEnum.Urdu);
      for (Quran quran : qurans) {
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
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  public void createIndexEn(){
    try {
      Directory dir = FSDirectory.open(new File(PathResolver.resolveIndexPath(Optional.of(Translator.YousufAli))));
      Analyzer analyzer = new EnglishPhoneticAnalyzer(Version.LUCENE_46);

      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,analyzer);
      iwc.setOpenMode(OpenMode.CREATE);
      IndexWriter writer = new IndexWriter(dir, iwc);

      // loading data from database.
//      List<Quran> qurans = quranJDBCRepo.findAll(LocaleEnum.English);

      // load data from file.
      List<Quran> qurans = parseSQLData(LocaleEnum.English);
      for (Quran quran : qurans) {
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
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  public void searchIndexAr(){
    try {
      String term = "قلوبنا";
//      term = "محمد";
//      term = "علي";
//      term = "على";

      SearchParam searchParam = SearchParam.builder()
          .withTerm(term)
          .withLocale(LocaleEnum.Arabic)
          .withOriginal(true)
          .withPageNo(1)
          .withPageSize(12)
          .build();
      QuranSearcher quranSearcher = new QuranTextSearcher();
      SearchResult searchResult = quranSearcher.search(searchParam);

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
  public void searchIndexUr(){
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
      QuranSearcher quranSearcher = new QuranTranslationSearcher();
      SearchResult searchResult = quranSearcher.search(searchParam);

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
  public void searchIndexEn(){
    try {
      String term = "Mohamad";
      SearchParam searchParam = SearchParam.builder()
          .withTerm(term)
          .withLocale(LocaleEnum.English)
          .withTranslator(Translator.YousufAli)
          .withOriginal(false)
          .withPageNo(1)
          .withPageSize(12)
          .build();
      QuranSearcher quranSearcher = new QuranTranslationSearcher();
      SearchResult searchResult = quranSearcher.search(searchParam);

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
  public void genericTest(){
    int a = 15;
    int b = 9;
    System.out.println(15/9);
    System.out.println(Math.ceil(15/9));
    System.out.println(Math.ceil((double)a/b));
  }

  @Test
  public void genFromSQL(){

    try {
      Directory dir = FSDirectory.open(new File(contextPath + File.separator + LocaleEnum.Arabic.value().getLanguage()));
      ArabicCustomizedAnalyzer arabicAnalyzer = new ArabicCustomizedAnalyzer(Version.LUCENE_46);
      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,arabicAnalyzer);
      iwc.setOpenMode(OpenMode.CREATE);
      IndexWriter writer = new IndexWriter(dir, iwc);

      // loading data from database.

      FileReader fileReader = new FileReader("/Users/haroonpadhyar/Personal/Work/Projects/Ibn-Taymiyyah/db_scripts/quran-original_book.sql");
//      fileReader.r

      List<Quran> qurans = quranJDBCRepo.findAll(LocaleEnum.Arabic);
      for (Quran quran : qurans) {
        System.out.println("Indexing... "+quran.getAccmId()+" : "+quran.getAyahText());
        Document doc = new Document();
        doc.add(new TextField(QuranField.accumId.value(), new StringReader(String.valueOf(quran.getAccmId()))) );
        doc.add(new TextField(QuranField.ayahId.value(), new StringReader(String.valueOf(quran.getAyahId()))) );
        doc.add(new TextField(QuranField.surahId.value(), new StringReader(String.valueOf(quran.getSurahId()))) );
        doc.add(new TextField(QuranField.juzId.value(), new StringReader(String.valueOf(quran.getJuzId()))) );
        doc.add(new TextField(QuranField.surahName.value(), new StringReader(quran.getSurahName())) );
        doc.add(new TextField(QuranField.juzName.value(), new StringReader(quran.getJuzName())));
        doc.add(new TextField(QuranField.ayahText.value(), new StringReader(quran.getAyahText())));
        writer.addDocument(doc);
      }

      writer.close();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  public void testParseSQLData(){
    List<Quran> quranList = parseSQLData(LocaleEnum.Urdu);
    System.out.println(quranList.size());
  }

  private List<Quran> parseSQLData(LocaleEnum locale){
    //TODO make all files in same format to use unified parser.
    List<Quran> quranList = new ArrayList<Quran>();
    String filePath = "";
    int start = 0;
    if(locale == LocaleEnum.Arabic) {
      filePath = "./db_scripts/quran-original_book.sql";
      start = 137;
    }
    else if(locale == LocaleEnum.Urdu) {
      filePath = "./db_scripts/quran_ur_maududi.sql";
      start = 148;
    }
    else if(locale == LocaleEnum.English) {
      filePath = "./db_scripts/quran_en_yousufali.txt";
      start = 150;
    }

    try {
      ImmutableMap<Integer, Quran> quranImmutableMap = null;
      if(locale != LocaleEnum.Arabic) {
        List<Quran> quranListTemp = parseSQLData(LocaleEnum.Arabic);
        quranImmutableMap = Maps.uniqueIndex(quranListTemp, new Function<Quran, Integer>() {
          @Override
          public Integer apply(Quran quran) {
            return quran.getAccmId();
          }
        });
      }
      String accumId, ayahId, surahId,juzId, surahName, juzName, ayahText;
      accumId = ayahId = surahId = juzId = surahName = juzName = ayahText = "";
      BufferedReader reader = new BufferedReader(new FileReader(filePath));
      String line = reader.readLine();

      int i = 0;
      while (line != null) {
        i++;
        if(locale == LocaleEnum.English) {
          String[] split = line.split("\\|");
          surahId = split[0].trim();
          ayahId = split[1].trim();
          ayahText = split[2].trim();
        }else {
          String substring = line.substring(start, line.length() - 2);
          String[] split = substring.split(",");
          accumId = split[0].trim();
          ayahId = split[1].trim();
          surahId = split[6].trim();
          juzId = split[8].trim();
          surahName = split[7].trim().substring(1, split[7].trim().length() - 1);
          juzName = split[9].trim().substring(1, split[9].trim().length() - 1);
          ayahText = split[2].trim().substring(1, split[2].trim().length() - 1);
        }

        if(locale != LocaleEnum.Arabic) {
          accumId = String.valueOf(quranImmutableMap.get(i).getAccmId());
          juzId = String.valueOf(quranImmutableMap.get(i).getJuzId());
          surahName = String.valueOf(quranImmutableMap.get(i).getSurahName());
          juzName = String.valueOf(quranImmutableMap.get(i).getJuzName());
        }

//        System.out.println(accumId + " " + ayahId + " " + surahId + " " + juzId + " " + surahName + " " + juzName + " " + ayahText);
        Quran quran = new Quran();
        quran.setAccmId(Integer.valueOf(accumId));
        quran.setAyahId(Integer.valueOf(ayahId));
        quran.setSurahId(Integer.valueOf(surahId));
        quran.setJuzId(Integer.valueOf(juzId));
        quran.setAyahText(ayahText);
        quran.setSurahName(surahName);
        quran.setJuzName(juzName);
        quranList.add(quran);

        line = reader.readLine();
      }

      reader.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

    return quranList;
  }

  @Override
  protected String resolveIndexPath(SearchParam searchParam) {
    return null;
  }

  @Override
  protected String resolveSpellIndexPath(SearchParam searchParam) {
    return null;
  }

  @Override
  protected String getSearchedTextFromField(Quran quran) {
    return null;
  }

  @Override
  protected void setSearchedTextInField(Quran quran, String text) {

  }

  @Override
  protected void setUnSearchedTextInField(SearchParam searchParam, List<Quran> quranList) {

  }

  @Override
  protected Analyzer chooseAnalyzer(SearchParam searchParam) {
    return null;
  }
}
