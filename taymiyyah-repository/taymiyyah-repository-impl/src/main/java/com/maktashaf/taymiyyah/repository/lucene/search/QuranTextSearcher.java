package com.maktashaf.taymiyyah.repository.lucene.search;

import java.nio.file.Paths;
import java.util.List;

import com.google.common.base.Optional;
import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.QuranField;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.util.PathResolver;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.repository.lucene.analysis.AnalyzerRegistry;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Searcher for Quran Text.
 *
 * @author Haroon Anwar Padhyar.
 */
public class QuranTextSearcher extends AbstractQuranSearcher implements QuranSearcher{
  private static Logger logger = Logger.getLogger(QuranTextSearcher.class);

  @Override
  protected String resolveIndexPath(SearchParam searchParam) {
    return PathResolver.resolveIndexPath(Optional.<Translator>absent());
  }

  @Override
  protected String resolveSpellIndexPath(SearchParam searchParam) {
    return PathResolver.resolveSpellIndexPath(Optional.<Translator>absent());
  }

  @Override
  protected String getSearchedTextFromField(Quran quran) {
    return quran.getAyahText();
  }

  @Override
  protected void setSearchedTextInField(Quran quran, String text) {
    quran.setAyahText(text);
  }

  @Override
  protected Analyzer chooseAnalyzer(SearchParam searchParam){
    return AnalyzerRegistry.getAnalyzer(LocaleEnum.Arabic);
  }

  @Override
  protected Analyzer chooseDictionaryAnalyzer(SearchParam searchParam){
    return AnalyzerRegistry.getDictionaryAnalyzer(LocaleEnum.Arabic);
  }

  @Override
  protected void setUnSearchedTextInField(SearchParam searchParam, List<Quran> quranList) {
    Directory dir = null;
    IndexReader reader = null;
    IndexSearcher searcher = null;
    try {
      dir = FSDirectory.open(Paths.get(PathResolver.resolveIndexPath(Optional.of(searchParam.getTranslator()))));
      reader = DirectoryReader.open(dir);
      searcher = new IndexSearcher(reader);

      for (Quran quran : quranList) {
        Query query = IntPoint.newExactQuery(QuranField.accumId.value(), quran.getAccmId());
        TopDocs topDocs = searcher.search(query, 1);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        Document doc = searcher.doc(scoreDocs[0].doc);
        quran.setAyahTranslationText(doc.get(QuranField.ayahText.value()));
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
