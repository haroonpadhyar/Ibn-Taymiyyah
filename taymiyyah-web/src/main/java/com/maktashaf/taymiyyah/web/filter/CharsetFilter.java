package com.maktashaf.taymiyyah.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * Web request filter to set encoding.
 *
 * @author Haroon Anwar Padhyar.
 */
public class CharsetFilter implements Filter{
  private String encoding;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    encoding = filterConfig.getInitParameter("encoding");

    if( encoding==null )
      encoding="UTF-8";
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if(request.getCharacterEncoding() == null)
      request.setCharacterEncoding(encoding);

    response.setCharacterEncoding(encoding);

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }
}
