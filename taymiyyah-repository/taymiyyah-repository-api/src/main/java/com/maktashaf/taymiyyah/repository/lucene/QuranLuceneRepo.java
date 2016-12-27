package com.maktashaf.taymiyyah.repository.lucene;

import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.vo.SearchResult;

/**
 * Represents Lucene repository for Quran and Translation. Provide to access
 * to appropriate {@link com.maktashaf.taymiyyah.repository.lucene.search.QuranSearcher}
 * and index resources.
 *
 * @author Haroon Anwar padhyar.
 */
public interface QuranLuceneRepo {
  /**
   * Provide full text according to provided {@link com.maktashaf.taymiyyah.common.vo.SearchParam}
   * and give {@link com.maktashaf.taymiyyah.vo.SearchResult}.
   *
   * @param searchParam
   * @return {@link com.maktashaf.taymiyyah.vo.SearchResult}.
   */
  SearchResult searchAyah(SearchParam searchParam);

  /**
   * Search Quran's ayah based upon ayah accumulated id.
   *
   * @param accumId
   * @param translator
   * @return @return {@link com.maktashaf.taymiyyah.model.Quran}
   */
  Quran findByAccumId(int accumId, Translator translator);

  /**
   * Search Quran's ayah based upon ayah surah id and ayah id.
   *
   * @param surahId
   * @param ayahId
   * @param translator
   * @return @return {@link com.maktashaf.taymiyyah.model.Quran}
   */
  Quran findByAyahId(int surahId, int ayahId, Translator translator);

  /**
   * Read next Quran's ayah from accumulated id.
   *
   * @param accumId
   * @param translator
   * @param numberOfNext
   * @param readDirection true for forward, false for reverse.
   * @return {@link com.maktashaf.taymiyyah.model.Quran}
   */
  SearchResult findNextByAccumId(int accumId, Translator translator, int numberOfNext, boolean readDirection);
}
