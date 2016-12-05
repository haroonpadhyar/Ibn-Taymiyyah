package com.maktashaf.taymiyyah.repository.lucene.analysis.ur.filter;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Filter substitute letter from urdu text.
 *
 * @author Haroon Anwar Padhyar.
 */
public final class UrduLetterSubstituteFilter extends TokenFilter {
  private final UrduLetterSubstituteNormalizer normalizer = new UrduLetterSubstituteNormalizer();
  private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

  public UrduLetterSubstituteFilter(TokenStream input) {
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
