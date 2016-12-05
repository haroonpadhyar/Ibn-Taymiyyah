package com.maktashaf.taymiyyah.repository.lucene.analysis.ur;

import java.io.IOException;
import java.io.Reader;

import com.maktashaf.taymiyyah.repository.lucene.analysis.ar.filter.ArabicDiacriticsFilter;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ar.filter.ArabicExtendedNormalizationFilter;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ur.filter.UrduLetterSubstituteFilter;
import com.maktashaf.taymiyyah.repository.lucene.analysis.ur.filter.UrduTransliterationFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.miscellaneous.SetKeywordMarkerFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;

/**
 * Urdu text analyzer.
 *
 * @author Haroon Anwar Padhyar.
 */
public class UrduAnalyzer extends StopwordAnalyzerBase {

  /**
   * File containing default Arabic stopwords.
   *
   * Default stopword list is from http://members.unine.ch/jacques.savoy/clef/index.html
   * The stopword list is BSD-Licensed.
   */
  public final static String DEFAULT_STOPWORD_FILE = "stopwords.txt";
  public final static String DEFAULT_STEM_EXCLUSION_FILE = "stemExclusionSet.txt";

  /**
   * Returns an unmodifiable instance of the default stop-words set.
   * @return an unmodifiable instance of the default stop-words set.
   */
  public static CharArraySet getDefaultStopSet(){
    return DefaultSetHolder.DEFAULT_STOP_SET;
  }

  /**
   * Atomically loads the DEFAULT_STOP_SET in a lazy fashion once the outer class
   * accesses the static final set the first time.;
   */
  private static class DefaultSetHolder {
    static final CharArraySet DEFAULT_STOP_SET;
    static final CharArraySet DEFAULT_STEM_EXCLUSION_SET;

    static {
      try {
        DEFAULT_STOP_SET = loadStopwordSet(false, UrduAnalyzer.class, DEFAULT_STOPWORD_FILE, "#");
        DEFAULT_STEM_EXCLUSION_SET = loadStopwordSet(false, UrduAnalyzer.class, DEFAULT_STEM_EXCLUSION_FILE, "#");
      } catch (IOException ex) {
        // default set should always be present as it is part of the
        // distribution (JAR)
        throw new RuntimeException("Unable to load default stopword set");
      }
    }
  }

  private final CharArraySet stemExclusionSet;

  /**
   * Builds an analyzer with the default stop words: {@link #DEFAULT_STOPWORD_FILE}.
   */
  public UrduAnalyzer(Version matchVersion) {
    this(matchVersion, DefaultSetHolder.DEFAULT_STOP_SET);
  }

  /**
   * Builds an analyzer with the given stop words
   *
   * @param matchVersion
   *          lucene compatibility version
   * @param stopwords
   *          a stopword set
   */
  public UrduAnalyzer(Version matchVersion, CharArraySet stopwords){
    this(matchVersion, stopwords, CharArraySet.EMPTY_SET);
  }

  /**
   * Builds an analyzer with the given stop word. If a none-empty stem exclusion set is
   * provided this analyzer will add a {@link org.apache.lucene.analysis.miscellaneous.SetKeywordMarkerFilter} before
   * {@link org.apache.lucene.analysis.ar.ArabicStemFilter}.
   *
   * @param matchVersion
   *          lucene compatibility version
   * @param stopwords
   *          a stopword set
   * @param stemExclusionSet
   *          a set of terms not to be stemmed
   */
  public UrduAnalyzer(Version matchVersion, CharArraySet stopwords, CharArraySet stemExclusionSet){
    super(matchVersion, stopwords);
    if(!stemExclusionSet.isEmpty()) {
      this.stemExclusionSet = CharArraySet.unmodifiableSet(CharArraySet.copy(
          matchVersion, stemExclusionSet));
    }else
      this.stemExclusionSet = DefaultSetHolder.DEFAULT_STEM_EXCLUSION_SET;
  }

  @Override
  protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
    final Tokenizer source = new StandardTokenizer(matchVersion, reader);
    TokenStream result = new LowerCaseFilter(matchVersion, source);
    result = new UrduTransliterationFilter(result);
    result = new ArabicDiacriticsFilter(result);
    result = new StopFilter( matchVersion, result, stopwords); // TODO find more urdu stop words.
    result = new ArabicExtendedNormalizationFilter(result);
    if(!stemExclusionSet.isEmpty()) {
      result = new SetKeywordMarkerFilter(result, stemExclusionSet);
    }
//    result = new ArabicStemFilter(result); //TODO Urdu stem Filter.
    result = new UrduLetterSubstituteFilter(result);
    return new TokenStreamComponents(source, result);
  }
}
