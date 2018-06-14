package com.maktashaf.taymiyyah.repository.lucene.search;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.maktashaf.taymiyyah.common.QuranField;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.repository.lucene.spellcheck.SpellAdviser;
import com.maktashaf.taymiyyah.repository.lucene.spellcheck.SpellAdviserImpl;
import com.maktashaf.taymiyyah.vo.SearchResult;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.NullFragmenter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Base class for Quran and Quran's Translation search
 *
 * @author Haroon Anwar Padhyar.
 */
public abstract class AbstractQuranSearcher  implements QuranSearcher{
  private static Logger logger = Logger.getLogger(AbstractQuranSearcher.class);
  public static int MAX_HITS = 500; // ideally 20 result per page so total 25 pages.
  private SpellAdviser spellAdviser = new SpellAdviserImpl();

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchResult search(SearchParam searchParam){
    SearchResult searchResult = SearchResult.builder().build();
    Directory dir = null;
    IndexReader reader = null;
    IndexSearcher searcher = null;
    try {
      List<Quran> quranList = new ArrayList<Quran>();
      long totalHits = 0;
      int totalPages = 0;

      dir = FSDirectory.open(Paths.get(resolveIndexPath(searchParam)));

      Analyzer analyzer = chooseAnalyzer(searchParam);
      Analyzer dictionaryAnalyzer = chooseDictionaryAnalyzer(searchParam);
      if(analyzer == null || dictionaryAnalyzer == null)
        throw new RuntimeException("No Analyzer found for: "
            +searchParam.getTranslator().getLocaleEnum().value() + ": "+searchParam.getTranslator());

      reader = DirectoryReader.open(dir);
      searcher = new IndexSearcher(reader);

      QueryParser parser = new QueryParser(QuranField.ayahText.value(), analyzer);
      Query query = parser.parse(searchParam.getTerm());

      TopScoreDocCollector collector = TopScoreDocCollector.create(MAX_HITS);
      searcher.search(query, collector);

      int pageNo = Math.max(1, searchParam.getPageNo()); // if PageNo is zero then get 1.
      int pageSize = Math.max(1, searchParam.getPageSize());// if PageSize is zero then get 1.
      TopDocs topDocs = collector.topDocs((pageNo - 1)*pageSize, pageSize);

      totalHits = topDocs.totalHits;
      totalPages = (int)Math.ceil((double) Math.min(MAX_HITS, totalHits)/pageSize);
      ScoreDoc[] scoreDocs = topDocs.scoreDocs;
      for (ScoreDoc scoreDoc : scoreDocs) {
        Document doc = searcher.doc(scoreDoc.doc);
        Quran quran = new Quran();
        quran.setAccmId(Integer.valueOf(doc.get(QuranField.accumId.value())).intValue());
        quran.setAyahId(Integer.valueOf(doc.get(QuranField.ayahId.value())).intValue());
        quran.setSurahId(Integer.valueOf(doc.get(QuranField.surahId.value())).intValue());
        quran.setJuzId(Integer.valueOf(doc.get(QuranField.juzId.value())).intValue());
        quran.setSurahName(doc.get(QuranField.surahName.value()));
        quran.setJuzName(doc.get(QuranField.juzName.value()));
        setSearchedTextInField(quran, doc.get(QuranField.ayahText.value()));

        highlight(quran, query, analyzer);
        quranList.add(quran);
      }

      String suggestedTerm = "";
      if (quranList.size() <= 0) {
        String spellIndexPath = resolveSpellIndexPath(searchParam);
        suggestedTerm = spellAdviser.suggest(searchParam.getTerm(), spellIndexPath, dictionaryAnalyzer);
      }

      setUnSearchedTextInField(searchParam, quranList);
      //prepare result
      searchResult = SearchResult.builder()
          .withTotalHits(totalHits)
          .withTotalPages(totalPages)
          .withQuranList(quranList)
          .withSuggestedTerm(suggestedTerm)
          .build();
    }
    catch(Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    } finally {
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
    return searchResult;
  }

  private void highlight(Quran quran, Query query, Analyzer analyzer){
    try {
      String text = getSearchedTextFromField(quran);
      StringReader stringReader = new StringReader(text);
      TokenStream tokenStream = analyzer.tokenStream(QuranField.ayahText.value(),stringReader);
      SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span class=\"highlight\">","</span>");
      Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer( query));
      highlighter.setTextFragmenter(new NullFragmenter());
      String bestFragment = highlighter.getBestFragment(tokenStream, text);

      setSearchedTextInField(quran, bestFragment);
    }
    catch(Exception e) {
      e.printStackTrace();
      logger.debug(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Quran findByAccumId(int accumId, Translator translator){
    Quran quran = null;
    Directory dir = null;
    IndexReader reader = null;
    IndexSearcher searcher = null;
    try {
      SearchParam searchParam = SearchParam.builder()
          .withOriginal(true)
          .withTranslator(translator)
          .build();

      dir = FSDirectory.open(Paths.get(resolveIndexPath(searchParam)));
      reader = DirectoryReader.open(dir);
      searcher = new IndexSearcher(reader);

      Query query = IntPoint.newExactQuery(QuranField.accumId.value(), accumId);
      TopDocs topDocs = searcher.search(query, 1);
      ScoreDoc[] scoreDocs = topDocs.scoreDocs;
      Document doc = searcher.doc(scoreDocs[0].doc);

      quran = new Quran();
      quran.setAccmId(Integer.valueOf(doc.get(QuranField.accumId.value())).intValue());
      quran.setAyahId(Integer.valueOf(doc.get(QuranField.ayahId.value())).intValue());
      quran.setSurahId(Integer.valueOf(doc.get(QuranField.surahId.value())).intValue());
      quran.setJuzId(Integer.valueOf(doc.get(QuranField.juzId.value())).intValue());
      quran.setSurahName(doc.get(QuranField.surahName.value()));
      quran.setJuzName(doc.get(QuranField.juzName.value()));
      quran.setAyahText(doc.get(QuranField.ayahText.value()));

      // load translation.
      setUnSearchedTextInField(searchParam, Lists.newArrayList(quran));


    } catch (Exception e) {
      e.printStackTrace();
      new RuntimeException(e);
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

    return quran;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SearchResult findNextByAccumId(int accumId, Translator translator, int numberOfNext, boolean readDirection){
    Directory dir = null;
    IndexReader reader = null;
    IndexSearcher searcher = null;
    SearchResult searchResult = SearchResult.builder().build();
    try {
      List<Quran> quranList = new ArrayList<Quran>();
      SearchParam searchParam = SearchParam.builder()
          .withOriginal(true)
          .withTranslator(translator)
          .build();

      dir = FSDirectory.open(Paths.get(resolveIndexPath(searchParam)));
      reader = DirectoryReader.open(dir);
      searcher = new IndexSearcher(reader);

      int from = accumId;
      int to   = accumId + numberOfNext;
      if(!readDirection){
        from = accumId - numberOfNext;
        to   = accumId;
      }

      Query numericRangeQuery = IntPoint.newRangeQuery(QuranField.accumId.value(), from, to);
      TopDocs topDocs = searcher.search(numericRangeQuery, numberOfNext + 1); // +1 since lower range included.
      ScoreDoc[] scoreDocs = topDocs.scoreDocs;

      long totalHits = topDocs.totalHits;
      for (ScoreDoc scoreDoc : scoreDocs) {
        Document doc = searcher.doc(scoreDoc.doc);
        Quran quran = new Quran();
        quran.setAccmId(Integer.valueOf(doc.get(QuranField.accumId.value())).intValue());
        quran.setAyahId(Integer.valueOf(doc.get(QuranField.ayahId.value())).intValue());
        quran.setSurahId(Integer.valueOf(doc.get(QuranField.surahId.value())).intValue());
        quran.setJuzId(Integer.valueOf(doc.get(QuranField.juzId.value())).intValue());
        quran.setSurahName(doc.get(QuranField.surahName.value()));
        quran.setJuzName(doc.get(QuranField.juzName.value()));
        quran.setAyahText(doc.get(QuranField.ayahText.value()));

        // load translation.
        setUnSearchedTextInField(searchParam, Lists.newArrayList(quran));

        quranList.add(quran);
      }

      //prepare result
      searchResult = SearchResult.builder()
          .withTotalHits(totalHits)
          .withQuranList(quranList)
          .build();

    } catch (Exception e) {
      e.printStackTrace();
      new RuntimeException(e);
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

    return searchResult;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Quran findByAyahId(int surahId, int ayahId, Translator translator){
    Quran quran = null;
    Directory dir = null;
    IndexReader reader = null;
    IndexSearcher searcher = null;
    try {
      SearchParam searchParam = SearchParam.builder()
          .withOriginal(true)
          .withTranslator(translator)
          .build();

      dir = FSDirectory.open(Paths.get(resolveIndexPath(searchParam)));
      reader = DirectoryReader.open(dir);
      searcher = new IndexSearcher(reader);

      Query numericQuerySurah = IntPoint.newExactQuery(QuranField.surahId.value(), surahId);
      Query numericQueryAyah = IntPoint.newExactQuery(QuranField.ayahId.value(), ayahId);
      BooleanQuery booleanQuery = new BooleanQuery.Builder()
          .add(numericQuerySurah, BooleanClause.Occur.MUST)
          .add(numericQueryAyah, BooleanClause.Occur.MUST)
          .build();
      TopDocs topDocs = searcher.search(booleanQuery, 1);
      ScoreDoc[] scoreDocs = topDocs.scoreDocs;
      Document doc = searcher.doc(scoreDocs[0].doc);

      quran = new Quran();
      quran.setAccmId(Integer.valueOf(doc.get(QuranField.accumId.value())).intValue());
      quran.setAyahId(Integer.valueOf(doc.get(QuranField.ayahId.value())).intValue());
      quran.setSurahId(Integer.valueOf(doc.get(QuranField.surahId.value())).intValue());
      quran.setJuzId(Integer.valueOf(doc.get(QuranField.juzId.value())).intValue());
      quran.setSurahName(doc.get(QuranField.surahName.value()));
      quran.setJuzName(doc.get(QuranField.juzName.value()));
      quran.setAyahText(doc.get(QuranField.ayahText.value()));

      // load translation.
      setUnSearchedTextInField(searchParam, Lists.newArrayList(quran));


    } catch (Exception e) {
      e.printStackTrace();
      new RuntimeException(e);
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

    return quran;
  }

  protected abstract String resolveIndexPath(SearchParam searchParam);
  protected abstract String resolveSpellIndexPath(SearchParam searchParam);
  protected abstract String getSearchedTextFromField(Quran quran);
  protected abstract void setSearchedTextInField(Quran quran, String text);
  protected abstract void setUnSearchedTextInField(SearchParam searchParam, List<Quran> quranList);
  protected abstract Analyzer chooseAnalyzer(SearchParam searchParam);
  protected abstract Analyzer chooseDictionaryAnalyzer(SearchParam searchParam);
}
