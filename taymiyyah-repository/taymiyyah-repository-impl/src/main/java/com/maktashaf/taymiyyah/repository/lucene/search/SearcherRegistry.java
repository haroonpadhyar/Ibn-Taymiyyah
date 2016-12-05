package com.maktashaf.taymiyyah.repository.lucene.search;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Registry for all available Quran and Translation searcher in system.
 *
 * @autho: Haroon Anwar Padhyar.
 */
public class SearcherRegistry {
  private static Map<Boolean, QuranSearcher> searcherMap = Maps.newHashMap();

  static {
    searcherMap.put(Boolean.TRUE, new QuranTextSearcher());
    searcherMap.put(Boolean.FALSE, new QuranTranslationSearcher());
  }

  public static QuranSearcher getSearcher(Boolean isOriginal){
    return searcherMap.get(isOriginal);
  }
}
