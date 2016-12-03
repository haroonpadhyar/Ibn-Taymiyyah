package com.maktashaf.taymiyyah.repository.lucene;

import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.repository.lucene.search.SearcherRegistry;
import com.maktashaf.taymiyyah.vo.SearchResult;
import org.apache.log4j.Logger;

/**
 * @author: Haroon Anwar Padhyar.
 */
public class QuranLuceneRepoImpl implements QuranLuceneRepo{
  private static Logger logger = Logger.getLogger(QuranLuceneRepoImpl.class);

  public SearchResult searchAyah(SearchParam searchParam){
    SearchResult searchResult = SearcherRegistry.getSearcher(searchParam.isOriginal()).search(searchParam);
    return searchResult;
  }

  public Quran findByAccumId(int accumId, Translator translator){
    Quran byAccumId = SearcherRegistry.getSearcher(Boolean.TRUE).findByAccumId(accumId, translator);
    return byAccumId;
  }
  public Quran findByAyahId(int surahId, int ayahId, Translator translator){
    Quran byAyahId = SearcherRegistry.getSearcher(Boolean.TRUE).findByAyahId(surahId, ayahId, translator);
    return byAyahId;
  }
}
