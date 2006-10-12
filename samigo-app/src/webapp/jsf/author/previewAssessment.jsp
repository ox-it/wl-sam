<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
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
     basename="org.sakaiproject.tool.assessment.bundle.AuthorMessages"
     var="msg"/>
    <html xmlns="http://www.w3.org/1999/xhtml">
      <head><%= request.getAttribute("html.head") %>
      <title><h:outputText value="#{msg.create_modify_a}" /></title>
      </head>
<body onload="document.forms[0].reset();;<%= request.getAttribute("html.body.onload") %>">

<div class="portletBody">
<!-- content... -->
<!-- some back end stuff stubbed -->
<h:form id="assesssmentForm">

<h:messages styleClass="validation"/>

 <div class="navView">
    <h3>
        <h:outputText value="#{assessmentBean.title}" />
    </h3>
 </div>

<h:dataTable border="1" cellpadding="0" cellspacing="0" styleClass="listHier" id="parts" value="#{assessmentBean.sections}" var="partBean">
  <%-- note that partBean is ui/delivery/SectionContentBean not ui/author/SectionBean --%>
  <h:column>
    <h:panelGrid columns="2" width="100%">
      <h:panelGroup>
        <h:outputText styleClass="tier1" value="#{msg.p}" />
        <h:outputText styleClass="tier1" value="#{partBean.number}" />
        <h:outputText styleClass="tier1" value="#{partBean.title}" />
        <f:verbatim>&nbsp;- </f:verbatim>
        <h:outputText rendered="#{partBean.sectionAuthorTypeString == null || partBean.sectionAuthorTypeString == '1'}" styleClass="tier1" value="#{partBean.questions}" />
        <h:outputText rendered="#{partBean.sectionAuthorTypeString != null && partBean.sectionAuthorTypeString == '2'}" styleClass="tier1" value="#{partBean.numberToBeDrawnString}" />
        <h:outputText styleClass="tier1" value="#{msg.questions_lower_case}" />
      </h:panelGroup>

    </h:panelGrid>

  <!-- PART ATTACHMENTS -->
  <%@ include file="/jsf/author/part_attachment.jsp" %>

    <h:dataTable border="1" cellpadding="0" cellspacing="0" styleClass="listHier" id="parts" value="#{partBean.itemContents}" var="question">

      <h:column>
        <h:panelGrid columns="2" border="1" width="100%">
          <h:panelGroup>
            <h:outputText styleClass="tier1" value="#{msg.q}" />
            <h:outputText styleClass="tier1" value="#{question.number}" />
            <h:outputText styleClass="tier1" value="#{question.itemData.type.keyword}" />
            <h:outputText styleClass="tier1" value="#{question.itemData.score}" />
            <h:outputText styleClass="tier1" value="#{msg.points_lower_case}" />
          </h:panelGroup>

        </h:panelGrid>

        <h:panelGrid>
          <h:panelGroup rendered="#{question.itemData.typeId == 9}">
            <%@ include file="/jsf/author/preview_item/Matching.jsp" %>
          </h:panelGroup>
		 
		  <h:panelGroup rendered="#{question.itemData.typeId == 11}">
            <%@ include file="/jsf/author/preview_item/FillInNumeric.jsp" %>
          </h:panelGroup>


          <h:panelGroup rendered="#{question.itemData.typeId == 8}">
            <%@ include file="/jsf/author/preview_item/FillInTheBlank.jsp" %>
          </h:panelGroup>

          <h:panelGroup rendered="#{question.itemData.typeId == 7}">
            <%@ include file="/jsf/author/preview_item/AudioRecording.jsp" %>
          </h:panelGroup>

          <h:panelGroup rendered="#{question.itemData.typeId == 6}">
            <%@ include file="/jsf/author/preview_item/FileUpload.jsp" %>
          </h:panelGroup>

          <h:panelGroup rendered="#{question.itemData.typeId == 5}">
            <%@ include file="/jsf/author/preview_item/ShortAnswer.jsp" %>
          </h:panelGroup>

          <h:panelGroup rendered="#{question.itemData.typeId == 4}">
            <%@ include file="/jsf/author/preview_item/TrueFalse.jsp" %>
          </h:panelGroup>

          <!-- same as multiple choice single -->
          <h:panelGroup rendered="#{question.itemData.typeId == 3}">
            <%@ include file="/jsf/author/preview_item/MultipleChoiceSurvey.jsp" %>
          </h:panelGroup>

          <h:panelGroup rendered="#{question.itemData.typeId == 2}">
            <%@ include file="/jsf/author/preview_item/MultipleChoiceMultipleCorrect.jsp" %>
          </h:panelGroup>

          <h:panelGroup rendered="#{question.itemData.typeId == 1}">
            <%@ include file="/jsf/author/preview_item/MultipleChoiceSingleCorrect.jsp" %>
          </h:panelGroup>

        </h:panelGrid>

</h:column>
</h:dataTable>
  </h:column>
</h:dataTable>
<p class="act">
 <h:commandButton accesskey="#{msg.a_back}" value="#{msg.button_back}"  action="editAssessment" type="submit" styleClass="active"/>
</p>
</h:form>
<!-- end content -->
</div>

      </body>
    </html>
  </f:view>

