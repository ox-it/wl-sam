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
    <html xmlns="http://www.w3.org/1999/xhtml">
      <head><%= request.getAttribute("html.head") %>
      <title><h:outputText value="#{authorMessages.auto_submit}" /></title>
      </head>
      <body>

<div class="portletBody">
<!-- content... -->
<h3><h:outputText value="#{authorMessages.auto_submit}" /></h3>
<div class="tier1">
  <h3 style="insColor insBak">
   <h:outputText  value="#{authorMessages.time_exp}" />
  </h3>
  <%-- Clicking OK returns user to the assessments page. --%>
  <h:form id="ok">
  <h:messages styleClass="validation"/>
   <h:commandButton accesskey="#{authorMessages.a_ok}" value="#{authorMessages.button_ok}" type="submit"
     style="act" action="select" />
  </h:form>
</div>
<!-- end content -->
</div>
      </body>
    </html>
  </f:view>

