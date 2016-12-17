package com.maktashaf.taymiyyah.search.service;

import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.repository.lucene.QuranLuceneRepo;
import com.maktashaf.taymiyyah.repository.lucene.QuranLuceneRepoImpl;
import com.maktashaf.taymiyyah.vo.SearchResult;

/**
 * Provide primary searching service for Quran and Translation.
 *
 * @author Haroon Anwar padhyar.
 */
public class QuranSearchSearchServiceImpl implements QuranSearchService {
  private QuranLuceneRepo quranLuceneRepo = new QuranLuceneRepoImpl();

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchResult doFullTextSearch(SearchParam searchParam) {
    SearchResult searchResult = quranLuceneRepo.searchAyah(searchParam);
    return searchResult;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Quran findByAccumId(int accumId, Translator translator) {
    return quranLuceneRepo.findByAccumId(accumId, translator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Quran findByAyahId(int surahId, int ayahId, Translator translator) {
    return quranLuceneRepo.findByAyahId(surahId, ayahId, translator);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchResult findNextByAccumId(int accumId, Translator translator, int numberOfNext){
    return quranLuceneRepo.findNextByAccumId(accumId, translator, numberOfNext);
  }
}
