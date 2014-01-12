package com.maktashaf.taymiyyah.repository.lucene.spellcheck;

import org.apache.lucene.analysis.Analyzer;

/**
 * @author: Haroon Anwar Padhyar.
 */
public interface SpellAdviser {
  String suggest(String text, String spellIndexPath, Analyzer analyzer);
}
