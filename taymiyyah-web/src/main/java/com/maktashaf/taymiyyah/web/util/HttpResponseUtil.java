package com.maktashaf.taymiyyah.web.util;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.maktashaf.taymiyyah.web.dto.HttpResponse;

/**
 * Utility class for writing http response.
 *
 * * @author Haroon Anwar Padhyar.
 */
public class HttpResponseUtil {
  public static int SUCCESS_CODE = 200;
  public static int FAILURE_CODE = 300;

  /**
   * Write data to response stream.
   * @param resp
   * @param data
   * @throws IOException
   */
  public static void writeSuccessResponse(HttpServletResponse resp, Object data) throws IOException {
    resp.setContentType("text/html");

    HttpResponse httpResponse = new HttpResponse();
    httpResponse.setCode(SUCCESS_CODE);
    httpResponse.setData(data);

    resp.getWriter().write(JsonUtil.toJson(httpResponse));
  }

  /**
   * Write failure message to response stream.
   * @param resp
   * @param message
   * @throws IOException
   */
  public static void writeFailureResponse(HttpServletResponse resp, String message) throws IOException {
    resp.setContentType("text/html");

    HttpResponse httpResponse = new HttpResponse();
    httpResponse.setCode(FAILURE_CODE);
    httpResponse.setData(message);

    resp.getWriter().write(JsonUtil.toJson(httpResponse));
  }

}
