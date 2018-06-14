package com.maktashaf.taymiyyah.analysis;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;

import com.google.common.base.Optional;
import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.ProjectConstant;
import com.maktashaf.taymiyyah.common.QuranField;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.util.PathResolver;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ar.ArabicCustomizedAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.en.EnglishPhoneticAnalyzer;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ur.UrduAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.LevenshteinDistance;
import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

/**
 * @author: Haroon Anwar Padhyar.
 */
public class AnalyzerDemo {
  final static String contextPath = "./index";
  final static String spellCheck = ProjectConstant.SPELL_CHECK_DIR;
  final static String spellPath = contextPath + File.separator + spellCheck + File.separator + LocaleEnum.Arabic.value()
      .getLanguage();
  final static String indexPath = contextPath + File.separator + LocaleEnum.Arabic.value().getLanguage();


  @Test
  public void analyze() throws IOException {

  // Analyzers
    Analyzer[] analyzers = new Analyzer[] {
//      new WhitespaceAnalyzer(),
//      new SimpleAnalyzer(),
//      new StopAnalyzer(Version.LUCENE_31),
//      new StandardAnalyzer(Version.LUCENE_31)
//      new ArabicAnalyzer(),
//      new ArabicCustomizedAnalyzer(),
//      new UrduAnalyzer(),
        new EnglishPhoneticAnalyzer(),
        new EnglishPhoneticAnalyzer(true)
    };

    // Text to analyze
    String[] examples = {
        "Muhammad muhamd mohamd Mohmd, ali, ahmed",
        "The quick brown fox jumped over the lazy dogs",
        "XY&Z Corporation - xyz@example.com",
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
//      "یحیی يحيي ىحىى "
//      "زکوة زکوۃ زکوة "
//     "زکوہ زکوۃ "
//      "ہارون ھارون حارون هارون "
    };

    for (int j = 0; j < examples.length; j++) {
      String text = examples[j];
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
  }

  @Test
  public void createSpellCheckIndex() {
    try {
      Directory dir = FSDirectory.open(Paths.get(spellPath));
      SpellChecker spell = new SpellChecker(dir);
      IndexReader r = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
      LuceneDictionary luceneDictionary = new LuceneDictionary(r, QuranField.ayahText.value());
      IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new ArabicCustomizedAnalyzer());
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

  @Test
  public void doSpellCheck() {
    String term = "مُحَمَّدٌ";
    term = "ہارون";
//    term = "هارون";
//    term = "هار";
//    term = "محد";
//    term = "صدری";
//    term = "محمد صدری";
//    term = "OR";
    try {
      Directory dir = FSDirectory.open(Paths.get(spellPath));
      SpellChecker spell = new SpellChecker(dir);
      spell.setStringDistance(new LevenshteinDistance());

      System.out.println("Exist: " + spell.exist(term));
      String[] suggestions = spell.suggestSimilar(term, 150);
      System.out.println("suggestions: " + suggestions.length);
      for (String suggestion : suggestions) {
        System.out.println(suggestion);
      }


      dir.close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void doSpellCheck1() {
    String term = "muhmad";
    try {
      Directory dir = FSDirectory.open(Paths.get(PathResolver.resolveSpellIndexPath(Optional.of(Translator.English_YousufAli))));
      SpellChecker spell = new SpellChecker(dir);
      spell.setStringDistance(new LevenshteinDistance());

      System.out.println("Exist: " + spell.exist(term));
      String[] suggestions = spell.suggestSimilar(term, 150);
      System.out.println("suggestions: " + suggestions.length);
      for (String suggestion : suggestions) {
        System.out.println(suggestion);
      }

      System.out.println("*******************************");
      String s = term;
      EnglishPhoneticAnalyzer analyzer = new EnglishPhoneticAnalyzer(true);
      TokenStream stream = analyzer.tokenStream("contents", new StringReader(s));
      stream.reset();
      if (stream.incrementToken()) {
        CharTermAttribute charTermAttribute = stream.addAttribute(CharTermAttribute.class);
        suggestions = spell.suggestSimilar(charTermAttribute.toString(), 150);
        for (String suggestion : suggestions) {
          System.out.println(suggestion);
        }
      }


      dir.close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void doSpellCheck2() {
    String term = "مُحَمَّدٌ";
//    term = "ہارون";
//    term = "هارون";
//    term = "هار";
//    term = "محد";
//    term = "صدری";
//    term = "محمد صدری";
//    term = "OR";
    try {
      Directory dir = FSDirectory.open(Paths.get(PathResolver.resolveSpellIndexPath(Optional.of(Translator.Urdu_Maududi))));
      SpellChecker spell = new SpellChecker(dir);
      spell.setStringDistance(new LevensteinDistance());

      System.out.println("Exist: " + spell.exist(term));
      String[] suggestions = spell.suggestSimilar(term, 150);
      System.out.println("suggestions: " + suggestions.length);
      for (String suggestion : suggestions) {
        System.out.println(suggestion);
      }

      System.out.println("*******************************");
      String s = term;
      UrduAnalyzer analyzer = new UrduAnalyzer();
      TokenStream stream = analyzer.tokenStream("contents", new StringReader(s));
      stream.reset();
      if (stream.incrementToken()) {
        CharTermAttribute charTermAttribute = stream.addAttribute(CharTermAttribute.class);
        suggestions = spell.suggestSimilar(charTermAttribute.toString(), 150);
        for (String suggestion : suggestions) {
          System.out.println(suggestion);
        }
      }


      dir.close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }
}
