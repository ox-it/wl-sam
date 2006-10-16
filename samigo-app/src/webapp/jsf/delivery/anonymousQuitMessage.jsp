<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.sakaiproject.org/samigo" prefix="samigo" %>
<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--
* $Id$
<%--
***********************************************************************************
*
* Copyright (c) 2004, 2005, 2006 The Sakai Foundation.
*
* Licensed under the Educational Community License, Version 1.0 (the"License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.opensource.org/licenses/ecl1.php
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License. 
*
**********************************************************************************/
--%>
-->
  <f:view>
    <f:loadBundle
      basename="org.sakaiproject.tool.assessment.bundle.DeliveryMessages"
      var="msg"/>
  
    <html xmlns="http://www.w3.org/1999/xhtml">
      <head><%= request.getAttribute("html.head") %>
      <title><h:outputText value="#{msg.quit_assessment}"/></title>
      </head>
      <body onload="<%= request.getAttribute("html.body.onload") %>">

<div class="portletBody">
<h3> <h:outputText value="#{msg.anonymous_quit_warning}"/></h3>
<h:form id="redirectLoginForm">
 <div class="validation">
<h:outputText  value="#{msg.anonymous_quit_warning_message}" />
 </div>

<p class="act">
  <h:commandButton accesskey="#{msg.a_cancel}" value="#{msg.button_continue}" type="button"
     styleClass="active" onclick="javascript:history.go(-1);" onkeypress="javascript:history.go(-1);" />

  <h:commandButton accesskey="#{msg.a_quit}" value="#{msg.button_quit}" type="button"
     onclick="javascript:window.open('#{delivery.portal}/login','_top')" onkeypress="javascript:window.open('#{delivery.portal}/login','_top')" />
</p>
</h:form>
</div>
      </body>
    </html>
  </f:view>

