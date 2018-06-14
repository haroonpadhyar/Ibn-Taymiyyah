package com.maktashaf.taymiyyah.common.util;

import java.io.File;

import com.google.common.base.Optional;
import com.maktashaf.taymiyyah.common.ProjectConstant;
import com.maktashaf.taymiyyah.common.Translator;

/**
 *  Utility class for index path resolving for documents
 *
 * @author Haroon Anwar Padhyar.
 */
public class PathResolver {

  /**
   * Resolve path to index file of {@link com.maktashaf.taymiyyah.common.Translator} if present, otherwise
   * of Quran's index file.
   *
   * @param translatorOptional
   * @return path to index file.
   */
  public static String resolveIndexPath(Optional<Translator> translatorOptional){
    if(translatorOptional.isPresent()){
      return resolveIndexPathForTranslation(translatorOptional.get());
    }else {
      return resolveIndexPathForOriginal();
    }
  }

  /**
   * Resolve path to spell check index file of {@link com.maktashaf.taymiyyah.common.Translator} if present, otherwise
   * of Quran's spell check index file.
   *
   * @param translatorOptional
   * @return path to spell check index file
   */
  public static String resolveSpellIndexPath(Optional<Translator> translatorOptional){
    if(translatorOptional.isPresent()){
      return resolveSpellIndexPathForTranslation(translatorOptional.get());
    }else {
      return resolveSpellIndexPathForOriginal();
    }
  }

  /**
   * Resolve path to Dictionary index file of {@link com.maktashaf.taymiyyah.common.Translator} if present, otherwise
   * of Quran's Dictionary index file.
   *
   * @param translatorOptional
   * @return path to Dictionary index file
   */
  public static String resolveDictionaryIndexPath(Optional<Translator> translatorOptional){
    if(translatorOptional.isPresent()){
      return resolveDictionaryIndexPathForTranslation(translatorOptional.get());
    }else {
      return resolveDictionaryIndexPathForOriginal();
    }
  }

  /**
   * Resolve to base index path.
   */
  private static String resolveIndexPath(){
    return ProjectConstant.LUCENE_INDEX_PATH;
  }

  /**
   * Resolve to translation index path with base index path.
   */
  private static String resolveIndexPathForTranslation(Translator translator) {
    return new StringBuilder()
        .append(resolveIndexPath())
        .append(File.separator)
        .append(ProjectConstant.TRANSLATION_DIR)
        .append(File.separator)
        .append(translator.getLocaleEnum().name())
        .append(File.separator)
        .append(translator.name())
        .toString();
  }

  /**
   * Resolve to translation's spell check index path with translation index path.
   */
  private static String resolveSpellIndexPathForTranslation(Translator translator) {
    return new StringBuilder()
        .append(resolveIndexPathForTranslation(translator))
        .append(File.separator)
        .append(ProjectConstant.SPELL_CHECK_DIR)
        .toString();
  }

  /**
   * Resolve to translation's dictionary index path with translation index path.
   */
  private static String resolveDictionaryIndexPathForTranslation(Translator translator) {
    return new StringBuilder()
        .append(resolveIndexPathForTranslation(translator))
        .append(File.separator)
        .append(ProjectConstant.DICTIONARY)
        .toString();
  }

  //-----Original

  /**
   * Resolve to Quran index path with base index path.
   */
  private static String resolveIndexPathForOriginal() {
    return new StringBuilder()
        .append(resolveIndexPath())
        .append(File.separator)
        .append(ProjectConstant.QURANN_DIR)
        .toString();
  }

  /**
   * Resolve to Quran's spell check index path with Quran index path.
   */
  private static String resolveSpellIndexPathForOriginal() {
    return new StringBuilder()
        .append(resolveIndexPathForOriginal())
        .append(File.separator)
        .append(ProjectConstant.SPELL_CHECK_DIR)
        .toString();
  }

  /**
   * Resolve to Quran's Dictionary index path with Quran index path.
   */
  private static String resolveDictionaryIndexPathForOriginal() {
    return new StringBuilder()
        .append(resolveIndexPathForOriginal())
        .append(File.separator)
        .append(ProjectConstant.DICTIONARY)
        .toString();
  }
}
