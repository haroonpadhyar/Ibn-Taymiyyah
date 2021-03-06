<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">--%>
<!DOCTYPE html>
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
  <html>
  <head>
    <title>مكتشف</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <%--
      <c:if test="${sessionScope.locale eq null or sessionScope.locale.language.equals('en')}">
        <link id="cssLink" href="css/ltr/bootstrap.css" rel="stylesheet" media="screen">
      </c:if>
      <c:if test="${sessionScope.locale ne null and !sessionScope.locale.language.equals('en')}">
        <link id="cssLink" href="css/rtl/bootstrap.css" rel="stylesheet" media="screen">
      </c:if>
      --%>
    <%
      //      String localeLang = (String)session.getAttribute("localeLang");
      if(localeLang != null && !localeLang.equals("en")) {
    %>
    <link id="cssLink" href="css/rtl/bootstrap.css" rel="stylesheet" media="screen">
    <%
    }else {
    %>
    <link id="cssLink" href="css/ltr/bootstrap.css" rel="stylesheet" media="screen">
    <%
      }
    %>

    <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
    <link rel="stylesheet" type="text/css" href="css/table.css"/>
    <script src="javascript/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="javascript/bootstrap.min.js"></script>
    <script src="javascript/ajax.js" type="text/javascript"></script>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="earch in the Quran القران الكريم, Quran, Quraan, Qur'an, Coran, Islam, Islaam, Islamic, Search Engine, Free, Translation, Transliteration, Allah, Muhammad, , Yusuf Ali,Maududi, Quran Reader,Sura, Sora, Islamic Finder, IslamicFinder, Urdu Quran search,Arabic quran search,Search The Quran.  The most advanced advanced Quran Search Engine built using cutting-edge search technology.">
    <meta http-equiv="description" content="This is my page">

    <!--CUSTOM CSS-->
    <style>
      body {
        padding-top: 50px;
      }
    </style>
  </head>

  <body>

  <jsp:include page="header.jsp" />
  <jsp:include page="search.jsp" />

  <div class="container">
    <ul class="nav nav-tabs" style="display: none">
      <li class="active">
        <a id="searchTab" data-toggle="tab" href="#Search" style="display: none"><fmt:message key="search" /></a>
      </li>
      <li>
        <a id="readTab" data-toggle="tab" href="#Read" style="display: none"><fmt:message key="reading" /></a>
      </li>
    </ul>
    <div id="readingLableHidden" style="display: none"><fmt:message key="QuranReading" /></div>

    <div class="tab-content">
      <div id="Search" class="tab-pane fade in active">
        <div id="timeDiv" style="display: none">
          <small style="color: darkgray">
              <%--<fmt:message key="almost" bundle="${msg}"/>--%>
            <fmt:message key="almost"/>
            <span id="totalHitsSmall"></span>
              <%--<fmt:message key="results" bundle="${msg}"/>--%>
            <fmt:message key="results"/>
            (<span id="time"></span>)&nbsp;
              <%--<fmt:message key="seconds" bundle="${msg}"/>--%>
            <fmt:message key="seconds"/>
          </small>
        </div>
        <div id="qtableDiv" style="display: none"></div>
        <div id="paginationDiv" style="display: none">
          <jsp:include page="pagination.jsp" />
        </div>
        <div id="errorMsg" class="alert alert-danger" style="display: none">
          <strong><fmt:message key="error" />!&nbsp;</strong><span><fmt:message key="error.system.general" /></span>
        </div>
        <div id="didYouMean" class="alert alert-info" style="display: none">
          <label style="color: red"><fmt:message key="didYouMean" />:</label>&nbsp;
          <span id="didYouMeanSuggestion" style="color: blue; font: bold;font-size: medium"></span>
        </div>

      </div>
      <div id="Read" class="tab-pane fade">
        <div id="timeReadDiv" style="display: none">
          <small style="color: darkgray">
              <%--<fmt:message key="almost" bundle="${msg}"/>--%>
            <fmt:message key="almost"/>
            <span id="totalHitsSmallRead"></span>
              <%--<fmt:message key="results" bundle="${msg}"/>--%>
            <fmt:message key="results"/>
            (<span id="timeRead"></span>)&nbsp;
              <%--<fmt:message key="seconds" bundle="${msg}"/>--%>
            <fmt:message key="seconds"/>
          </small>
        </div>
        <div id="qtableReadDiv" style="display: none"></div>
        <div id="paginationReadDiv" style="display: none">
          <ul class="pagination">
            <li><a href="" id="prvRead" class="previous">&laquo;&nbsp;<fmt:message key="previous"/></a></li>
            <li><a href="" id="nxtRead" class="next"><fmt:message key="next"/>&nbsp;&raquo;</a></li>
          </ul>
        </div>
        <input id="accumIdHidden" type="hidden" value="0" />
      </div>
    </div>

  </div>


    <%--
      <div class="container">
          <c:catch var="ex">
            <c:if test="${ex ne null}">
              <c:out value="Some error occure."/>
              <c:out value="${ex.message}"/>
            </c:if>
          </c:catch>
        </div>
    --%>

  <div class="modal"></div>
  </body>
  </html>
</fmt:bundle>