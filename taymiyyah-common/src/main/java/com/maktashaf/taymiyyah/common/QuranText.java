package com.maktashaf.taymiyyah.common;

/**
 * Available Database table name for Quran and translation text.
 *
 * @author Haroon Anwar Padhyar.
 */
@Deprecated
public enum QuranText {
  Quran("QURAN"),
  QuranUrMaududi("QURAN_UR_MAUDUDI") ,
  QuranEnYousufAli("QURAN_EN_YOUSUFALI")
  ;

  private String value;
  QuranText(String value){
    this.value = value;
  }

  public String value() {
    return value;
  }
}
