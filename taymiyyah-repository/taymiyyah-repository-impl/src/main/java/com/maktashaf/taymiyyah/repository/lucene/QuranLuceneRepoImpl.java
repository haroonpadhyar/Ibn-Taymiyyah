package com.maktashaf.taymiyyah.repository.lucene;

import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.repository.lucene.search.SearcherRegistry;
import com.maktashaf.taymiyyah.vo.SearchResult;
import org.apache.log4j.Logger;

/**
 * Represents Lucene repository for Quran and Translation. Provide to access
 * to appropriate {@link com.maktashaf.taymiyyah.repository.lucene.search.QuranSearcher}
 * and index resources.
 *
 * @author Haroon Anwar padhyar.
 */
public class QuranLuceneRepoImpl implements QuranLuceneRepo{
  private static Logger logger = Logger.getLogger(QuranLuceneRepoImpl.class);

  /**
   * {@inheritDoc}
   */
  public SearchResult searchAyah(SearchParam searchParam){
    SearchResult searchResult = SearcherRegistry.getSearcher(searchParam.isOriginal()).search(searchParam);
    return searchResult;
  }

  /**
   * {@inheritDoc}
   */
  public Quran findByAccumId(int accumId, Translator translator){
    Quran byAccumId = SearcherRegistry.getSearcher(Boolean.TRUE).findByAccumId(accumId, translator);
    return byAccumId;
  }

  /**
   * {@inheritDoc}
   */
  public Quran findByAyahId(int surahId, int ayahId, Translator translator){
    Quran byAyahId = SearcherRegistry.getSearcher(Boolean.TRUE).findByAyahId(surahId, ayahId, translator);
    return byAyahId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchResult findNextByAccumId(int accumId, Translator translator, int numberOfNext, boolean readDirection){
    return SearcherRegistry.getSearcher(Boolean.TRUE).findNextByAccumId(accumId, translator, numberOfNext, readDirection);
  }
}
