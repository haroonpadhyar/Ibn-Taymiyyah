package com.maktashaf.taymiyyah.repository.lucene.analysis.ur.filter;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Filter diacritics from arabic text.
 *
 * @author: Haroon Anwar Padhyar.
 */
public final class UrduSoundAlikeFilter extends TokenFilter {
  private final UrduSoundAlikeNormalizer normalizer = new UrduSoundAlikeNormalizer();
  private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

  public UrduSoundAlikeFilter(TokenStream input) {
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
