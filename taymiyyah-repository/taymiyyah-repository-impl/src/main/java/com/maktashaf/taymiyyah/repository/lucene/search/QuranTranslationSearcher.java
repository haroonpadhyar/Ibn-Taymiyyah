package com.maktashaf.taymiyyah.repository.lucene.search;

import java.io.File;
import java.util.List;

import com.google.common.base.Optional;
import com.maktashaf.taymiyyah.common.QuranField;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.util.PathResolver;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.repository.lucene.analysis.AnalyzerRegistry;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Searcher for Translation Text.
 *
 * @author Haroon Anwar Padhyar.
 */
public class QuranTranslationSearcher extends AbstractQuranSearcher implements QuranSearcher{
  private static Logger logger = Logger.getLogger(QuranTranslationSearcher.class);

  @Override
  protected String resolveIndexPath(SearchParam searchParam) {
    return PathResolver.resolveIndexPath(Optional.of(searchParam.getTranslator()));
  }

  @Override
  protected String resolveSpellIndexPath(SearchParam searchParam) {
    return PathResolver.resolveSpellIndexPath(Optional.of(searchParam.getTranslator()));
  }

  @Override
  protected String getSearchedTextFromField(Quran quran) {
    return quran.getAyahTranslationText();
  }

  @Override
  protected void setSearchedTextInField(Quran quran, String text) {
    quran.setAyahTranslationText(text);
  }

  @Override
  protected Analyzer chooseAnalyzer(SearchParam searchParam){
    return AnalyzerRegistry.getAnalyzer(searchParam.getTranslator().getLocaleEnum());
  }

  @Override
  protected void setUnSearchedTextInField(SearchParam searchParam, List<Quran> quranList) {
    Directory dir = null;
    IndexReader reader = null;
    IndexSearcher searcher = null;
    try {
      dir = FSDirectory.open(new File(PathResolver.resolveIndexPath(Optional.<Translator>absent())));
      reader = DirectoryReader.open(dir);
      searcher = new IndexSearcher(reader);

      for (Quran quran : quranList) {
        TermQuery termQuery = new TermQuery(new Term(QuranField.accumId.value(), String.valueOf(quran.getAccmId())));
        TopDocs topDocs = searcher.search(termQuery, 1);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        Document doc = searcher.doc(scoreDocs[0].doc);
        quran.setAyahText(doc.get(QuranField.ayahText.value()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }finally {
      try {
        if(dir != null)
          dir.close();
        if(reader != null)
          reader.close();
      } catch(Exception e){
        e.printStackTrace();
        logger.error(e.getMessage());
        throw new RuntimeException(e);
      }
    }
  }
}
