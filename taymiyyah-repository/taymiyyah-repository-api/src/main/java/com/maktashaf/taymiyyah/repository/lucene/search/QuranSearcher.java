package com.maktashaf.taymiyyah.repository.lucene.search;

import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.vo.SearchResult;

/**
 * Interface for Quran and Quran's Translation search
 *
 * @author Haroon Anwar Padhyar.
 */
public interface QuranSearcher {
  /**
   * Provide full text according to provided {@link com.maktashaf.taymiyyah.common.vo.SearchParam}
   * and give {@link com.maktashaf.taymiyyah.vo.SearchResult}.
   *
   * @param searchParam.
   * @return .
   */
  SearchResult search(SearchParam searchParam);

  /**
   * Search Quran's ayah based upon ayah accumulated id.
   *
   * @param accumId
   * @param translator
   * @return
   */
  Quran findByAccumId(int accumId, Translator translator);

  /**
   * Search Quran's ayah based upon ayah surah id and ayah id.
   *
   * @param surahId
   * @param ayahId
   * @param translator
   * @return
   */
  Quran findByAyahId(int surahId, int ayahId, Translator translator);

  /**
   * Read next Quran's ayah from accumulated id.
   *
   * @param accumId
   * @param translator
   * @param numberOfNext
   * @return {@link com.maktashaf.taymiyyah.model.Quran}
   */
  SearchResult findNextByAccumId(int accumId, Translator translator, int numberOfNext);
}
