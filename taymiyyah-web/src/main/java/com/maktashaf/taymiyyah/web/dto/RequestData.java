package com.maktashaf.taymiyyah.web.dto;

/**
 * Class to hold web request json data.
 *
 * @author Haroon Anwar Padhyar.
 */
public class RequestData {
  private String term;
  private String translator;
  private int pageNo;
  private Boolean original = true;

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

  /**
   * Get user's selected page number.
   *
   * @return page number.
   */
  public int getPageNo() {
    return pageNo;
  }

  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
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
