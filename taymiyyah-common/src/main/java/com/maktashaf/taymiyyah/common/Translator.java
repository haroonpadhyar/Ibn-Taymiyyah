package com.maktashaf.taymiyyah.common;

import com.google.common.collect.ImmutableMap;

/**
 * @author: Haroon Anwar Padhyar.
 */
public enum Translator {
  Maududi(LocaleEnum.Urdu, " ابوالاعلی مودودی"),
  YousufAli(LocaleEnum.English, "Yousuf Ali");

  private LocaleEnum localeEnum;
  private String label;
  private Translator(LocaleEnum localeEnum, String label){
    this.localeEnum = localeEnum;
    this.label = label;
  }

  public LocaleEnum getLocaleEnum(){
    return this.localeEnum;
  }
  public String getLabel(){
    return this.label;
  }

  private static final ImmutableMap<String, Translator> nameMap;

  static {
    ImmutableMap.Builder<String, Translator> mapBuilder = ImmutableMap.builder();
    for (Translator translator : Translator.values()) {
      mapBuilder.put(translator.name(), translator);
    }
    nameMap = mapBuilder.build();
  }

  public static Translator look(String name) {
    return nameMap.get(name);
  }
}
