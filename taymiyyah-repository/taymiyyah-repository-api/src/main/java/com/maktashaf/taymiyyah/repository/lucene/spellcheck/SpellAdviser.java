package com.maktashaf.taymiyyah.repository.lucene.spellcheck;

import org.apache.lucene.analysis.Analyzer;

/**
 * Interface to provide suggestions if there is spell error.
 *
 * @author Haroon Anwar Padhyar.
 */
public interface SpellAdviser {
  /**
   * Provide suggestions if there is spell error in provided text term.
   *
   * @param text
   * @param spellIndexPath
   * @param analyzer
   * @return
   */
  String suggest(String text, String spellIndexPath, Analyzer analyzer);
}
