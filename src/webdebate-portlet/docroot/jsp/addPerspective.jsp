<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ page import="com.arguments.application.javax.JavaxPageModels" %>
<%@ page import="com.arguments.functional.report.pagemodels.*" %>

<portlet:defineObjects />
<%
    AddPerspectivePageModel myPage = JavaxPageModels.getAddPerspectivePage(renderRequest);
%>
<h1> Enter new perspective name: </h1>

<portlet:actionURL var="myAddPerspectiveURL">
    <portlet:param name="jspPage" value="/jsp/addPerspective.jsp" />
</portlet:actionURL>

<aui:form action="<%= myAddPerspectiveURL %>" method="post">
    <aui:input
         label="Perspective name"
         name="<%=myPage.thePerspectiveTextFormName%>"
         type="text"
         size="80"
         value="" />
</aui:form>

<portlet:renderURL var="myThesisFocusURL">
    <portlet:param name="jspPage" value="/jsp/thesisFocus.jsp" />
</portlet:renderURL>

<p><a href="<%= myThesisFocusURL %>">&larr; Back</a></p>
