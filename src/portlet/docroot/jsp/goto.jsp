<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ page import="com.arguments.application.javax.JavaxPageModels" %>
<%@ page import="com.arguments.functional.report.pagemodels.*" %>
<portlet:defineObjects />
<%
    GotoThesisPageModel myPage =
    JavaxPageModels.getGotoThesisPage(renderRequest);
%>

<portlet:actionURL var="myGotoThesisURL">
    <portlet:param name="jspPage" value="/jsp/goto.jsp" />
</portlet:actionURL>

<aui:form action="<%= myGotoThesisURL %>" method="post">
    <aui:input
    	label="Enter Thesis Id"
        name="<%=myPage.theThesisIdFormName%>"
        type="text"
        value="" />
    <aui:button value="Go" type="submit" />
</aui:form>


<portlet:renderURL var="myThesisFocusURL">
    <portlet:param name="jspPage" value="/jsp/thesisFocus.jsp" />
</portlet:renderURL>

<p><a href="<%= myThesisFocusURL %>">&larr; Back</a></p>
