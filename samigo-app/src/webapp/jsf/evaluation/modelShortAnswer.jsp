<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!--
$Id: fullShortAnswer.jsp 6643 2006-03-13 19:38:07Z hquinn@stanford.edu $
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
      <title><h:outputText
        value="#{evaluationMessages.title_question}" /></title>
      </head>
      <body onload="<%= request.getAttribute("html.body.onload") %>">
  
<h:dataTable value="#{delivery.pageContents.partsContents}" var="part">
<h:column>
<h:dataTable cellpadding="0" cellspacing="0" id="questionScoreTable" value="#{part.itemContents}"
    var="question" style="listHier tier2" columnClasses="textTable">
<h:column>
<h:outputText rendered="#{param.idString eq question.itemData.itemId}" escape="false" value="#{question.key}"/>
</h:column>
</h:dataTable>
</h:column>
</h:dataTable>
      </body>
    </html>
  </f:view>
