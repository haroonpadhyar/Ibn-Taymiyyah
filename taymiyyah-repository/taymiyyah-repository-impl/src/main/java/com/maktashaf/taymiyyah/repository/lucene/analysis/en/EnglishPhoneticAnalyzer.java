package com.maktashaf.taymiyyah.repository.lucene.analysis.en;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.phonetic.PhoneticFilterFactory;
import org.apache.solr.core.SolrResourceLoader;

/**
 * Phonetic analyzer for English language.
 *
 * @author Haroon Anwar Padhyar.
 */
public class EnglishPhoneticAnalyzer extends StopwordAnalyzerBase {
  public EnglishPhoneticAnalyzer(Version matchVersion) {
    super(matchVersion, StandardAnalyzer.STOP_WORDS_SET);
  }

  @Override
  protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
    final Tokenizer source = new StandardTokenizer(matchVersion, reader);
    TokenStream result = new StandardFilter(matchVersion, source);
    result = new EnglishPossessiveFilter(matchVersion, result);
    result = new LowerCaseFilter(matchVersion, result);
    result = new StopFilter(matchVersion, result, stopwords);
    result = new PorterStemFilter(result);

    Map<String, String> map = new HashMap<String, String>();
    map.put(PhoneticFilterFactory.ENCODER, "METAPHONE");
    PhoneticFilterFactory phoneticFilterFactory = new PhoneticFilterFactory(map);
    try {
      phoneticFilterFactory.inform(new SolrResourceLoader(""));
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    result = phoneticFilterFactory.create(result);

    return new TokenStreamComponents(source, result);
  }
}
