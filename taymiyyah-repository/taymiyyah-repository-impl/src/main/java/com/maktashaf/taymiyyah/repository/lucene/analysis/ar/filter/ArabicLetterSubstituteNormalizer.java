package com.maktashaf.taymiyyah.repository.lucene.analysis.ar.filter;

/**
 * Letter Substitute Normalizer for Arabic.
 *
 *  Normalization is defined as:
 *  <ul>
 *  <li> Normalization of WAW_WITH_HAMZA with WAW and so on.
 * </ul>
 *
 * @author: Haroon Anwar Padhyar.
 */
public class ArabicLetterSubstituteNormalizer {
  public static final char WAW = '\u0648'; //  و
  public static final char WAW_WITH_HAMZA = '\u0624'; //  ؤ
  public static final char YEH = '\u064A'; //  ي
  public static final char YEH_WITH_HAMZA = '\u0626'; // ئ


  /**
   * Normalize an input buffer of Arabic text
   *
   * @param s input buffer
   * @param len length of input buffer
   * @return length of input buffer after normalization
   */
  public int normalize(char s[], int len) {

    for (int i = 0; i < len; i++) {
      switch (s[i]) {
        case WAW_WITH_HAMZA:
          s[i] = WAW;
          break;
        case YEH_WITH_HAMZA:
          s[i] = YEH;
          break;
        default:
          break;
      }
    }

    return len;
  }
}
