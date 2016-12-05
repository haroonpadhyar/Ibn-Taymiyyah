package com.maktashaf.taymiyyah.repository.lucene.analysis.ar.filter;

/**
 * Letter Sound alike Normalizer for Arabic.
 * Letters of similar sound are replaced with common one.
 * It replaces the Arabic alphabet for Arabic
 *
 *  Normalization is defined as:
 *  <ul>
 *  <li> Normalization of SAAD with SEEN and so on.
 * </ul>
 *
 * @author Haroon Anwar Padhyar.
 */
public class ArabicSoundAlikeNormalizer {
  public static final char ALEF = '\u0627';
  public static final char TAA = '\u062B'; // ت
  public static final char SAA = '\u062B'; // ث
  public static final char HA = '\u062D'; // ح
  public static final char DAAL = '\u062F'; // د
  public static final char ZAAL = '\u0630'; //  ذ
  public static final char ZAY = '\u0632';  //   ز
  public static final char SEEN = '\u0633'; //    س
  public static final char SAAD = '\u0635'; //     ص
  public static final char ZAAD = '\u0636'; //     ض
  public static final char TTA = '\u0637';  //   ط
  public static final char ZA = '\u0638';  //    ظ
  public static final char AYN = '\u0639';//      ع
  public static final char QAF = '\u0642';//       ق
  public static final char KAF = '\u0643'; //       ك
  public static final char HEH = '\u0647'; //  ه
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
        case SAA:
          s[i] = SEEN;
          break;
        case HA:
          s[i] = HEH;
          break;
        case ZAY:
          s[i] = ZAAL;
          break;
        case SAAD:
          s[i] = SEEN;
          break;
        case ZAAD:
          s[i] = DAAL;
          break;
        case TTA:
          s[i] = TAA;
          break;
        case ZA:
          s[i] = ZAAL;
          break;
        case AYN:
          s[i] = ALEF;
          break;
        case KAF:
          s[i] = QAF;
          break;
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
