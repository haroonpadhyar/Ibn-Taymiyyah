package com.maktashaf.taymiyyah.web.dto;

import java.util.List;

import com.maktashaf.taymiyyah.model.Quran;

/**
 * Class to hold web response data.
 *
 * @author Haroon Anwar Padhyar.
 */
public class ResultData {
  private int currentPage;
  private int totalPages;
  private int totalHits;
  private boolean original;
  private String term;
  private String translatorLanguage;
  private String time;
  private String suggestedTerm;
  List<Quran> quranList;

  /**
   * Get number current page of searched term.
   *
   * @return  current page
   */
  public int getCurrentPage() {
    return currentPage;
  }

  /**
   * Set current page of searched term.
   *
   * @param currentPage
   */
  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  /**
   * Set current page of searched term.
   *
   * @param currentPage
   * @return {@link com.maktashaf.taymiyyah.web.dto.ResultData}
   */
  public ResultData withCurrentPage(int currentPage) {
    this.currentPage = currentPage;
    return this;
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
   * Set number of total pages against searched term.
   *
   * @param totalPages
   */
  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  /**
   * Set number of total pages against searched term.
   *
   * @param totalPages
   * @return {@link com.maktashaf.taymiyyah.web.dto.ResultData}
   */
  public ResultData withTotalPages(int totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  /**
   * Get number of total hits against searched term.
   *
   * @return number of total hits
   */
  public int getTotalHits() {
    return totalHits;
  }

  /**
   * Set number of total hits against searched term.
   *
   * @param totalHits
   */
  public void setTotalHits(int totalHits) {
    this.totalHits = totalHits;
  }

  /**
   * Set number of total hits against searched term.
   *
   * @param totalHits
   * @return {@link com.maktashaf.taymiyyah.web.dto.ResultData}
   */
  public ResultData withTotalHits(int totalHits) {
    this.totalHits = totalHits;
    return this;
  }

  /**
   * Get nature of search.
   *
   * @return true if for Quran search is selected, false if translator search is selected.
   */
  public boolean getOriginal() {
    return original;
  }

  /**
   * Set Quran search or translator search.
   *
   * @param original
   */
  public void setOriginal(boolean original) {
    this.original = original;
  }

  /**
   * Set Quran search or translator search.
   *
   * @param original
   * @return {@link com.maktashaf.taymiyyah.web.dto.ResultData}
   */
  public ResultData withOriginal(boolean original) {
    this.original = original;
    return this;
  }

  /**
   *  Get term that searched.
   *
   * @return term.
   */
  public String getTerm() {
    return term;
  }

  /**
   * Set term that searched.
   * @param term
   */
  public void setTerm(String term) {
    this.term = term;
  }

  /**
   * Set term that searched.
   * @param term
   * @return {@link com.maktashaf.taymiyyah.web.dto.ResultData}
   */
  public ResultData withTerm(String term) {
    this.term = term;
    return this;
  }

  /**
   *  Get user's selected language.
   *
   * @return lang.
   */
  public String getTranslatorLanguage() {
    return translatorLanguage;
  }

  /**
   *  Set user's selected language.
   *
   * @param  translatorLanguage
   */
  public void setTranslatorLanguage(String translatorLanguage) {
    this.translatorLanguage = translatorLanguage;
  }

  /**
   *  Set user's selected language.
   *
   * @param  translatorLanguage
   * @return {@link com.maktashaf.taymiyyah.web.dto.ResultData}
   */
  public ResultData withTranslatorLanguage(String translatorLanguage) {
    this.translatorLanguage = translatorLanguage;
    return this;
  }

  /**
   *  get system processing time.
   *
   * @return time.
   */
  public String getTime() {
    return time;
  }

  /**
   *  Set system processing time.
   *
   * @param  time
   */
  public void setTime(String time) {
    this.time = time;
  }

  /**
   *  Set system processing time.
   *
   * @param  time
   * @return {@link com.maktashaf.taymiyyah.web.dto.ResultData}
   */
  public ResultData withTime(String time) {
    this.time = time;
    return this;
  }

  /**
   * Get suggested terms if no result found against searched term.
   * @return suggested terms
   */
  public String getSuggestedTerm() {
    return suggestedTerm;
  }

  /**
   * Set suggested terms if no result found against searched term..
   *
   * @param suggestedTerm
   */
  public void setSuggestedTerm(String suggestedTerm) {
    this.suggestedTerm = suggestedTerm;
  }

  /**
   * Set suggested terms if no result found against searched term..
   *
   * @param suggestedTerm
   * @return {@link com.maktashaf.taymiyyah.web.dto.ResultData}
   */
  public ResultData withSuggestedTerm(String suggestedTerm) {
    this.suggestedTerm = suggestedTerm;
    return this;
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
   * Set list of searched Quran's Ayahs against searched term.
   *
   * @param quranList
   */
  public void setQuranList(List<Quran> quranList) {
    this.quranList = quranList;
  }

  /**
   * Set list of searched Quran's Ayahs against searched term.
   *
   * @param quranList
   * @return {@link com.maktashaf.taymiyyah.web.dto.ResultData}
   */
  public ResultData withQuranList(List<Quran> quranList) {
    this.quranList = quranList;
    return this;
  }
}
