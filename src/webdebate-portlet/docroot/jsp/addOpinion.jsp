<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ page import="com.arguments.application.javax.JavaxPageModels" %>
<%@ page import="com.arguments.functional.report.pagemodels.*" %>

<portlet:defineObjects />
<%
    AddOpinionPageModel myPage =
    JavaxPageModels.getAddOpinionPage(renderRequest);
%>

<h1> Set opinion for: <%=myPage.theThesisText%> </h1>

<portlet:actionURL var="myEditThesisURL">
    <portlet:param name="jspPage" value="/jsp/editThesis.jsp" />
</portlet:actionURL>

<aui:form action="<%= myEditThesisURL %>" method="post">
    <aui:input
         label="<%=myPage.theFormLabel%>"
         name="<%=myPage.theFormName%>"
         type="integer"
         size="10"
         value="<%=myPage.theThesisOpinion%>" />
    <aui:button type="submit" />
</aui:form>

<portlet:renderURL var="myThesisFocusURL">
    <portlet:param name="jspPage" value="/jsp/thesisFocus.jsp" />
</portlet:renderURL>

<p><a href="<%= myThesisFocusURL %>">&larr; Back</a></p>
