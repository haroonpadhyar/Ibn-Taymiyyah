package com.maktashaf.taymiyyah.repository.lucene.analysis.en;

import org.apache.commons.codec.language.Metaphone;
import org.apache.lucene.analysis.StopwordAnalyzerBase;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.phonetic.PhoneticFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * Phonetic analyzer for English language.
 *
 * @author Haroon Anwar Padhyar.
 */
public class EnglishPhoneticAnalyzer extends StopwordAnalyzerBase {
  private boolean isForDictionary;

  public EnglishPhoneticAnalyzer() {
    super(StandardAnalyzer.STOP_WORDS_SET);
  }

  public EnglishPhoneticAnalyzer(boolean isForDictionary) {
    super(StandardAnalyzer.STOP_WORDS_SET);
    this.isForDictionary = isForDictionary;
  }

  @Override
  protected TokenStreamComponents createComponents(String fieldName) {
    final Tokenizer source = new StandardTokenizer();
    TokenStream result = new StandardFilter(source);
    result = new EnglishPossessiveFilter(result);
    result = new LowerCaseFilter(result);
    result = new StopFilter(result, stopwords);
    if(!isForDictionary) {
      result = new PorterStemFilter(result);
      result = new PhoneticFilter(result, new Metaphone(), false);
    }

    return new TokenStreamComponents(source, result);
  }
}
