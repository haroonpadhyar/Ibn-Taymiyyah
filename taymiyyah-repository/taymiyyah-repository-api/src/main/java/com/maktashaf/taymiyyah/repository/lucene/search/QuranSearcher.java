package com.maktashaf.taymiyyah.repository.lucene.search;

import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.vo.SearchResult;

/**
 * @author: Haroon
 */
public interface QuranSearcher {
  SearchResult search(SearchParam searchParam);
  Quran findByAccumId(int accumId, LocaleEnum localeEnum, String realPath);
  Quran findByAyahId(int surahId, int ayahId, LocaleEnum localeEnum, String realPath);
}
