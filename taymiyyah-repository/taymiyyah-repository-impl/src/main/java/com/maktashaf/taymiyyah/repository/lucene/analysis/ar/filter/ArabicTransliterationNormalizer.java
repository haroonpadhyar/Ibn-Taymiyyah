package com.maktashaf.taymiyyah.repository.lucene.analysis.ar.filter;

/**
 * Transliterate the alphabets of non-Arabic languages having arabic script
 * like (Urdu, Persia etc.) to closer Arabic language alphabet.
 *
 * Only those letters should be transliterate that have same visual shape
 * and sound but have different code point.e.g Urdu choti yeh ی '\u06CC' to
 * Arabic dot-less yeh ى '\u0649'
 *
 * @author: Haroon Anwar Padhyar.
 */
public class ArabicTransliterationNormalizer {

  public static final char CHOTI_YEH = '\u06CC';
  public static final char BARI_YEH = '\u06D2'; //  ے
  public static final char BARI_YEH_WITH_HAMZA_ABOVE = '\u06D3';
  public static final char DOTLESS_YEH = '\u0649';
  public static final char HEH = '\u0647';
  public static final char CHOTI_HEH = '\u06C1';
  public static final char DO_CHASHMI_HEH = '\u06BE';

  public static final char HEH_WITH_YEH_ABOVE = '\u06C0';
  public static final char HEH_GOAL_WITH_HAMZA_ABOVE = '\u06C2';
  public static final char TEH_MARBUTA_GOAL = '\u06C3';


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
        case KAF_UR:
          s[i] = KAF_AR;
          break;
        case CHOTI_YEH:
        case BARI_YEH:
        case BARI_YEH_WITH_HAMZA_ABOVE:
          s[i] = DOTLESS_YEH;
          break;
        case CHOTI_HEH:
        case DO_CHASHMI_HEH:
        case HEH_WITH_YEH_ABOVE:
        case HEH_GOAL_WITH_HAMZA_ABOVE:
        case TEH_MARBUTA_GOAL:
          s[i] = HEH;
          break;
        default:
          break;
      }
    }

    return len;
  }
}
