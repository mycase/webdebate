<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ page import="com.arguments.application.javax.JavaxPageModels" %>
<%@ page import="com.arguments.functional.report.pagemodels.*" %>

<portlet:defineObjects />
<%
    EditThesisPageModel myPage =
    JavaxPageModels.getEditThesisPage(renderRequest);
%>

<portlet:actionURL var="myEditThesisURL">
    <portlet:param name="jspPage" value="/jsp/editThesis.jsp" />
</portlet:actionURL>

<aui:form action="<%= myEditThesisURL %>" method="post">
    <aui:input
         label="Thesis text"
         name="<%=myPage.theThesisTextFormName%>"
         type="text"
         size="80"
         value="<%=myPage.theThesisTextFormValue%>" />
    <aui:input
         label="<%=myPage.theThesisOpinionFormLabel%>"
         name="<%=myPage.theThesisOpinionFormName%>"
         type="integer"
         size="10"
         value="<%= myPage.theThesisOpinionFormValue%>" />
    <aui:button type="submit" />
</aui:form>

<portlet:renderURL var="myThesisFocusURL">
    <portlet:param name="jspPage" value="/jsp/thesisFocus.jsp" />
</portlet:renderURL>

<p><a href="<%= myThesisFocusURL %>">&larr; Back</a></p>
