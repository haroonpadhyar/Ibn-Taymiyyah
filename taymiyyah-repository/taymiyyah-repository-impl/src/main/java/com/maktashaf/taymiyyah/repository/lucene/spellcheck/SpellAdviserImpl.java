package com.maktashaf.taymiyyah.repository.lucene.spellcheck;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.ProjectConstant;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.repository.lucene.analysis.AnalyzerRegistry;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author: Haroon Anwar Padhyar.
 */
public class SpellAdviserImpl implements SpellAdviser {
  private static Logger logger = Logger.getLogger(SpellAdviserImpl.class);

  @Override
  public String suggest(String text, String spellIndexPath, Analyzer analyzer){
    String suggestedTerm = "";
    try {
      Directory dir = FSDirectory.open(new File(spellIndexPath));
      SpellChecker spell = new SpellChecker(dir);
      spell.setStringDistance(new LevensteinDistance());

      String[] split = text.split(" ");
      boolean suggestionFound = false;
      for (int i = 0; i < split.length; i++) {
        String s = split[i];
        // To skip stop words use Analyser.
        TokenStream stream = analyzer.tokenStream("contents", new StringReader(s));
        stream.reset();
        if (stream.incrementToken()){
          String[] suggestions = spell.suggestSimilar(s, 1);
          if(suggestions.length > 0)  {
            split[i] = suggestions[0];
            suggestionFound = true;
          }
        }
        stream.close();
      }

      if(suggestionFound)
        suggestedTerm = StringUtils.join(split, ' ');

      spell.close();
      dir.close();
    }
    catch(IOException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    }
    return suggestedTerm;
  }

  private Analyzer chooseAnalyzer(SearchParam searchParam){
    Analyzer analyzer = null;
    if(searchParam.isOriginal())
      analyzer = AnalyzerRegistry.getAnalyzer(LocaleEnum.Original, Translator.None);
    else
      analyzer = AnalyzerRegistry.getAnalyzer(searchParam.getLocaleEnum(), searchParam.getTranslator());
    return analyzer;
  }
}
