package com.maktashaf.taymiyyah.repository.lucene.analysis.ar.filter;

import static org.apache.lucene.analysis.util.StemmerUtil.delete;

/**
 * Normalize the word's end superscript. like SALLALLAHOU ALAYHE WASSALLAM
 *
 * <ul>
 *  <li> Normalization of hamza with alef seat to a bare alef.
 *  <li> Normalization of teh marbuta to heh
 *  <li> Normalization of dotless yeh (alef maksura) to yeh.
 * </ul>
 *
 * @author Haroon Anwar Padhyar.
 */
public class ArabicExtendedNormalizer {
  public static final char ALEF = '\u0627';
  public static final char ALEF_MADDA = '\u0622';
  public static final char ALEF_HAMZA_ABOVE = '\u0623';
  public static final char ALEF_HAMZA_BELOW = '\u0625';

  public static final char YEH = '\u064A';
  public static final char DOTLESS_YEH = '\u0649';

  public static final char TEH_MARBUTA = '\u0629';
  public static final char HEH = '\u0647';

  public static final char SIGN_SALLALLAHOU_ALAYHE_WASSALLAM = '\u0610';
  public static final char SIGN_ALAYHE_ASSALLAM = '\u0611';
  public static final char SIGN_RAHMATULLAH_ALAYHE = '\u0612';
  public static final char SIGN_RADI_ALLAHOU_ANHU = '\u0613';
  public static final char SIGN_TAKHALLUS = '\u0614';


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
        case ALEF_MADDA:
        case ALEF_HAMZA_ABOVE:
        case ALEF_HAMZA_BELOW:
          s[i] = ALEF;
          break;
        case DOTLESS_YEH:
          s[i] = YEH;
          break;
        case TEH_MARBUTA:
          s[i] = HEH;
          break;
        case SIGN_SALLALLAHOU_ALAYHE_WASSALLAM:
        case SIGN_ALAYHE_ASSALLAM:
        case SIGN_RADI_ALLAHOU_ANHU:
        case SIGN_RAHMATULLAH_ALAYHE:
        case SIGN_TAKHALLUS:
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
