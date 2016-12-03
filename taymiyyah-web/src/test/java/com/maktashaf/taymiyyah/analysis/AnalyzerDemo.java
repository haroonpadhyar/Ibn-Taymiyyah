package com.maktashaf.taymiyyah.analysis;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Optional;
import com.maktashaf.taymiyyah.common.ProjectConstant;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.util.PathResolver;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ar.ArabicCustomizedAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ur.UrduAnalyzer;
import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.QuranField;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ar.ArabicAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * @author: Haroon Anwar Padhyar.
 */
public class AnalyzerDemo {
  final static String contextPath = "./index";
  final static String spellCheck = ProjectConstant.SPELL_CHECK_DIR;
  final static String spellPath = contextPath+File.separator+spellCheck+File.separator+LocaleEnum.Arabic.value().getLanguage();
  final static String indexPath = contextPath+File.separator+ LocaleEnum.Arabic.value().getLanguage();

  private static final String[] examples = {
//      "The quick brown fox jumped over the lazy dogs",
//      "XY&Z Corporation - xyz@example.com",
//            "قلوبنا", "علي", "على",
//      "اے محمدؐ، تم" ,
//      "محمدؐ"
//      "اللَّهَ"
//      "قد صدري"
//      "مُحَمَّدٌ"
//      " صَدْرِي"
//      " صدري"
//      " صدری"
//      "جهاد"
//      "الَّذِينَ "
//      "عَلىَ "
//      "موسی "
//      "الیسع اور ذوالکفلؑ کا ذکر کرو"
//      "کو "
//      "زکوٰۃ "
      "یحیی يحيي ىحىى "
//      "زکوة زکوۃ زکوة "
//     "زکوہ زکوۃ "
//      "ہارون ھارون حارون هارون "
  };
  private static final Analyzer[] analyzers = new Analyzer[] {
//      new WhitespaceAnalyzer(),
//      new SimpleAnalyzer(),
//      new StopAnalyzer(Version.LUCENE_31),
//      new StandardAnalyzer(Version.LUCENE_31)
      new ArabicAnalyzer(Version.LUCENE_46),
      new ArabicCustomizedAnalyzer(Version.LUCENE_46),
      new UrduAnalyzer(Version.LUCENE_46)
  };
  public static void main(String[] args) throws IOException {

//    createSpellCheckIndex();
//    String[] split = "as".split(" ");
//    System.out.println(StringUtils.join(split, ' '));
    doSpellCheck1();
//    System.out.println("Done.");
//    System.exit(1);

   /* String[] strings = examples;
    if (args.length > 0) {
      strings = args;
    }
    for (int i = 0; i < strings.length; i++) {
      analyze(strings[i]);
    }*/

  }

  private static void analyze(String text) throws IOException {

    System.out.println("Analyzing \"" + text + "\"");
    for (int i = 0; i < analyzers.length; i++) {
      Analyzer analyzer = analyzers[i];
      String name = analyzer.getClass().getName();
      name = name.substring(name.lastIndexOf(".") + 1);
      System.out.println("  " + name + ":");
      System.out.print("    ");
      AnalyzerUtils.displayTokens(analyzer, text);
      System.out.println("\n");
    }
  }

  public static void createSpellCheckIndex(){
    try {
      Directory dir = FSDirectory.open(new File(spellPath));
      SpellChecker spell = new SpellChecker(dir);
      IndexReader r = DirectoryReader.open(FSDirectory.open(new File(indexPath)));
      LuceneDictionary luceneDictionary = new LuceneDictionary(r, QuranField.ayahText.value());
      IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
          Version.LUCENE_46,
          new ArabicCustomizedAnalyzer(Version.LUCENE_46)
      );
      spell.indexDictionary(
          luceneDictionary,
          indexWriterConfig, false
      );

      r.close();
      dir.close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

  public static void doSpellCheck(){
    String term = "مُحَمَّدٌ";
    term = "ہارون";
//    term = "هارون";
//    term = "هار";
//    term = "محد";
//    term = "صدری";
//    term = "محمد صدری";
//    term = "OR";
    try {
      Directory dir = FSDirectory.open(new File(spellPath));
      SpellChecker spell = new SpellChecker(dir);
      spell.setStringDistance(new LevensteinDistance());

      System.out.println("Exist: "+spell.exist(term));
      String[] suggestions = spell.suggestSimilar(term, 150);
      System.out.println("suggestions: "+suggestions.length);
      for (String suggestion : suggestions) {
        System.out.println(suggestion);
      }



      dir.close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

  public static void doSpellCheck1(){
    String term = "muhmad";
    try {
      Directory dir = FSDirectory.open(new File(PathResolver.resolveSpellIndexPath(Optional.of(Translator.YousufAli))));
      SpellChecker spell = new SpellChecker(dir);
      spell.setStringDistance(new LevensteinDistance());

      System.out.println("Exist: "+spell.exist(term));
      String[] suggestions = spell.suggestSimilar(term, 150);
      System.out.println("suggestions: "+suggestions.length);
      for (String suggestion : suggestions) {
        System.out.println(suggestion);
      }



      dir.close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
}
