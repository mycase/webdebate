<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.arguments.application.liferay.*" %>
<%@ page import="com.arguments.application.javax.JavaxPageModels" %>
<%@ page import="com.arguments.functional.report.pagemodels.*" %>

<portlet:defineObjects />
<liferay-ui:error
     key="<%=LiferayArgsBridge.MAKE_YOUR_CASE_EXC_KEY%>"
     message="<%=LiferayArgsBridge.MAKE_YOUR_CASE_EXC_MESS%>" />
<liferay-ui:error
     key="<%=LiferayArgsBridge.THESIS_NOT_OWNED_KEY%>"
     message="<%=LiferayArgsBridge.THESIS_NOT_OWNED_MESS%>" />

<%
    ListThesesPageModel myPage = JavaxPageModels.getListThesesPage(renderRequest);
%>

<portlet:renderURL var="myAddThesisURL">
    <portlet:param name="jspPage" value="/jsp/addThesis.jsp" />
</portlet:renderURL>

<portlet:renderURL var="myGotoThesisURL">
    <portlet:param name="jspPage" value="/jsp/goto.jsp" />
</portlet:renderURL>

<portlet:renderURL var="myThesisFocusURL">
    <portlet:param name="jspPage" value="/jsp/thesisFocus.jsp" />
</portlet:renderURL>

<portlet:renderURL var="myHelpURL">
    <portlet:param name="jspPage" value="/jsp/help.jsp" />
</portlet:renderURL>

<ul class="lavaLampWithImage" id="1">
<li class="current">
 <a href="<%= myAddThesisURL %>">New</a></li>
<li>
 <a href="<%= myGotoThesisURL %>">Goto</a></li>
<li>
 <a href="<%= myThesisFocusURL %>">Focus</a></li>
<li>
 <a href="<%= myHelpURL %>">Help</a></li>
</ul>

<%= myPage.theHtml %>
