package com.maktashaf.taymiyyah.common.vo;

import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.Translator;

/**
 * Common value object to hold search parameters for full text search.
 *
 * @author Haroon Anwar Padhyar.
 */
public class SearchParam {
  private String term;
  private LocaleEnum localeEnum;
  private Translator translator;
  private boolean original;
  private int pageNo;
  private int pageSize;

  private SearchParam(){

  }

  /**
   *  Get term to search.
   *
   * @return term.
   */
  public String getTerm() {
    return term;
  }

  /**
   *  Get user's selected {@link com.maktashaf.taymiyyah.common.LocaleEnum}.
   *
   * @return localEnum.
   */
  public LocaleEnum getLocaleEnum() {
    return localeEnum;
  }

  /**
   * Get nature of search.
   *
   * @return true if for Quran search is selected, false if translator search is selected.
   */
  public boolean isOriginal() {
    return original;
  }

  /**
   * Get the user's selected {@link com.maktashaf.taymiyyah.common.Translator}.
   *
   * @return translator.
   */
  public Translator getTranslator(){
    return translator;
  }

  /**
   * Get user's selected page number.
   *
   * @return page number.
   */
  public int getPageNo(){
    return pageNo;
  }

  /**
   * Get user's selected page size.
   *
   * @return page size
   */
  public int getPageSize(){
    return pageSize;
  }

  /**
   * Builder for {@link com.maktashaf.taymiyyah.common.vo.SearchParam}.
   *
   * @return {@link com.maktashaf.taymiyyah.common.vo.SearchParam.SearchParamBuilder}
   */
  public static SearchParamBuilder builder(){
    return new SearchParamBuilder();
  }

  /**
   * Class SearchParamBuilder
   */
  public static class SearchParamBuilder{
    private SearchParam searchParam;

    private SearchParamBuilder(){
      searchParam = new SearchParam();
    }

    /**
     * Set term to search.
     * @param term
     * @return {@link com.maktashaf.taymiyyah.common.vo.SearchParam.SearchParamBuilder}
     */
    public SearchParamBuilder withTerm(String term){
      searchParam.term = term;
      return this;
    }

    /**
     * Set {@link com.maktashaf.taymiyyah.common.LocaleEnum}.
     *
     * @param localeEnum
     * @return {@link com.maktashaf.taymiyyah.common.vo.SearchParam.SearchParamBuilder}
     */
    public SearchParamBuilder withLocale(LocaleEnum localeEnum){
      searchParam.localeEnum = localeEnum;
      return this;
    }

    /**
     * Set {@link com.maktashaf.taymiyyah.common.Translator}.
     *
     * @param translator
     * @return {@link com.maktashaf.taymiyyah.common.vo.SearchParam.SearchParamBuilder}
     */
    public SearchParamBuilder withTranslator(Translator translator){
      searchParam.translator = translator;
      return this;
    }

    /**
     * Set Quran search or translator search.
     *
     * @param original
     * @return {@link com.maktashaf.taymiyyah.common.vo.SearchParam.SearchParamBuilder}
     */
    public SearchParamBuilder withOriginal(boolean original){
      searchParam.original = original;
      return this;
    }

    /**
     * Set page number.
     *
     * @param pageNo
     * @return {@link com.maktashaf.taymiyyah.common.vo.SearchParam.SearchParamBuilder}
     */
    public SearchParamBuilder withPageNo(int pageNo){
      searchParam.pageNo = pageNo;
      return this;
    }

    /**
     * Set page size.
     * @param pageSize
     * @return {@link com.maktashaf.taymiyyah.common.vo.SearchParam.SearchParamBuilder}
     */
    public SearchParamBuilder withPageSize(int pageSize){
      searchParam.pageSize = pageSize;
      return this;
    }

    /**
     * Build {@link com.maktashaf.taymiyyah.common.vo.SearchParam} instance.
     *
     * @return instance of {@link com.maktashaf.taymiyyah.common.vo.SearchParam}.
     */
    public SearchParam build(){
      return searchParam;
    }
  }
}
