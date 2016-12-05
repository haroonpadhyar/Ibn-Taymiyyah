package com.maktashaf.taymiyyah.web.dto;

/**
 * Class to hold web request json data.
 *
 * @author Haroon Anwar Padhyar.
 */
public class RequestData {
  private Boolean ajax;
  private String term;
  private String termHidden;
  private String locale;
  private String translator;
  private String src;
  private int currentPage;
  private int totalPages;
  private Boolean original;

  public Boolean getAjax() {
    return ajax;
  }

  public void setAjax(Boolean ajax) {
    this.ajax = ajax;
  }

  /**
   *  Get term to search.
   *
   * @return term.
   */
  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

  /**
   *  Get hidden term to search. This is used in pagination request since first time searched
   *  term is set in client as hidden.
   *
   * @return term.
   */
  public String getTermHidden() {
    return termHidden;
  }

  public void setTermHidden(String termHidden) {
    this.termHidden = termHidden;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  /**
   * Get the user's selected Translator.
   *
   * @return translator.
   */
  public String getTranslator() {
    return translator;
  }

  public void setTranslator(String translator) {
    this.translator = translator;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  /**
   * Get user's selected page number.
   *
   * @return page number.
   */
  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  /**
   * Get nature of search.
   *
   * @return true if for Quran search is selected, false if translator search is selected.
   */
  public Boolean getOriginal() {
    return original;
  }

  public void setOriginal(Boolean original) {
    this.original = original;
  }
}
