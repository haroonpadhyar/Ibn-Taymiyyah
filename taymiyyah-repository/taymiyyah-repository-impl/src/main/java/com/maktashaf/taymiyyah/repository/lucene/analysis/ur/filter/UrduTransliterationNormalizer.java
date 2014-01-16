package com.maktashaf.taymiyyah.repository.lucene.analysis.ur.filter;

/**
 * Transliterate the alphabets of non-Urdu languages having arabic script
 * like (Arabic, Persia etc.) to closer Urdu language alphabet.
 *
 * Only those letters should be transliterate that have same visual shape
 * and sound but have different code point.e.g
 * Arabic dot-less yeh ى '\u0649' to Urdu choti yeh ی '\u06CC'.
 *
 * @author: Haroon Anwar Padhyar.
 */
public class UrduTransliterationNormalizer {

  public static final char CHOTI_YEH = '\u06CC';
  public static final char DOTLESS_YEH = '\u0649';
  public static final char HEH = '\u0647';
  public static final char TEH_MARBUTA = '\u0629';
  public static final char CHOTI_HEH = '\u06C1';
  public static final char YEH = '\u064A';


  public static final char KAF_AR = '\u0643';
  public static final char KAF_UR = '\u06A9';


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
        case KAF_AR:
          s[i] = KAF_UR;
          break;
        case DOTLESS_YEH:
        case YEH:
          s[i] = CHOTI_YEH;
          break;
        case HEH:
        case TEH_MARBUTA:
          s[i] = CHOTI_HEH;
          break;
        default:
          break;
      }
    }

    return len;
  }
}
