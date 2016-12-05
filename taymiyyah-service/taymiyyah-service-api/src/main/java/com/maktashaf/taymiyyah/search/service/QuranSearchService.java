package com.maktashaf.taymiyyah.search.service;

import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.vo.SearchResult;

/**
 * Provide primary searching service for Quran and Translation.
 *
 * @author Haroon Anwar padhyar.
 */
public interface QuranSearchService {

  /**
   * Provide full text according to provided {@link com.maktashaf.taymiyyah.common.vo.SearchParam}
   * and give {@link com.maktashaf.taymiyyah.vo.SearchResult}.
   *
   * @param searchParam
   * @return {@link com.maktashaf.taymiyyah.vo.SearchResult}.
   */
  public SearchResult doFullTextSearch(SearchParam searchParam);

  /**
   * Search Quran's ayah based upon ayah accumulated id.
   *
   * @param accumId
   * @param translator
   * @return {@link com.maktashaf.taymiyyah.model.Quran}
   */
  public Quran findByAccumId(int accumId, Translator translator);

  /**
   * Search Quran's ayah based upon ayah surah id and ayah id.
   *
   * @param surahId
   * @param ayahId
   * @param translator
   * @return @return {@link com.maktashaf.taymiyyah.model.Quran}
   */
  public Quran findByAyahId(int surahId, int ayahId, Translator translator);
}
