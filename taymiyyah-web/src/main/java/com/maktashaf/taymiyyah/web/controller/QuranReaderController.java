package com.maktashaf.taymiyyah.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.model.Quran;
import com.maktashaf.taymiyyah.search.service.QuranSearchSearchServiceImpl;
import com.maktashaf.taymiyyah.search.service.QuranSearchService;
import com.maktashaf.taymiyyah.vo.SearchResult;
import com.maktashaf.taymiyyah.web.dto.ResultData;
import com.maktashaf.taymiyyah.web.exception.BusinessException;
import com.maktashaf.taymiyyah.web.util.HttpResponseUtil;
import com.maktashaf.taymiyyah.web.util.ResourceBundleUtil;
import org.apache.log4j.Logger;

/**
 * Controller to handle reading based search requests. Like surah id, ayah id.
 *
 * @author Haroon Anwar Padhyar.
 */
public class QuranReaderController extends HttpServlet {
  private static Logger logger = Logger.getLogger(QuranReaderController.class);
  private Map<Integer, Integer> ayahCountMap = new HashMap<Integer, Integer>(114);
  private QuranSearchService quranSearchService = new QuranSearchSearchServiceImpl();
  private static final String CONTROLLER_URL = "search/read";

  /**
   * URL: /search/read/{accmId}/{translator}
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(logger.isDebugEnabled()) {
      logger.debug("Identity Search Controller receive POST request.");
    }
    String url = req.getRequestURI();
    try{
      long startTime = System.currentTimeMillis();
      String substring = url.substring(url.indexOf(CONTROLLER_URL));
      String[] splitUrl = substring.split("/");

      ResultData resultData = new ResultData();
      if(splitUrl.length != 4){
        throw new BusinessException("error.invalid.request");
      }else {
          String accumIdStr = splitUrl[2];
          String translatorStr = splitUrl[3];
          Translator translator = null;
          int ayahId = 0;
          try {
            ayahId = Integer.valueOf(accumIdStr);
          }
          catch(Exception e) {
            throw new BusinessException("error.invalid.ayah.id");
          }
          translator = Translator.look(translatorStr);
          if (null == translator)
            throw new BusinessException("error.unsupported.translator");

          resultData = findNextByAccumId(ayahId, translator, 10);
      }

      long totalTime = System.currentTimeMillis() - startTime;
      resultData.setTime(String.valueOf(totalTime / 1000f));
      HttpResponseUtil.writeSuccessResponse(resp, resultData);

    }catch(Exception e){
      logger.error("Error occurred while Identity Search Controller receive POST request. : "+url);
      logger.error(Throwables.getStackTraceAsString(e));
      String message = ResourceBundleUtil.getMessage("error.system.general", (Locale) req.getSession().getAttribute("locale"));
      if(e instanceof BusinessException) {
        message = e.getMessage();
        if(!((BusinessException)e).isResolved())
          message = ResourceBundleUtil.getMessage(message, (Locale) req.getSession().getAttribute("locale"));
      }
      HttpResponseUtil.writeFailureResponse(resp, message);
    }
  }

  private ResultData findNextByAccumId(int ayahId, Translator translator, int numberOfNext){
    Quran quran = null;
    SearchResult searchResult = SearchResult.builder().build();
    if(ayahId > 0 && ayahId <= 6236) {
      searchResult = quranSearchService.findNextByAccumId(ayahId, translator, numberOfNext);
    }
    else
      throw new BusinessException("error.invalid.ayah.serial.range");

    List<Quran> quranList = Lists.newArrayList();
    if(quran != null)
      quranList.add(quran);
    ResultData resultData = new ResultData()
        .withTranslatorLanguage(translator.getLocaleEnum().value().getLanguage())
        .withQuranList(searchResult.getQuranList())
        .withTotalHits(searchResult.getTotalHits());
    return resultData;
  }

}
