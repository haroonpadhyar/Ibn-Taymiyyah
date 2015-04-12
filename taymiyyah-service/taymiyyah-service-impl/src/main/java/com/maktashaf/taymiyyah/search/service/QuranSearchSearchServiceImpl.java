package com.maktashaf.taymiyyah.search.service;

import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.repository.jdbc.QuranJDBCRepo;
import com.maktashaf.taymiyyah.repository.jdbc.QuranJDBCRepoImpl;
import com.maktashaf.taymiyyah.repository.lucene.QuranLuceneRepo;
import com.maktashaf.taymiyyah.repository.lucene.QuranLuceneRepoImpl;
import com.maktashaf.taymiyyah.vo.SearchResult;

/**
 * @author Haroon Anwar padhyar.
 */
public class QuranSearchSearchServiceImpl implements QuranSearchService {
  QuranJDBCRepo quranJDBCRepo = new QuranJDBCRepoImpl();
  QuranLuceneRepo quranLuceneRepo = new QuranLuceneRepoImpl();

  @Override
  public SearchResult doFullTextSearch(SearchParam searchParam) {
    SearchResult searchResult = quranLuceneRepo.searchAyah(searchParam);
//    quranJDBCRepo.fillTranslation(searchResult.getQuranList(), searchParam.getLocaleEnum(), searchParam.isOriginal());
    return searchResult;
  }

  @Override
  public Quran findByAccumId(int accumId, LocaleEnum localeEnum, String realPath) {
//    return quranJDBCRepo.findByAccumId(accumId, localeEnum);
    return quranLuceneRepo.findByAccumId(accumId, localeEnum, realPath);
  }

  @Override
  public Quran findByAyahId(int surahId, int ayahId, LocaleEnum localeEnum, String realPath) {
//    return quranJDBCRepo.findByAyahId(surahId, ayahId, localeEnum);
    return quranLuceneRepo.findByAyahId(surahId, ayahId, localeEnum, realPath);
  }
}
