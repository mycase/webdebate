<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.arguments.application.liferay.*" %>
<%@ page import="com.arguments.application.javax.JavaxPageModels" %>
<%@ page import="com.arguments.functional.report.pagemodels.*" %>
<%@ page import="com.arguments.functional.report.html.UrlContainer" %>

<portlet:defineObjects />
<liferay-ui:error
     key="<%=LiferayArgsBridge.MAKE_YOUR_CASE_EXC_KEY%>"
     message="<%=LiferayArgsBridge.MAKE_YOUR_CASE_EXC_MESS%>" />
<liferay-ui:error
     key="<%=LiferayArgsBridge.THESIS_NOT_OWNED_KEY%>"
     message="<%=LiferayArgsBridge.THESIS_NOT_OWNED_MESS%>" />

<liferay-ui:error
     key="<%=LiferayArgsBridge.PERSPECTIVE_NOT_OWNED_KEY%>"
     message="<%=LiferayArgsBridge.PERSPECTIVE_NOT_OWNED_MESS%>" />

<portlet:renderURL var="myAddThesisURL">
    <portlet:param name="jspPage" value="/jsp/addThesis.jsp" />
</portlet:renderURL>

<portlet:renderURL var="myEditThesisURL">
    <portlet:param name="jspPage" value="/jsp/editThesis.jsp" />
</portlet:renderURL>

<portlet:renderURL var="myAddPremiseURL">
    <portlet:param name="jspPage" value="/jsp/addPremise.jsp" />
</portlet:renderURL>

<portlet:renderURL var="myGotoThesisURL">
    <portlet:param name="jspPage" value="/jsp/goto.jsp" />
</portlet:renderURL>

<portlet:renderURL var="myAllThesesURL">
    <portlet:param name="jspPage" value="/jsp/listTheses.jsp" />
</portlet:renderURL>

<portlet:renderURL var="myEditLinkURL">
    <portlet:param name="jspPage" value="/jsp/editLink.jsp" />
</portlet:renderURL>

<portlet:renderURL var="myAddOpinionURL">
    <portlet:param name="jspPage" value="/jsp/addOpinion.jsp" />
</portlet:renderURL>

<portlet:renderURL var="myChangePerspectiveURL">
    <portlet:param name="jspPage" value="/jsp/changePerspective.jsp" />
</portlet:renderURL>

<portlet:renderURL var="myHelpURL">
    <portlet:param name="jspPage" value="/jsp/help.jsp" />
</portlet:renderURL>

<%
    UrlContainer myUrlContainer = new UrlContainer();
	myUrlContainer.setEditLinkUrl(myEditLinkURL);
	myUrlContainer.setEditThesisUrl(myEditThesisURL);
	myUrlContainer.setAddPremiseUrl(myAddPremiseURL);
	myUrlContainer.setAddOpinionUrl(myAddOpinionURL);
%>

<%
    ThesisFocusPageModel myPage =
	    JavaxPageModels.getThesisFocusPage(renderRequest, myUrlContainer);
%>

<ul class="lavaLampWithImage" id="1">
<li class="current">
 <a href="<%= myAddThesisURL %>">New thesis</a></li>
<li>
 <a href="<%= myGotoThesisURL %>">Goto</a></li>
<li>
 <a href="<%= myChangePerspectiveURL %>">Change perspective</a></li>
<li>
 <a href="<%= myAllThesesURL %>">Search</a></li>
<li>
 <a href="<%= myHelpURL %>">Help</a></li>
</ul>

<%= myPage.theHtml %>
