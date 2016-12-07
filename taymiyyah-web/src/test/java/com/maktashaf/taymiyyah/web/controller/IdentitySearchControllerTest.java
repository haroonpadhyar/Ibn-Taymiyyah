package com.maktashaf.taymiyyah.web.controller;

import com.maktashaf.taymiyyah.common.Translator;
import com.maktashaf.taymiyyah.web.exception.BusinessException;
import org.junit.Test;

/**
 * Unit Test for {@link com.maktashaf.taymiyyah.web.controller.IdentitySearchController}
 *
 * @author Haroon Anwar Padhyar.
 */
public class IdentitySearchControllerTest {
  @Test
  public void testURL(){
    // /taymiyyah/search/identity/1/1
    String url = "/taymiyyah/search/identity/1";
//    url = "/taymiyyah/search/identity/search/identity/1/1";

    String substring = url.substring(url.indexOf("search/identity"));
    System.out.println(substring);
    String[] splitUrl = substring.split("/");
    System.out.println(splitUrl.length);

    if(splitUrl.length != 4 && splitUrl.length != 5){
      throw new BusinessException("Invalid URL."); //TODO load from iI8n file
    }else {
      if(splitUrl.length == 4){
        String accumIdStr = splitUrl[2];
        String translatorStr = splitUrl[3];
        System.out.println("2 -> " + accumIdStr);
        System.out.println("3 -> " + translatorStr);
        Translator translator = null;
        int ayahId = 0;
        try{
          ayahId = Integer.valueOf(accumIdStr);
        } catch(Exception e){
          throw new BusinessException("Invalid URL Aaya id must be a valid number."); //TODO load from iI8n file
        }
        translator = Translator.look(translatorStr);
        if(null == translator)
          throw new BusinessException("Translator not supported."); //TODO load from iI8n file

      }else if(splitUrl.length == 5){
        String surahIdStr = splitUrl[2];
        String ayahIdStr = splitUrl[3];
        String translatorStr = splitUrl[4];
        System.out.println("2 -> " + surahIdStr);
        System.out.println("3 -> " + ayahIdStr);
        System.out.println("4 -> " + translatorStr);

        Translator translator = null;
        int ayahId = 0;
        int surahId = 0;
        try{
          surahId = Integer.valueOf(surahIdStr);
          ayahId = Integer.valueOf(ayahIdStr);
        } catch(Exception e){
          throw new BusinessException("Invalid URL Surah/Aaya id must be a valid number."); //TODO load from iI8n file
        }
        translator = Translator.look(translatorStr);
        if(null == translator)
          throw new BusinessException("Translator not supported."); //TODO load from iI8n file

      }

    }
  }
}
