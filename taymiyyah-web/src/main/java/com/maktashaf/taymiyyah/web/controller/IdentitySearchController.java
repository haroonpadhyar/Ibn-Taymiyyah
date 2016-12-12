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
import com.maktashaf.taymiyyah.web.dto.ResultData;
import com.maktashaf.taymiyyah.web.exception.BusinessException;
import com.maktashaf.taymiyyah.web.util.HttpResponseUtil;
import com.maktashaf.taymiyyah.web.util.ResourceBundleUtil;
import org.apache.log4j.Logger;

/**
 * Controller to handle identity based search requests. Like surah id, ayah id.
 *
 * @author Haroon Anwar Padhyar.
 */
public class IdentitySearchController extends HttpServlet {
  private static Logger logger = Logger.getLogger(IdentitySearchController.class);
  private Map<Integer, Integer> ayahCountMap = new HashMap<Integer, Integer>(114);
  private QuranSearchService quranSearchService = new QuranSearchSearchServiceImpl();
  private static final String CONTROLLER_URL = "search/identity";

  /**
   * URL: /search/identity/{translator}/{surahId}/{ayahId}
   * URL: /search/identity/{translator}/{ayahId}
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
      if(splitUrl.length != 4 && splitUrl.length != 5){
        throw new BusinessException("error.invalid.request");
      }else {
        if (splitUrl.length == 4) {
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

          resultData = findByAccumId(ayahId, translator);
        }
        else if (splitUrl.length == 5) {
          String surahIdStr = splitUrl[2];
          String ayahIdStr = splitUrl[3];
          String translatorStr = splitUrl[4];
          Translator translator = null;
          int ayahId = 0;
          int surahId = 0;
          try {
            surahId = Integer.valueOf(surahIdStr);
            ayahId = Integer.valueOf(ayahIdStr);
          }
          catch(Exception e) {
            throw new BusinessException("error.invalid.ayah.id");
          }
          translator = Translator.look(translatorStr);
          if (null == translator)
            throw new BusinessException("error.unsupported.translator");

          resultData = findByAyahId(req, surahId, ayahId, translator);
        }
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

  private ResultData findByAyahId(HttpServletRequest req,int surahId, int ayahId, Translator translator){
    Quran quran = null;
    if((surahId > 0 && surahId <= 114)
        && (ayahId > 0 && ayahId <= ayahCountMap.get(surahId)))
      quran = quranSearchService.findByAyahId(surahId, ayahId, translator);
    else {
      String msg = ResourceBundleUtil.getMessage("error.invalid.surah.range", (Locale) req.getSession().getAttribute("locale"));
      if(surahId > 0 && surahId <= 114){
        msg += ResourceBundleUtil.getMessage("error.invalid.ayah.range", (Locale) req.getSession().getAttribute("locale"));
        msg = String.format(msg, String.valueOf(surahId), String.valueOf(ayahCountMap.get(surahId)));
      }
      throw new BusinessException(msg, true);
    }

    List<Quran> quranList = Lists.newArrayList();
    if(quran != null)
      quranList.add(quran);
    ResultData resultData = new ResultData()
        .withTranslatorLanguage(translator.getLocaleEnum().value().getLanguage())
        .withQuranList(quranList);
    return resultData;
  }

  private ResultData findByAccumId(int ayahId, Translator translator){
    Quran quran = null;
    if(ayahId > 0 && ayahId <= 6236)
      quran = quranSearchService.findByAccumId(ayahId, translator);
    else
      throw new BusinessException("error.invalid.ayah.serial.range");

    List<Quran> quranList = Lists.newArrayList();
    if(quran != null)
      quranList.add(quran);
    ResultData resultData = new ResultData()
        .withTranslatorLanguage(translator.getLocaleEnum().value().getLanguage())
        .withQuranList(quranList);
    return resultData;
  }

  @Override
  public void init() throws ServletException {
    ayahCountMap.put(1,7);
    ayahCountMap.put(2,286);
    ayahCountMap.put(3,200);
    ayahCountMap.put(4,176);
    ayahCountMap.put(5,120);
    ayahCountMap.put(6,165);
    ayahCountMap.put(7,206);
    ayahCountMap.put(8,75);
    ayahCountMap.put(9,129);
    ayahCountMap.put(10,109);
    ayahCountMap.put(11,123);
    ayahCountMap.put(12,111);
    ayahCountMap.put(13,43);
    ayahCountMap.put(14,52);
    ayahCountMap.put(15,99);
    ayahCountMap.put(16,128);
    ayahCountMap.put(17,111);
    ayahCountMap.put(18,110);
    ayahCountMap.put(19,98);
    ayahCountMap.put(20,135);
    ayahCountMap.put(21,112);
    ayahCountMap.put(22,78);
    ayahCountMap.put(23,118);
    ayahCountMap.put(24,64);
    ayahCountMap.put(25,77);
    ayahCountMap.put(26,227);
    ayahCountMap.put(27,93);
    ayahCountMap.put(28,88);
    ayahCountMap.put(29,69);
    ayahCountMap.put(30,60);
    ayahCountMap.put(31,34);
    ayahCountMap.put(32,30);
    ayahCountMap.put(33,73);
    ayahCountMap.put(34,54);
    ayahCountMap.put(35,45);
    ayahCountMap.put(36,83);
    ayahCountMap.put(37,182);
    ayahCountMap.put(38,88);
    ayahCountMap.put(39,75);
    ayahCountMap.put(40,85);
    ayahCountMap.put(41,54);
    ayahCountMap.put(42,53);
    ayahCountMap.put(43,89);
    ayahCountMap.put(44,59);
    ayahCountMap.put(45,37);
    ayahCountMap.put(46,35);
    ayahCountMap.put(47,38);
    ayahCountMap.put(48,29);
    ayahCountMap.put(49,18);
    ayahCountMap.put(50,45);
    ayahCountMap.put(51,60);
    ayahCountMap.put(52,49);
    ayahCountMap.put(53,62);
    ayahCountMap.put(54,55);
    ayahCountMap.put(55,78);
    ayahCountMap.put(56,96);
    ayahCountMap.put(57,29);
    ayahCountMap.put(58,22);
    ayahCountMap.put(59,24);
    ayahCountMap.put(60,13);
    ayahCountMap.put(61,14);
    ayahCountMap.put(62,11);
    ayahCountMap.put(63,11);
    ayahCountMap.put(64,18);
    ayahCountMap.put(65,12);
    ayahCountMap.put(66,12);
    ayahCountMap.put(67,30);
    ayahCountMap.put(68,52);
    ayahCountMap.put(69,52);
    ayahCountMap.put(70,44);
    ayahCountMap.put(71,28);
    ayahCountMap.put(72,28);
    ayahCountMap.put(73,20);
    ayahCountMap.put(74,56);
    ayahCountMap.put(75,40);
    ayahCountMap.put(76,31);
    ayahCountMap.put(77,50);
    ayahCountMap.put(78,40);
    ayahCountMap.put(79,46);
    ayahCountMap.put(80,42);
    ayahCountMap.put(81,29);
    ayahCountMap.put(82,19);
    ayahCountMap.put(83,36);
    ayahCountMap.put(84,25);
    ayahCountMap.put(85,22);
    ayahCountMap.put(86,17);
    ayahCountMap.put(87,19);
    ayahCountMap.put(88,26);
    ayahCountMap.put(89,30);
    ayahCountMap.put(90,20);
    ayahCountMap.put(91,15);
    ayahCountMap.put(92,21);
    ayahCountMap.put(93,11);
    ayahCountMap.put(94,8);
    ayahCountMap.put(95,8);
    ayahCountMap.put(96,19);
    ayahCountMap.put(97,5);
    ayahCountMap.put(98,8);
    ayahCountMap.put(99,8);
    ayahCountMap.put(100,11);
    ayahCountMap.put(101,11);
    ayahCountMap.put(102,8);
    ayahCountMap.put(103,3);
    ayahCountMap.put(104,9);
    ayahCountMap.put(105,5);
    ayahCountMap.put(106,4);
    ayahCountMap.put(107,7);
    ayahCountMap.put(108,3);
    ayahCountMap.put(109,6);
    ayahCountMap.put(110,3);
    ayahCountMap.put(111,5);
    ayahCountMap.put(112,4);
    ayahCountMap.put(113,5);
    ayahCountMap.put(114,6);

  }
}
