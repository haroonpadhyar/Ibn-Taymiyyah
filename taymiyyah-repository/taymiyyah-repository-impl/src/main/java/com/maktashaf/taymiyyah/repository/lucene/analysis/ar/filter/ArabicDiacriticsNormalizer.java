package com.maktashaf.taymiyyah.repository.lucene.analysis.ar.filter;

import static org.apache.lucene.analysis.util.StemmerUtil.delete;

/**
 * Normalize diacritics.
 *
 * @author: Haroon Anwar Padhyar.
 */
public class ArabicDiacriticsNormalizer {

  public static final char TATWEEL = '\u0640';
  public static final char FATHATAN = '\u064B';
  public static final char DAMMATAN = '\u064C';
  public static final char KASRATAN = '\u064D';
  public static final char FATHA = '\u064E';
  public static final char DAMMA = '\u064F';
  public static final char KASRA = '\u0650';
  public static final char SHADDA = '\u0651';
  public static final char SUKUN = '\u0652';
  public static final char SMALL_HIGH_ZAIN = '\u0617';
  public static final char SMALL_FATHA = '\u0618';
  public static final char SMALL_DAMMA = '\u0619';
  public static final char SMALL_KASRA = '\u061A';
  public static final char MADDAH_ABOVE = '\u0653';
  public static final char HAMZA_ABOVE = '\u0654';
  public static final char HAMZA_BELOW = '\u0655';
  public static final char SUBSCRIPT_ALEF = '\u0656';
  public static final char SUPERSCRIPT_ALEF = '\u0670';

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
        case TATWEEL:
        case KASRATAN:
        case DAMMATAN:
        case FATHATAN:
        case FATHA:
        case DAMMA:
        case KASRA:
        case SHADDA:
        case SUKUN:
        case SMALL_HIGH_ZAIN:
        case SMALL_FATHA:
        case SMALL_DAMMA:
        case SMALL_KASRA:
        case MADDAH_ABOVE:
        case HAMZA_ABOVE:
        case HAMZA_BELOW:
        case SUBSCRIPT_ALEF:
        case SUPERSCRIPT_ALEF:
          len = delete(s, i, len);
          i--;
          break;
        default:
          break;
      }
    }

    return len;
  }
}
