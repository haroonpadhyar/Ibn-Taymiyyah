package com.maktashaf.taymiyyah.repository.analysis.generator;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.List;

import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.QuranField;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.repository.jdbc.QuranJDBCRepo;
import com.maktashaf.taymiyyah.repository.jdbc.QuranJDBCRepoImpl;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ar.ArabicCustomizedAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.en.EnglishPhoneticAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ur.UrduAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.search.QuranSearcher;
import com.maktashaf.taymiyyah.repository.lucene.search.QuranTextSearcher;
import com.maktashaf.taymiyyah.vo.SearchResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
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
public class IndexGenerator {
  QuranJDBCRepo quranJDBCRepo = new QuranJDBCRepoImpl();
  final String contextPath = "./index";

  @Test
  public void createIndexAr(){
    try {
      Directory dir = FSDirectory.open(new File(contextPath + File.separator + LocaleEnum.Ar.value().getLanguage()));
      ArabicCustomizedAnalyzer arabicAnalyzer = new ArabicCustomizedAnalyzer(Version.LUCENE_46);
      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,arabicAnalyzer);
      iwc.setOpenMode(OpenMode.CREATE);
      IndexWriter writer = new IndexWriter(dir, iwc);

      // loading data from database.
      List<Quran> qurans = quranJDBCRepo.findAll(LocaleEnum.Ar);
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
  public void createIndexUr(){
    try {
      Directory dir = FSDirectory.open(new File(contextPath + File.separator + LocaleEnum.Ur.value().getLanguage()));
      Analyzer analyzer = new UrduAnalyzer(Version.LUCENE_46);

      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,analyzer);
      iwc.setOpenMode(OpenMode.CREATE); 
      IndexWriter writer = new IndexWriter(dir, iwc);

      // loading data from database.
      List<Quran> qurans = quranJDBCRepo.findAll(LocaleEnum.Ur);
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
  public void createIndexEn(){
    try {
      Directory dir = FSDirectory.open(new File(contextPath + File.separator + LocaleEnum.En.value().getLanguage()));
      Analyzer analyzer = new EnglishPhoneticAnalyzer(Version.LUCENE_46);

      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,analyzer);
      iwc.setOpenMode(OpenMode.CREATE);
      IndexWriter writer = new IndexWriter(dir, iwc);

      // loading data from database.
      List<Quran> qurans = quranJDBCRepo.findAll(LocaleEnum.En);
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
  public void searchIndexAr(){
    try {
      String term = "قلوبنا";
//      term = "محمد";
//      term = "علي";
//      term = "على";

      SearchParam searchParam = SearchParam.builder()
          .withContextPath(contextPath)
          .withTerm(term)
          .withLocale(LocaleEnum.Ar)
          .withTranslator(Translator.None)
          .withOriginal(true)
          .withPageNo(1)
          .withPageSize(2)
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
          .withContextPath(contextPath)
          .withTerm(term)
          .withLocale(LocaleEnum.Ur)
          .withTranslator(Translator.Maududi)
          .withOriginal(false)
          .withPageNo(3)
          .withPageSize(2)
          .build();
      QuranSearcher quranSearcher = new QuranTextSearcher();
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
          .withContextPath(contextPath)
          .withTerm(term)
          .withLocale(LocaleEnum.En)
          .withTranslator(Translator.YousufAli)
          .withOriginal(false)
          .withPageNo(2)
          .withPageSize(2)
          .build();
      QuranSearcher quranSearcher = new QuranTextSearcher();
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
      Directory dir = FSDirectory.open(new File(contextPath + File.separator + LocaleEnum.Ar.value().getLanguage()));
      ArabicCustomizedAnalyzer arabicAnalyzer = new ArabicCustomizedAnalyzer(Version.LUCENE_46);
      IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,arabicAnalyzer);
      iwc.setOpenMode(OpenMode.CREATE);
      IndexWriter writer = new IndexWriter(dir, iwc);

      // loading data from database.

      FileReader fileReader = new FileReader("/Users/haroonpadhyar/Personal/Work/Projects/Ibn-Taymiyyah/db_scripts/quran-original_book.sql");
//      fileReader.r

      List<Quran> qurans = quranJDBCRepo.findAll(LocaleEnum.Ar);
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
}
