<%@ page import="com.maktashaf.taymiyyah.common.Translator" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%--<fmt:setLocale value="${sessionScope.locale}" />--%>
<%
  String localeLang = (String)session.getAttribute("localeLang");
  if(localeLang != null && localeLang.equals("en")) {
%>
<fmt:setLocale value="en" />
<%
}else if(localeLang != null && localeLang.equals("ar")) {
%>
<fmt:setLocale value="ar" />
<%
}else if(localeLang != null && localeLang.equals("ur")) {
%>
<fmt:setLocale value="ur" />
<%
}else {
%>
<fmt:setLocale value="en" />
<%
  }
%>
<%--<fmt:setBundle basename="i18n.messages" var="msg"/>--%>
<fmt:bundle basename="i18n.messages">

<div class="container">

<a href="home?locale=ar">العربیة</a>&nbsp;
<a href="home?locale=ur">اردو</a>&nbsp;
<a href="home?locale=en">English</a>&nbsp;

  <div class="row">
    <%-- Full text search form --%>
    <div class="col-md-6">
      <form class="well form-search">
        <div class="row">
          <div class="col-md-2">
            <%--<label style="padding: 5px;"><fmt:message key="searchTerm" bundle="${msg}"/>:</label>--%>
            <label style="padding: 5px;"><fmt:message key="searchTerm" />:</label>
          </div>
          <div class="col-md-10">
            <input id="term" type="text" maxlength="150" class="input-medium search-query"
                   style="width: 100%;padding: 5px;" value="محمد" />
          </div>
        </div><%-- end row 1--%>
        <div class="row">
          <div class="col-md-2">
              <%--<label style="padding: 5px;"><fmt:message key="searchTerm" bundle="${msg}"/>:</label>--%>
            <label style="padding: 5px;"><fmt:message key="translator" />:</label>
          </div>
          <div class="col-md-10">
            <select id="translatorCombo" style="width: 100%;padding: 7px;">
              <%
                Translator[] translators = (Translator[])session.getAttribute("translators");
                if(null != translators)
                for(Translator translator: translators) {
                  out.println("<option value=\"" + translator.name()+"\"> " +translator.getLocaleEnum().name() +" - "+ translator.getLabel()+"</option>");
                }
              %>

            </select>
          </div>
        </div><%-- end row 2--%>
        <br>
        <div class="row">
          <div class="col-md-2">

          </div>
          <div class="col-md-5">
            <button id="orgSrch" type="submit" class="btn btn-primary"
                    <%--style="width: 100%" ><fmt:message key="searchOriginal" bundle="${msg}"/></button>--%>
                    style="width: 100%" ><fmt:message key="searchOriginal" /></button>
          </div>
          <div class="col-md-5">
            <button id="trnsSrch" type="submit" class="btn btn-primary"
                    <%--style="width: 100%" ><fmt:message key="searchTrns" bundle="${msg}"/></button>--%>
                    style="width: 100%" ><fmt:message key="searchTrns" /></button>
          </div>
        </div> <%-- end row 2--%>

        <%--Hidden values--%>
        <%--<input id="locale" type="hidden" name="locale" value=<c:out value="${sessionScope.localeLang}" default="en" /> />--%>
        <%
//          String localeLang = (String)session.getAttribute("localeLang");
          if(localeLang != null && localeLang.equals("en")) {
        %>
          <input id="locale" type="hidden" name="locale" value="en" />
        <%
          }else if(localeLang != null && localeLang.equals("ar")) {
        %>
          <input id="locale" type="hidden" name="locale" value="ar" />
        <%
          }else if(localeLang != null && localeLang.equals("ur")) {
        %>
          <input id="locale" type="hidden" name="locale" value="ur" />
        <%
          }else {
        %>
          <input id="locale" type="hidden" name="locale" value="en" />
        <%
          }
        %>
        <input id="currentPageHidden" type="hidden" value="0" />
        <input id="totalPagesHidden" type="hidden" value="0" />
        <input id="originalHidden" type="hidden" value="false" />
        <input id="termHidden" type="hidden" value="" />
        <input id="translatorHidden" type="hidden" value="" />
        <%--Hidden values--%>

      </form>
    </div>

    <%-- id search form --%>
    <div class="col-md-6">
      <form class="well form-search">
        <div class="row">
          <div class="col-md-3">
            <input id="radio" type="radio" name="radio" value="idSrch" checked>&nbsp;
            <%--<label><fmt:message key="ayahNo" bundle="${msg}"/></label>--%>
            <label><fmt:message key="ayahNo" /></label>
          </div>
          <div class="col-md-7">
            <jsp:include page="surahList.jsp"/>
          </div>
          <div class="col-md-2">

          </div>
        </div> <%-- end row 1--%>
        <br>
        <div class="row">
          <div class="col-md-3">
            <input id="radio" type="radio" name="radio" value="srSrch">&nbsp;
            <%--<label><fmt:message key="serialNo" bundle="${msg}"/></label>--%>
            <label><fmt:message key="serialNo"/></label>
          </div>
          <div class="col-md-2">
            <input id="ayaNo" type="text" maxlength="4" class="input-medium search-query"
                   style="width: 100%;padding: 5px;" value="" />
          </div>
          <div class="col-md-5">
            <button id="srch" type="submit" class="btn btn-primary"
                    <%--style="width: 100%" ><fmt:message key="search" bundle="${msg}"/></button>--%>
                    style="width: 100%" ><fmt:message key="search"/></button>
          </div>
          <div class="col-md-2">

          </div>
        </div><%-- end row 2--%>

      </form>
    </div><%--end col 6--%>
  </div> <%-- end row--%>

</div>
</fmt:bundle>