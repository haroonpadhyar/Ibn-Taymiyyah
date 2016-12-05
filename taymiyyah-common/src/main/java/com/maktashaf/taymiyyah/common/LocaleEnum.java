package com.maktashaf.taymiyyah.common;

import java.util.Locale;

import com.google.common.collect.ImmutableBiMap;

/**
 * Available locals in system.
 *
 * @author Haroon Anwar Padhyar.
 */
public enum LocaleEnum {
  Arabic(new Locale("ar")),
  Urdu(new Locale("ur")),
  English(new Locale("en"))
  ;

  private Locale value;
  LocaleEnum(Locale value){
    this.value = value;
  }

  public Locale value(){
    return value;
  }

  /**
   * Map between {@link java.util.Locale} and {@link com.maktashaf.taymiyyah.common.LocaleEnum}
   */
  public static class localeBiMap {
    private static final ImmutableBiMap<Locale, LocaleEnum> mirror;

    static {
      ImmutableBiMap.Builder<Locale , LocaleEnum> mapBuilder = ImmutableBiMap.builder();
      for (LocaleEnum locale : LocaleEnum.values()) {
        mapBuilder.put(locale.value, locale);
      }
      mirror = mapBuilder.build();
    }

    public static LocaleEnum look(Locale locale) {
      return mirror.get(locale);
    }

    public static Locale look(LocaleEnum localeEnum) {
      return mirror.inverse().get(localeEnum);
    }
  }

  /**
   * Map between language and {@link com.maktashaf.taymiyyah.common.LocaleEnum}
   */
  public static class languageBiMap {
    private static final ImmutableBiMap<String, LocaleEnum> mirror;

    static {
      ImmutableBiMap.Builder<String , LocaleEnum> mapBuilder = ImmutableBiMap.builder();
      for (LocaleEnum localeEnum : LocaleEnum.values()) {
        mapBuilder.put(localeEnum.value.getLanguage(), localeEnum);
      }
      mirror = mapBuilder.build();
    }

    public static LocaleEnum look(String language) {
      return mirror.get(language);
    }

    public static String look(LocaleEnum localeEnum) {
      return mirror.inverse().get(localeEnum);
    }
  }

}
