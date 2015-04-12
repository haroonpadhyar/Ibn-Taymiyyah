package com.maktashaf.taymiyyah.repository.lucene.search;

import java.io.File;
import java.util.List;

import com.maktashaf.taymiyyah.common.ProjectConstant;
import com.maktashaf.taymiyyah.common.QuranField;
import com.maktashaf.taymiyyah.repository.lucene.analysis.AnalyzerRegistry;
import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
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
 * @author: Haroon Anwar Padhyar.
 */
public class QuranTextSearcher extends AbstractQuranSearcher implements QuranSearcher{
  private static Logger logger = Logger.getLogger(QuranTextSearcher.class);

  @Override
  protected String resolveIndexPath(SearchParam searchParam) {
    StringBuilder indexPath = new StringBuilder();
    indexPath.append(searchParam.getContextPath());
    indexPath.append(File.separator);
    indexPath.append(LocaleEnum.Ar.value().getLanguage());
    return indexPath.toString();
  }

  @Override
  protected String resolveSpellIndexPath(SearchParam searchParam) {
    StringBuilder indexPath = new StringBuilder();
    indexPath.append(searchParam.getContextPath());
    indexPath.append(File.separator);
    indexPath.append(ProjectConstant.spellCheckDir);
    indexPath.append(File.separator);
    indexPath.append(LocaleEnum.Ar.value().getLanguage());
    return indexPath.toString();
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
    return AnalyzerRegistry.getAnalyzer(LocaleEnum.Original, Translator.None);
  }

  @Override
  protected void setUnSearchedTextInField(SearchParam searchParam, List<Quran> quranList) {
    Directory dir = null;
    IndexReader reader = null;
    IndexSearcher searcher = null;
    try {
      StringBuilder indexPath = new StringBuilder();
      indexPath.append(searchParam.getContextPath());
      indexPath.append(File.separator);
      indexPath.append(searchParam.getLocaleEnum().value().getLanguage());

      dir = FSDirectory.open(new File(indexPath.toString()));
      reader = DirectoryReader.open(dir);
      searcher = new IndexSearcher(reader);

      for (Quran quran : quranList) {
        TermQuery termQuery = new TermQuery(new Term(QuranField.accumId.value(), String.valueOf(quran.getAccmId())));
        TopDocs topDocs = searcher.search(termQuery, 1);
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
