package com.maktashaf.taymiyyah.vo;

import java.util.List;

import com.maktashaf.taymiyyah.model.Quran;

/**
 * Class to hold searched result for full text search.
 *
 * @author Haroon Anwar Padhyar.
 */
public class SearchResult {
  private long totalHits;
  private int totalPages;
  private String suggestedTerm;
  private List<Quran> quranList;

  private SearchResult(){

  }

  /**
   * Get number of total hits against searched term.
   *
   * @return number of total hits
   */
  public long getTotalHits() {
    return totalHits;
  }

  /**
   * Get number of total pages against searched term.
   *
   * @return number of total pages
   */
  public int getTotalPages() {
    return totalPages;
  }

  /**
   * Get list of searched Quran's Ayahs against searched term.
   *
   * @return list of searched Quran's Ayahs
   */
  public List<Quran> getQuranList() {
    return quranList;
  }

  /**
   * Get suggested terms if no result found against searched term.
   * @return suggested terms
   */
  public String getSuggestedTerm() {
    return suggestedTerm;
  }

  /**
   * Builder for {@link com.maktashaf.taymiyyah.vo.SearchResult}.
   *
   * @return {@link com.maktashaf.taymiyyah.vo.SearchResult.SearchResultBuilder}
   */
  public static SearchResultBuilder builder(){
    return new SearchResultBuilder();
  }

  /**
   * Class SearchResultBuilder
   */
  public static class SearchResultBuilder{
    private SearchResult searchResult;

    private SearchResultBuilder(){
      searchResult = new SearchResult();
    }

    /**
     * Set number of total hits against searched term.
     *
     * @param totalHits
     * @return {@link com.maktashaf.taymiyyah.vo.SearchResult.SearchResultBuilder}
     */
    public SearchResultBuilder withTotalHits(long totalHits){
      searchResult.totalHits = totalHits;
      return this;
    }

    /**
     * Set number of total pages against searched term.
     *
     * @param totalPages
     * @return {@link com.maktashaf.taymiyyah.vo.SearchResult.SearchResultBuilder}
     */
    public SearchResultBuilder withTotalPages(int totalPages){
      searchResult.totalPages = totalPages;
      return this;
    }

    /**
     * Set list of searched Quran's Ayahs against searched term.
     *
     * @param quranList
     * @return {@link com.maktashaf.taymiyyah.vo.SearchResult.SearchResultBuilder}
     */
    public SearchResultBuilder withQuranList(List<Quran> quranList){
      searchResult.quranList = quranList;
      return this;
    }

    /**
     * Set suggested terms if no result found against searched term..
     *
     * @param suggestedTerm
     * @return {@link com.maktashaf.taymiyyah.vo.SearchResult.SearchResultBuilder}
     */
    public SearchResultBuilder withSuggestedTerm(String suggestedTerm){
      searchResult.suggestedTerm = suggestedTerm;
      return this;
    }

    /**
     * Build {@link com.maktashaf.taymiyyah.vo.SearchResult} instance.
     *
     * @return instance of {@link com.maktashaf.taymiyyah.vo.SearchResult}.
     */
    public SearchResult build(){
      return searchResult;
    }
  }
}
