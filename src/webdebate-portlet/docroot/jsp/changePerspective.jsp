<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ page import="com.arguments.application.javax.JavaxPageModels" %>
<%@ page import="com.arguments.functional.report.pagemodels.*" %>

<portlet:defineObjects />
<%
    ChangePerspectivePageModel myPage =
    JavaxPageModels.getChangePerspectivePage(renderRequest);
%>

<portlet:actionURL var="myChangePerspectiveURL">
    <portlet:param name="jspPage" value="/jsp/changePerspective.jsp" />
</portlet:actionURL>

<aui:form action="<%= myChangePerspectiveURL %>" method="post">
    <aui:input
         label="<%= myPage.thePerspectiveIdFormLabel%>"
         name="<%=myPage.thePerspectiveIdFormName%>"
         type="text"
         size="80"
         value="<%=myPage.thePerspectiveIdFormValue%>" />
    <aui:button type="submit" />
</aui:form>

<portlet:renderURL var="myThesisFocusURL">
    <portlet:param name="jspPage" value="/jsp/thesisFocus.jsp" />
</portlet:renderURL>

<p><a href="<%= myThesisFocusURL %>">&larr; Back</a></p>
