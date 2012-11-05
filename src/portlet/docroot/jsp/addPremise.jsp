<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ page import="com.arguments.application.javax.JavaxPageModels" %>
<%@ page import="com.arguments.functional.report.pagemodels.*" %>

<portlet:defineObjects />
<%
    AddPremisePageModel myPage = JavaxPageModels.getAddPremisePage(renderRequest);
%>
<h1> Add premise for: <%= myPage.theThesisText %> </h1>

<portlet:actionURL var="myAddPremiseURL">
    <portlet:param name="jspPage" value="/jsp/addPremise.jsp" />
</portlet:actionURL>

<aui:form action="<%= myAddPremiseURL %>" method="post">
    <aui:input
         label="Premise text"
         name="<%=myPage.thePremiseTextFormName%>"
         type="text"
         size="80"
         value="" />
         
    <aui:input
         label="<%=myPage.thePremiseOpinionFormLabel%>"
         name="<%=myPage.thePremiseOpinionFormName%>"
         type="integer"
         size="10"
         value="<%=myPage.thePremiseOpinionFormValue%>" />
         
    <aui:input
         label="<%=myPage.theIfTrueRelevanceFormLabel%>"
         name="<%=myPage.theIfTrueRelevanceFormName%>"
         type="integer"
         size="10"
         value="<%=myPage.theIfTrueRelevanceFormValue%>" />
         
    <aui:input
         label="<%=myPage.theIfFalseRelevanceFormLabel%>"
         name="<%=myPage.theIfFalseRelevanceFormName%>"
         type="integer"
         size="10"
         value="<%= myPage.theIfFalseRelevanceFormValue %>" />
         
    <aui:button type="submit" />
</aui:form>

<portlet:renderURL var="myThesisFocusURL">
    <portlet:param name="jspPage" value="/jsp/thesisFocus.jsp" />
</portlet:renderURL>

<p><a href="<%= myThesisFocusURL %>">&larr; Back</a></p>
