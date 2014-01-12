package com.maktashaf.taymiyyah.vo;

import java.util.List;

import com.maktashaf.taymiyyah.model.Quran;

/**
 * @author: Haroon Anwar Padhyar.
 */
public class SearchResult {
  private int totalHits;
  private int totalPages;
  private String suggestedTerm;
  private List<Quran> quranList;

  private SearchResult(){

  }

  public int getTotalHits() {
    return totalHits;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public List<Quran> getQuranList() {
    return quranList;
  }

  public String getSuggestedTerm() {
    return suggestedTerm;
  }

  public static SearchResultBuilder builder(){
    return new SearchResultBuilder();
  }

  public static class SearchResultBuilder{
    private SearchResult searchResult;

    private SearchResultBuilder(){
      searchResult = new SearchResult();
    }

    public SearchResultBuilder withTotalHits(int totalHits){
      searchResult.totalHits = totalHits;
      return this;
    }

    public SearchResultBuilder withTotalPages(int totalPages){
      searchResult.totalPages = totalPages;
      return this;
    }

    public SearchResultBuilder withQuranList(List<Quran> quranList){
      searchResult.quranList = quranList;
      return this;
    }

    public SearchResultBuilder withSuggestedTerm(String suggestedTerm){
      searchResult.suggestedTerm = suggestedTerm;
      return this;
    }

    public SearchResult build(){
      return searchResult;
    }
  }
}
