package com.maktashaf.taymiyyah.web.util;

import com.google.gson.Gson;
import com.maktashaf.taymiyyah.web.dto.HttpResponse;
import com.maktashaf.taymiyyah.web.dto.ResultData;
import org.junit.Test;

/**
 * Unit Test for {@link com.maktashaf.taymiyyah.web.util.JsonUtil}
 *
 * @author Haroon Anwar Padhyar.
 */
public class JsonUtilTest {

  @Test
  public void testToJson(){
    ResultData resultData = new ResultData().withCurrentPage(1);
    HttpResponse httpResponse = new HttpResponse();
    httpResponse.setCode(HttpResponseUtil.SUCCESS_CODE);
    httpResponse.setData(resultData);

    String s = JsonUtil.toJson(httpResponse);
    System.out.println(s);


    Gson gson = new Gson();
    s = gson.toJson(httpResponse);
    System.out.println(s);
  }
}
