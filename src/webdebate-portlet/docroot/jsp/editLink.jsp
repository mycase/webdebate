<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ page import="com.arguments.application.javax.JavaxPageModels" %>
<%@ page import="com.arguments.functional.report.pagemodels.*" %>

<portlet:defineObjects />
<%
    EditLinkPageModel myPage = JavaxPageModels.getEditLinkPage(renderRequest);
%>

<portlet:actionURL var="myEditThesisURL">
    <portlet:param name="jspPage" value="/jsp/editThesis.jsp" />
</portlet:actionURL>

<h1>Edit link between theses.</h1>

<h3> Premise: <%=myPage.theThesis1Summary %></h3>
<h3> Conclusion: <%=myPage.theThesis2Summary %></h3>

<aui:form action="<%= myEditThesisURL %>" method="post">
    <aui:input
         name="<%=myPage.theLinkIdFormName%>"
         type="hidden"
         value="<%=myPage.theLinkIdFormValue%>" />
    <aui:input
         label="Target Thesis ID: "
         name="<%=myPage.theTargetIdFormName%>"
         type="integer"
         size="10"
         value="<%=myPage.theTargetIdFormValue%>" />
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
