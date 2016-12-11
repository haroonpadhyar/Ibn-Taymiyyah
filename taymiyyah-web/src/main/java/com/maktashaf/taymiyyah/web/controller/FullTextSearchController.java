package com.maktashaf.taymiyyah.web.controller;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Throwables;
import com.maktashaf.taymiyyah.common.ProjectConstant;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.common.vo.SearchParam;
import com.maktashaf.taymiyyah.search.service.QuranSearchSearchServiceImpl;
import com.maktashaf.taymiyyah.search.service.QuranSearchService;
import com.maktashaf.taymiyyah.vo.SearchResult;
import com.maktashaf.taymiyyah.web.dto.RequestData;
import com.maktashaf.taymiyyah.web.dto.ResultData;
import com.maktashaf.taymiyyah.web.exception.BusinessException;
import com.maktashaf.taymiyyah.web.util.HttpResponseUtil;
import com.maktashaf.taymiyyah.web.util.JsonUtil;
import com.maktashaf.taymiyyah.web.util.ResourceBundleUtil;
import org.apache.log4j.Logger;

/**
 * Controller to handle full text search requests.
 *
 * @author Haroon Anwar Padhyar.
 */
public class FullTextSearchController extends HttpServlet {
  private static Logger logger = Logger.getLogger(FullTextSearchController.class);

  private QuranSearchService quranSearchService = new QuranSearchSearchServiceImpl();

  /**
   * URL: /search/fulltext
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(logger.isDebugEnabled()) {
      logger.debug("Full Text Search Controller receive POST request.");
    }
    try{
      long startTime = System.currentTimeMillis();
      RequestData requestData = extractRequestData(req);
      process(resp, requestData, startTime);
    }catch(Exception e){
      logger.error("Error occurred while Full Text Search Controller receive POST request.");
      logger.error(Throwables.getStackTraceAsString(e));
      String message = "error.system.general";
      if(e instanceof BusinessException)
        message = e.getMessage() ;
      HttpResponseUtil.writeFailureResponse(resp,
          ResourceBundleUtil.getMessage(message, (Locale) req.getSession().getAttribute("locale")) );
    }
  }

  private void process(HttpServletResponse resp, RequestData requestData, long startTime) throws ServletException, IOException {
    boolean original = requestData.getOriginal();
    String term = requestData.getTerm();
    int pageNo = Math.max(requestData.getPageNo(), 1); // page number must not be less than 1.
    String translatorStr = requestData.getTranslator();
    Translator translator = Translator.look(translatorStr);

    SearchResult searchResult = quranSearchService.doFullTextSearch(SearchParam.builder()
            .withTerm(term)
            .withTranslator(translator)
            .withOriginal(original)
            .withPageNo(pageNo)
            .withPageSize(ProjectConstant.RESULT_PAGE_SIZE)// can be override with user request,
            .build());

    long totalTime = System.currentTimeMillis() - startTime;
      ResultData resultData = new ResultData()
        .withCurrentPage(pageNo)
        .withTotalPages(searchResult.getTotalPages())
        .withTotalHits(searchResult.getTotalHits())
        .withOriginal(original)
        .withTerm(term)
        .withTranslator(translator.name())
        .withTranslatorLanguage(translator.getLocaleEnum().value().getLanguage())
        .withTime(String.valueOf(totalTime / 1000f))
        .withSuggestedTerm(searchResult.getSuggestedTerm())
        .withQuranList(searchResult.getQuranList());

    HttpResponseUtil.writeSuccessResponse(resp, resultData);
  }

  private RequestData extractRequestData(HttpServletRequest req){
    String json = req.getParameter("searchParams");
    if(null == json)
      throw new BusinessException("error.invalid.request.fulltext");

    RequestData requestData = JsonUtil.fromJson(json, RequestData.class);
    String term = requestData.getTerm();
    String translatorStr = requestData.getTranslator();
    Translator translator = Translator.look(translatorStr);

    if(null == translator)
      throw new BusinessException("error.unsupported.translator");

    if(null == term || term.length() <= 0)
      throw new BusinessException("error.search.term.empty.fulltext");

    return requestData;
  }
}
