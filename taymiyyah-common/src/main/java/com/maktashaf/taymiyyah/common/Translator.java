package com.maktashaf.taymiyyah.common;

import com.google.common.collect.ImmutableMap;

/**
 * @author: Haroon Anwar Padhyar.
 */
public enum Translator {
  Arabic_Jalalayn(LocaleEnum.Arabic, "تفسير الجلالين"),
  Arabic_Muyassar(LocaleEnum.Arabic, "تفسير المیسر"),
  Urdu_AhmedAli(LocaleEnum.Urdu, "احمد علی"),
  Urdu_Jalandhry(LocaleEnum.Urdu, "جالندہری"),
  Urdu_Jawadi(LocaleEnum.Urdu, "علامہ جوادی"),
  Urdu_Junagarhi(LocaleEnum.Urdu, "محمد جوناگڑھی"),
  Urdu_AhmedRazaKhan(LocaleEnum.Urdu, "احمد رضا خان"),
  Urdu_Maududi(LocaleEnum.Urdu, " ابوالاعلی مودودی"),
  Urdu_Najafi(LocaleEnum.Urdu, "محمد حسین نجفی"),
  Urdu_Qadri(LocaleEnum.Urdu, "طاہر القادری"),
  English_AhmedAli(LocaleEnum.English, "Ahmed Ali"),
  English_AhmedRazaKhanEn(LocaleEnum.English, "Ahmed Raza Khan"),
  English_Arberry(LocaleEnum.English, "Arberry"),
  English_Daryabadi(LocaleEnum.English, "Daryabadi"),
  English_Hilali(LocaleEnum.English, "Hilali & Khan"),
  English_Itani(LocaleEnum.English, "Talal Itani"),
  English_Maududi(LocaleEnum.English, "Abul Ala Maududi"),
  English_Mubarakpuri(LocaleEnum.English, "Mubarakpuri"),
  English_Pickthall(LocaleEnum.English, "Pickthall"),
  English_Qarai(LocaleEnum.English, "Qarai"),
  English_Qaribullah(LocaleEnum.English, "Qaribullah & Darwish"),
  English_Sahih(LocaleEnum.English, "Saheeh International"),
  English_Sarwar(LocaleEnum.English, "Muhammad Sarwar"),
  English_Shakir(LocaleEnum.English, "Mohammad Habib Shakir"),
  English_Wahiduddin(LocaleEnum.English, "Wahiduddin Khan"),
  English_YousufAli(LocaleEnum.English, "Yousuf Ali");

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
