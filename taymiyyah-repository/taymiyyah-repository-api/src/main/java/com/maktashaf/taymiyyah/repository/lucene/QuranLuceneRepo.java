package com.maktashaf.taymiyyah.repository.lucene;

import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.vo.SearchResult;

/**
 * @author Haroon Anwar padhyar.
 */
public interface QuranLuceneRepo {
  SearchResult searchAyah(SearchParam searchParam);
  public Quran findByAccumId(int accumId, LocaleEnum localeEnum, String realPath);
  public Quran findByAyahId(int surahId, int ayahId, LocaleEnum localeEnum, String realPath);
}
