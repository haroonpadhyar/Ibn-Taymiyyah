package com.maktashaf.taymiyyah.analysis.generator;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.QuranField;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.util.PathResolver;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.repository.lucene.analysis.AnalyzerRegistry;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * * @author Haroon Anwar Padhyar
 */
public class IndexGenerator {
  private static final String QURAN_METADATA_FILE_PATH = "./data/Quran_metadata.txt";
  private static final int QURAN_AYAH_COUNT = 6236;

  protected void createIndex(Optional<Translator> translatorOptional, String sourceFilePath){
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
  @Ignore
  public void testParser(){
    List<Quran> quranList = parseTextForQuran("./db_scripts/quran_en_yousufali.txt");
    System.out.println(quranList.size());
  }
}
