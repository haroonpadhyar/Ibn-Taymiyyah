package com.maktashaf.taymiyyah.search.service;

import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.vo.SearchResult;

/**
 * @author Haroon Anwar padhyar.
 */
public interface QuranSearchService {

  public SearchResult doFullTextSearch(SearchParam searchParam);
  public Quran findByAccumId(int accumId, Translator translator);
  public Quran findByAyahId(int surahId, int ayahId, Translator translator);
}
