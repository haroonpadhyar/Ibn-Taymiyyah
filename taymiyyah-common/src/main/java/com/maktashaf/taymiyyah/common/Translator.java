package com.maktashaf.taymiyyah.common;

import com.google.common.collect.ImmutableMap;

/**
 * @author: Haroon Anwar Padhyar.
 */
public enum Translator {
  Maududi(LocaleEnum.Ur),
  YousufAli(LocaleEnum.En);

  private LocaleEnum localeEnum;
  private Translator(LocaleEnum localeEnum){
    this.localeEnum = localeEnum;
  }

  public LocaleEnum getLocaleEnum(){
    return this.localeEnum;
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
