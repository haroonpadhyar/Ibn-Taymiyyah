package com.maktashaf.taymiyyah.web.controller;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.base.Throwables;
import com.maktashaf.taymiyyah.common.LocaleEnum;
import com.maktashaf.taymiyyah.common.Translator;
import org.apache.log4j.Logger;

/**
 * Controller for home page requests.
 *
 * @author Haroon Anwar Padhyar.
 */
public class HomeController  extends HttpServlet {
  private static Logger logger = Logger.getLogger(HomeController.class);

  /**
   * URL: /home
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(logger.isDebugEnabled()) {
      logger.debug("Home controller receive GET request.");
    }

    try {
      String locale = req.getParameter("locale");
      LocaleEnum localeEnum = null;
      if(locale != null && locale.length() > 0)
        localeEnum = LocaleEnum.languageBiMap.look(locale);

      if(localeEnum == null ) {
        if(null == req.getSession().getAttribute("locale"))
          localeEnum = LocaleEnum.Urdu;
        else {
          Locale local = (Locale) req.getSession().getAttribute("locale");
          localeEnum = LocaleEnum.localeBiMap.look(local);
        }
      }

      req.getSession().setAttribute("locale", localeEnum.value());
      req.getSession().setAttribute("localeLang", localeEnum.value().getLanguage());
      req.getSession().setAttribute("translators", Translator.values());
      req.getRequestDispatcher("/pages/main.jsp").forward(req, resp);


    }catch(Exception e){
      logger.error("Error occur red while Home controller receive GET request.");
      logger.error(Throwables.getStackTraceAsString(e));
    }
  }
}
