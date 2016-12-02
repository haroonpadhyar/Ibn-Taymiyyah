package com.maktashaf.taymiyyah.vo;

/**
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

  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

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

  public Boolean getOriginal() {
    return original;
  }

  public void setOriginal(Boolean original) {
    this.original = original;
  }
}
