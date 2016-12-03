package com.maktashaf.taymiyyah.common.util;

import java.io.File;

import com.google.common.base.Optional;
import com.maktashaf.taymiyyah.common.ProjectConstant;
import com.maktashaf.taymiyyah.common.Translator;

/**
 *
 * @author Haroon Anwar Padhyar.
 */
public class PathResolver {

  public static String resolveIndexPath(Optional<Translator> translatorOptional){
    if(translatorOptional.isPresent()){
      return resolveIndexPathForTranslation(translatorOptional.get());
    }else {
      return resolveIndexPathForOriginal();
    }
  }

  public static String resolveSpellIndexPath(Optional<Translator> translatorOptional){
    if(translatorOptional.isPresent()){
      return resolveSpellIndexPathForTranslation(translatorOptional.get());
    }else {
      return resolveSpellIndexPathForOriginal();
    }
  }

  private static String resolveIndexPath(){
    return ProjectConstant.LUCENE_INDEX_PATH;
  }

  private static String resolveIndexPathForTranslation(Translator translator) {
    return new StringBuilder()
        .append(resolveIndexPath())
        .append(File.separator)
        .append(ProjectConstant.TRANSLATION_DIR)
        .append(File.separator)
        .append(translator.getLocaleEnum().value().getLanguage())
        .append(File.separator)
        .append(translator.name())
        .toString();
  }

  private static String resolveSpellIndexPathForTranslation(Translator translator) {
    return new StringBuilder()
        .append(resolveIndexPathForTranslation(translator))
        .append(File.separator)
        .append(ProjectConstant.SPELL_CHECK_DIR)
        .toString();
  }

  private static String resolveIndexPathForOriginal() {
    return new StringBuilder()
        .append(resolveIndexPath())
        .append(File.separator)
        .append(ProjectConstant.QURANN_DIR)
        .toString();
  }

  private static String resolveSpellIndexPathForOriginal() {
    return new StringBuilder()
        .append(resolveIndexPathForOriginal())
        .append(File.separator)
        .append(ProjectConstant.SPELL_CHECK_DIR)
        .toString();
  }
}
