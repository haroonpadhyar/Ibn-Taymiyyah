package com.maktashaf.taymiyyah.repository.lucene.analysis.ar.filter;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Filter diacritics from arabic text.
 *
 * @author: Haroon Anwar Padhyar.
 */
public final class ArabicLetterSubstituteFilter extends TokenFilter {
  private final ArabicLetterSubstituteNormalizer normalizer = new ArabicLetterSubstituteNormalizer();
  private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

  public ArabicLetterSubstituteFilter(TokenStream input) {
    super(input);
  }

  @Override
  public final boolean incrementToken() throws IOException {
    if (input.incrementToken()) {
      int newLen = normalizer.normalize(termAtt.buffer(), termAtt.length());
      termAtt.setLength(newLen);
      return true;
    }
    return false;
  }
}
