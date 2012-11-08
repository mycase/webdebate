<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.arguments.application.liferay.*" %>
<%@ page import="com.arguments.functional.requeststate.ArgsRequestKey" %>
<portlet:defineObjects />
<%
%>

<h1> Overview</h1>
Have you ever had difficulty convincing another person of your viewpoints?
This site helps you to be more convincing in the following ways:
<ul>
    <li> It is built on the assumption that other people are more likely to listen to you
    	if you show that you listened to them. Or that the other person is going to pay more attention
    	if you show that you can put yourself in his/her position, by:
        <ul>
        	<li>Showing awareness of his/her viewpoint</li>
        	<li>Showing awareness of his/her train of thought and how it leads to that viewpoint</li>
        	<li>Indicating precisely at which point you don't agree, and how that difference leads to your different conclusion</li>
        	<li>Leaving parts of the other's reasoning intact</li>
	    </ul>
	</li>
	<li> This site specifically supports you in
		<ul>
			<li> being clear about your own line of thought </li>
			<li> being clear about what you think other people's line of thought is</li>
			<li> comparing your own reasoning with that of somebody else, or more generally one 'perspective' with another. </li>
		</ul>
	</li>
</ul>

While using this tool, you will be forced to get your thoughts clear. You will typically be asking yourself:
<ul>
    <li> Why do I believe XYZ? </li>
	<li> Why wasn't I able to convince my neighbor of XYZ? </li>
</ul>
 
The way it works is that you will:

<ul>
	<li> Express claims ('theses') in a succinct way </li>
    <li> Express your level of belief in such claims (Believe / DontKnow / DontBelieve) </li>
    <li> Express the exact reasons (that you are aware of) for which you believe or disbelieve such claims. </li>
    <li> Express the level of support that you consider one claim to be providing to another (Supports/Irrelevant/Weakens) </li>
</ul>

You can also express your level of belief in theses that OTHER users entered into the system. The system lists all belief levels
entered for a given thesis, which is the current way in which you can compare your perspective with that of somebody else. 
 

<h1> Theses = Claims = Statements </h1>

Theses are short sentences. You can believe they are true or not, and other people can have other beliefs.

<h1> Opinions </h1>
Opinions are about statements. They can range from "I Believe it is true", "I don't know", to "I believe it is false".

<h1> Perspectives </h1>
Opinions are collected in what we here call 'perspectives': Your perspective, my perspective, another user's perspective,
the world bank's perspective.

<p> The current version of the software will only enable you enter opinions from your own perspective.
A planned feature for the future is to be able to create new 'helper' perspectives,
like that of the World Bank which you can describe in the system without there being the need for a specific user to
represent the World Bank.

<p> Statements on the screen are colored by belief level: Red = Don't believe, Black = Neutralish, Green = Believe. As noted,
these belief levels depend on the perspective. As a user of the system, you can 'change perspective', and this may change the
color of the thesis on screen.

<h1> Links / Relations / Implications </h1>

<h1> History / Planning </h1>

This site is up and running since 2012-07-30. Future plans for this site include:
<ul>
    <li> Creating new perspectives. </li>
    <li> Searching</li>
 </ul>

<portlet:renderURL var="myThesisFocusURL">
    <portlet:param name="jspPage" value="/jsp/thesisFocus.jsp" />
</portlet:renderURL>

<p><a href="<%= myThesisFocusURL %>">&larr; Back</a></p>
