<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ page import="com.arguments.application.javax.JavaxPageModels" %>
<%@ page import="com.arguments.functional.report.pagemodels.*" %>

<portlet:defineObjects />
<%
    AddThesisPageModel myPage = JavaxPageModels.getAddThesisPage(renderRequest);
%>
<h1> Enter new thesis: </h1>

<portlet:actionURL var="myAddThesisURL">
    <portlet:param name="jspPage" value="/jsp/addThesis.jsp" />
</portlet:actionURL>

<aui:form action="<%= myAddThesisURL %>" method="post">
    <aui:input
         label="Thesis text"
         name="<%=myPage.theThesisTextFormName%>"
         type="text"
         size="80"
         value="" />
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
