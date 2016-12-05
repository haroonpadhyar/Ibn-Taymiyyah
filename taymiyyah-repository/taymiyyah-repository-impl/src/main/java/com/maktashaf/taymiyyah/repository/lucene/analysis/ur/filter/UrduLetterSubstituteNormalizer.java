package com.maktashaf.taymiyyah.repository.lucene.analysis.ur.filter;

/**
 * Letter Letter Substitute Normalizer for Urdu.
 *
 *  Normalization is defined as:
 *  <ul>
 *  <li> Normalization of DO_CHASHMI_HEH with CHOTI_HEH and so on.
 * </ul>
 *
 * @author Haroon Anwar Padhyar.
 */
public class UrduLetterSubstituteNormalizer {

  public static final char CHOTI_HEH = '\u06C1'; //  ہ
  public static final char CHOTI_YEH = '\u06CC'; //  ی
  public static final char BARI_YEH = '\u06D2'; //  ے
  public static final char BARI_YEH_WITH_HAMZA_ABOVE = '\u06D3';
  public static final char DO_CHASHMI_HEH = '\u06BE'; //  ھ
  public static final char WAW = '\u0648'; //  و
  public static final char WAW_WITH_HAMZA = '\u0624'; //  ؤ
  public static final char YEH_WITH_HAMZA = '\u0626'; // ئ

  public static final char HEH_WITH_YEH_ABOVE = '\u06C0';
  public static final char HEH_GOAL_WITH_HAMZA_ABOVE = '\u06C2';
  public static final char TEH_MARBUTA_GOAL = '\u06C3';


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
        case DO_CHASHMI_HEH:
        case HEH_WITH_YEH_ABOVE:
        case HEH_GOAL_WITH_HAMZA_ABOVE:
        case TEH_MARBUTA_GOAL:
          s[i] = CHOTI_HEH;
          break;
        case WAW_WITH_HAMZA:
          s[i] = WAW;
          break;
        case YEH_WITH_HAMZA:
          s[i] = CHOTI_YEH;
          break;
        case BARI_YEH:
        case BARI_YEH_WITH_HAMZA_ABOVE:
          s[i] = CHOTI_YEH;
          break;
        default:
          break;
      }
    }

    return len;
  }
}
