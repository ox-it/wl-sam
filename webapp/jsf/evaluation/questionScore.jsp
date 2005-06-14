<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://www.sakaiproject.org/samigo" prefix="samigo" %>
  <f:view>
    <f:verbatim><!DOCTYPE html
     PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    </f:verbatim>
    <f:loadBundle
     basename="org.sakaiproject.tool.assessment.bundle.EvaluationMessages"
     var="msg"/>
     <f:loadBundle
     basename="org.sakaiproject.tool.assessment.bundle.GeneralMessages"
     var="genMsg"/>
    <html xmlns="http://www.w3.org/1999/xhtml">
      <head><%= request.getAttribute("html.head") %>
      <title><h:outputText
        value="#{msg.title_question}" /></title>
      <samigo:stylesheet path="/css/samigo.css"/>
      <samigo:stylesheet path="/css/sam.css"/>
      <!-- HTMLAREA -->
      </head>
      <body onload="<%= request.getAttribute("html.body.onload") %>">
<!-- $Id:  -->
<!-- content... -->
<h:form id="editTotalResults">
  <h:inputHidden id="publishedId" value="#{questionScores.publishedId}" />
  <h:inputHidden id="itemId" value="#{questionScores.itemId}" />

  <!-- HEADINGS -->
  <p class="navIntraTool">
    <h:commandLink action="author" immediate="true">
       <f:actionListener
         type="org.sakaiproject.tool.assessment.ui.listener.author.AuthorActionListener" />
      <h:outputText value="#{msg.global_nav_assessmt}" />
    </h:commandLink>
    <h:outputText value=" | " />
    <h:commandLink action="template" immediate="true">
      <h:outputText value="#{msg.global_nav_template}" />
    </h:commandLink>
    <h:outputText value=" | " />
    <h:commandLink action="poolList" immediate="true">
      <h:outputText value="#{msg.global_nav_pools}" />
    </h:commandLink>
  </p>
  <h3>
    <h:outputText value="#{questionScores.itemName} (#{questionScores.assessmentName}) "/>
  </h3>
  <p class="navModeAction">
    <h:commandLink action="totalScores" immediate="true">
    <f:actionListener type="org.sakaiproject.tool.assessment.ui.listener.evaluation.TotalScoreListener" />
      <h:outputText value="#{msg.title_total}" />
    </h:commandLink>
    <h:outputText value=" | " />
      <h:outputText value="#{msg.q_view}" />
    <h:outputText value=" | " />
    <h:commandLink action="histogramScores" immediate="true">
      <h:outputText value="#{msg.stat_view}" />
      <f:param name="hasNav" value="true"/>
      <f:actionListener
        type="org.sakaiproject.tool.assessment.ui.listener.evaluation.HistogramListener" />
    </h:commandLink>
  </p>

  <h:messages layout="table" style="color:red"/>

  <h:dataTable value="#{questionScores.sections}" var="partinit">
    <h:column>
      <h:outputText value="#{partinit.partNumber}" />
    </h:column>
    <h:column>
      <samigo:dataLine value="#{partinit.questionNumberList}" var="iteminit" separator=" | " first="0" rows="100">
        <h:column>
          <h:commandLink action="questionScores" immediate="true"
            rendered="#{iteminit.linked}" >
            <h:outputText value="#{iteminit.partNumber} " />
            <f:actionListener
              type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreListener" />
            <f:param name="newItemId" value="#{iteminit.id}" />
          </h:commandLink>
          <h:outputText value="#{iteminit.partNumber} " 
             rendered="#{!iteminit.linked}" />
        </h:column>
      </samigo:dataLine>
    </h:column>
  </h:dataTable>

 <p class="navModeQuestion">
<h:panelGrid columns="2" columnClasses="alignLeft,alignCenter">

     <h:panelGroup rendered="#{questionScores.typeId == '7'}">
         <h:outputText value="AutoRecording"/>
     </h:panelGroup>
      <h:panelGroup rendered="#{questionScores.typeId == '6'}">
         <h:outputText value="File Upload"/>
     </h:panelGroup>
     <h:panelGroup rendered="#{questionScores.typeId == '8'}">
         <h:outputText value="Fill in the blank"/>
     </h:panelGroup>
      <h:panelGroup rendered="#{questionScores.typeId == '9'}">
         <h:outputText value="Matching"/>
     </h:panelGroup>
     <h:panelGroup rendered="#{questionScores.typeId == '2'}">
         <h:outputText value="Multiple Choice Multiple Correct"/>
     </h:panelGroup>
     <h:panelGroup rendered="#{questionScores.typeId == '4'}">
         <h:outputText value="True/False"/>
     </h:panelGroup>

     <h:panelGroup rendered="#{questionScores.typeId == '5'}">
         <h:outputText value="Short Answer"/>
     </h:panelGroup>
     <h:panelGroup rendered="#{questionScores.typeId == '1' || questionScores.typeId == '3'}">
    <h:outputText value="Multiple Choice Single Correct"/>
      </h:panelGroup>


     <h:outputText value="#{questionScores.maxScore} Point" style="instruction"/>

 </h:panelGrid>
 </p>
  <h:dataTable value="#{questionScores.deliveryItem}" var="question">
  <h:column>
  <h:panelGroup rendered="#{questionScores.typeId == '7'}">
    <f:subview id="displayAudioRecording">
      <%@ include file="/jsf/evaluation/item/displayAudioRecording.jsp" %>
    </f:subview>
  </h:panelGroup>
  <h:panelGroup rendered="#{questionScores.typeId == '6'}">
    <f:subview id="displayFileUpload">
    <%@ include file="/jsf/evaluation/item/displayFileUpload.jsp" %>
    </f:subview>
  </h:panelGroup>
  <h:panelGroup rendered="#{questionScores.typeId == '8'}">
    <f:subview id="displayFillInTheBlank">
    <%@ include file="/jsf/evaluation/item/displayFillInTheBlank.jsp" %>
    </f:subview>
  </h:panelGroup>
  <h:panelGroup rendered="#{questionScores.typeId == '9'}">
    <f:subview id="displayMatching">
    <%@ include file="/jsf/evaluation/item/displayMatching.jsp" %>
    </f:subview>
  </h:panelGroup>
  <h:panelGroup rendered="#{questionScores.typeId == '2'}">
    <f:subview id="displayMultipleChoiceMultipleCorrect">
  <%@ include file="/jsf/evaluation/item/displayMultipleChoiceMultipleCorrect.jsp" %>
    </f:subview>
  </h:panelGroup>
  <h:panelGroup
    rendered="#{questionScores.typeId == '1' ||
                questionScores.typeId == '3'}">
    <f:subview id="displayMultipleChoiceSingleCorrect">
    <%@ include file="/jsf/evaluation/item/displayMultipleChoiceSingleCorrect.jsp" %>
    </f:subview>
  </h:panelGroup>
  <h:panelGroup rendered="#{questionScores.typeId == '5'}">
    <f:subview id="displayShortAnswer">
   <%@ include file="/jsf/evaluation/item/displayShortAnswer.jsp" %>
    </f:subview>
  </h:panelGroup>
  <h:panelGroup rendered="#{questionScores.typeId == '4'}">
    <f:subview id="displayTrueFalse">
    <%@ include file="/jsf/evaluation/item/displayTrueFalse.jsp" %>
    </f:subview>
  </h:panelGroup>
  </h:column>
  </h:dataTable>
 <p class="navModeQuestion">
<h:outputText value="Responses"/>
</p>

  <!-- LAST/ALL SUBMISSIONS; PAGER; ALPHA INDEX  -->
  <span class="leftNav">
     <!-- h:outputText value="#{msg.max_score_poss}" style="instruction"/-->
     <!-- h:outputText value="#{questionScores.maxScore}" style="instruction"/-->

     <h:outputText value="#{msg.view}" />
      <h:outputText value=" : " />
     <h:selectOneMenu value="#{questionScores.allSubmissions}" id="allSubmissions"
        required="true" onchange="document.forms[0].submit();" >
      <f:selectItem itemValue="false" itemLabel="#{msg.last_sub}" />
      <f:selectItem itemValue="true" itemLabel="#{msg.all_sub}" />
      <f:valueChangeListener
         type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreListener" />
     </h:selectOneMenu>
  </span>

<%--  THIS MIGHT BE FOR NEXT RELEASE

  <span class="rightNav">
    <!-- Need to hook up to the back end, DUMMIED -->
    <samigo:pagerButtons  formId="editTotalResults" dataTableId="myData"
      firstItem="1" lastItem="10" totalItems="50"
      prevText="Previous" nextText="Next" numItems="10" />
 </span>
END OF TEMPORARY OUT FOR THIS RELEASE --%>
    <span class="abc">
      <samigo:alphaIndex initials="#{questionScores.agentInitials}" />
    </span>

  <!-- STUDENT RESPONSES AND GRADING -->
  <!-- note that we will have to hook up with the back end to get N at a time -->
  <h:dataTable id="questionScoreTable" value="#{questionScores.agents}"
    var="description" style="listHier indnt2" columnClasses="textTable">

    <!-- NAME/SUBMISSION ID -->
    <h:column rendered="#{questionScores.anonymous eq 'false' && questionScores.sortType eq 'lastName'}">
     <f:facet name="header">
        <h:outputText value="#{msg.name}" />
     </f:facet>
     <h:panelGroup>
       <h:outputText value="<a name=\"" escape="false" />
       <h:outputText value="#{description.lastInitial}" />
       <h:outputText value="\"></a>" escape="false" />
       <h:commandLink action="studentScores" immediate="true">
         <h:outputText value="#{description.firstName}" />
         <h:outputText value=" " />
         <h:outputText value="#{description.lastName}" />
         <f:actionListener
            type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
         <f:actionListener
            type="org.sakaiproject.tool.assessment.ui.listener.evaluation.StudentScoreListener" />
         <f:param name="studentid" value="#{description.idString}" />
         <f:param name="studentName" value="#{description.firstName} #{description.lastName}" />
         <f:param name="publishedIdd" value="#{questionScores.publishedId}" />
         <f:param name="gradingData" value="#{description.assessmentGradingId}" />
       </h:commandLink>
     </h:panelGroup>
    </h:column>

    <h:column rendered="#{questionScores.anonymous eq 'false' && questionScores.sortType ne 'lastName'}">
     <f:facet name="header">
        <h:commandLink id="lastName" action="questionScores">
          <h:outputText value="#{msg.name}" />
        <f:actionListener
         type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
        <f:actionListener
           type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreListener" />
        <f:param name="sortBy" value="lastName" />
        </h:commandLink>
     </f:facet>
     <h:panelGroup>
       <h:outputText value="<a name=\"" escape="false" />
       <h:outputText value="#{description.lastInitial}" />
       <h:outputText value="\"></a>" escape="false" />
       <h:commandLink action="studentScores" immediate="true">
         <h:outputText value="#{description.firstName}" />
         <h:outputText value=" " />
         <h:outputText value="#{description.lastName}" />
         <f:actionListener
            type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
         <f:actionListener
            type="org.sakaiproject.tool.assessment.ui.listener.evaluation.StudentScoreListener" />
         <f:param name="studentid" value="#{description.idString}" />
         <f:param name="studentName" value="#{description.firstName} #{description.lastName}" />
         <f:param name="publishedIdd" value="#{questionScores.publishedId}" />
         <f:param name="gradingData" value="#{description.assessmentGradingId}" />
       </h:commandLink>
     </h:panelGroup>
    </h:column>

    <h:column rendered="#{questionScores.anonymous eq 'true' && questionScores.sortType ne 'assessmentGradingId'}">
     <f:facet name="header">
        <h:commandLink action="questionScores">
          <h:outputText value="#{msg.sub_id}" />
      <f:actionListener
         type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
        <f:actionListener
           type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreListener" />
        <f:param name="sortBy" value="assessmentGradingId" />
        </h:commandLink>
     </f:facet>
     <h:panelGroup>
       <h:commandLink action="studentScores" immediate="true">
         <h:outputText value="#{description.assessmentGradingId}" />
         <f:actionListener
            type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
         <f:actionListener
            type="org.sakaiproject.tool.assessment.ui.listener.evaluation.StudentScoreListener" />
         <f:param name="studentid" value="#{description.idString}" />
         <f:param name="studentName" value="#{description.assessmentGradingId}" />
         <f:param name="publishedIdd" value="#{questionScores.publishedId}" />
         <f:param name="gradingData" value="#{description.assessmentGradingId}" />
       </h:commandLink>

     </h:panelGroup>
    </h:column>

    <h:column rendered="#{questionScores.anonymous eq 'true' && questionScores.sortType eq 'assessmentGradingId'}">
     <f:facet name="header">
          <h:outputText value="#{msg.sub_id}" />
     </f:facet>
     <h:panelGroup>
       <h:commandLink action="studentScores" immediate="true">
         <h:outputText value="#{description.assessmentGradingId}" />
         <f:actionListener
            type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
         <f:actionListener
            type="org.sakaiproject.tool.assessment.ui.listener.evaluation.StudentScoreListener" />
         <f:param name="studentid" value="#{description.idString}" />
         <f:param name="studentName" value="#{description.assessmentGradingId}" />
         <f:param name="publishedIdd" value="#{questionScores.publishedId}" />
         <f:param name="gradingData" value="#{description.assessmentGradingId}" />
       </h:commandLink>
     </h:panelGroup>
    </h:column>

   <!-- STUDENT ID -->
    <h:column rendered="#{questionScores.anonymous eq 'false' && questionScores.sortType!='idString'}" >
     <f:facet name="header">
       <h:commandLink id="idString" action="questionScores" >
          <h:outputText value="#{msg.uid}" />
      <f:actionListener
         type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
        <f:actionListener
           type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreListener" />
        <f:param name="sortBy" value="idString" />
        </h:commandLink>
     </f:facet>
        <h:outputText value="#{description.idString}" />
    </h:column>

    <h:column rendered="#{questionScores.anonymous eq 'false' && questionScores.sortType eq 'idString'}" >
     <f:facet name="header">
       <h:outputText value="#{msg.uid}" />
     </f:facet>
        <h:outputText value="#{description.idString}" />
    </h:column>

    <!-- ROLE -->
    <h:column rendered="#{questionScores.sortType ne 'role'}">
     <f:facet name="header" >
        <h:commandLink id="role" action="questionScores">
          <h:outputText value="#{msg.role}" />
      <f:actionListener
         type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
        <f:actionListener
           type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreListener" />
        <f:param name="sortBy" value="role" />
        </h:commandLink>
     </f:facet>
        <h:outputText value="#{description.role}"/>
    </h:column>

    <h:column rendered="#{questionScores.sortType eq 'role'}">
     <f:facet name="header" >
       <h:outputText value="#{msg.role}" />
     </f:facet>
        <h:outputText value="#{description.role}"/>
    </h:column>

    <!-- DATE -->
    <h:column rendered="#{questionScores.sortType!='submittedDate'}">
     <f:facet name="header">
        <h:commandLink id="submittedDate" action="questionScores">
          <h:outputText value="#{msg.date}" />
      <f:actionListener
         type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
        <f:actionListener
          type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreListener" />
        <f:param name="sortBy" value="submittedDate" />
        </h:commandLink>
     </f:facet>
        <h:outputText value="#{description.submittedDate}">
         <f:convertDateTime pattern="#{genMsg.output_date_picker}"/>
        </h:outputText>

        <h:outputText styleClass="red" value="#{msg.all_late}" escape="false"
          rendered="#{description.isLate}"/>
    </h:column>

    <h:column rendered="#{questionScores.sortType=='submittedDate'}">
     <f:facet name="header">
       <h:outputText value="#{msg.date}" />
     </f:facet>
        <h:outputText value="#{description.submittedDate}">
         <f:convertDateTime pattern="#{genMsg.output_date_picker}"/>
        </h:outputText>
        <h:outputText styleClass="red" value="#{msg.all_late}" escape="false"
          rendered="#{description.isLate}"/>
    </h:column>

    <!-- SCORE -->
    <h:column rendered="#{questionScores.sortType!='totalAutoScore'}">
      <f:facet name="header">
        <h:commandLink id="score" action="questionScores">
          <h:outputText value="#{msg.score}" />
      <f:actionListener
         type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
          <f:actionListener
             type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreListener" />
        <f:param name="sortBy" value="totalAutoScore" />
        </h:commandLink>
      </f:facet>
      <h:inputText value="#{description.totalAutoScore}" size="5" id="qscore" required="false">
<f:validateDoubleRange/>
</h:inputText>
<br />
 <h:message for="qscore" style="color:red"/>
 </h:column>
    <h:column rendered="#{questionScores.sortType=='totalAutoScore'}">
      <f:facet name="header">
        <h:outputText value="#{msg.score}" />
      </f:facet>
      <h:inputText value="#{description.totalAutoScore}" size="5"  id="qscore2" required="false">
<f:validateDoubleRange/>
</h:inputText>

 <h:message for="qscore2" style="color:red"/>
    </h:column>

    <!-- ANSWER -->
    <h:column rendered="#{questionScores.sortType!='answer'}">
      <f:facet name="header">
        <h:commandLink id="answer" action="questionScores">
          <h:outputText value="#{msg.stud_resp}" />
      <f:actionListener
         type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
          <f:actionListener
             type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreListener" />
        <f:param name="sortBy" value="answer" />
        </h:commandLink>
      </f:facet>
      <h:outputText value="#{description.answer}" escape="false" />
    </h:column>

    <h:column rendered="#{questionScores.sortType=='answer'}">
      <f:facet name="header">
        <h:outputText value="#{msg.stud_resp}" />
      </f:facet>
      <h:outputText value="#{description.answer}" escape="false" />
    </h:column>

    <!-- COMMENT -->
    <h:column rendered="#{questionScores.sortType!='comments'}">
     <f:facet name="header">
      <h:commandLink id="comments" action="questionScores">
      <f:actionListener
         type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
        <f:actionListener
           type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreListener" />
        <h:outputText value="#{msg.comment}"/>
        <f:param name="sortBy" value="comments" />
      </h:commandLink>
     </f:facet>
     <samigo:wysiwyg rows="140" value="#{description.comments}" >
       <f:validateLength maximum="4000"/>
     </samigo:wysiwyg>
    </h:column>

    <h:column rendered="#{questionScores.sortType=='comments'}">
     <f:facet name="header">
        <h:outputText value="#{msg.comment}" />
     </f:facet>
     <samigo:wysiwyg rows="140" value="#{description.comments}" >
       <f:validateLength maximum="4000"/>
     </samigo:wysiwyg>
    </h:column>
  </h:dataTable>

<p class="act">
   <%-- <h:commandButton value="#{msg.save_exit}" action="author"/> --%>
   <h:commandButton styleClass="active" value="#{msg.save_cont}" action="questionScores" type="submit" >
      <f:actionListener
         type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreUpdateListener" />
      <f:actionListener
         type="org.sakaiproject.tool.assessment.ui.listener.evaluation.QuestionScoreListener" />
   </h:commandButton>
   <h:commandButton value="#{msg.cancel}" action="totalScores" immediate="true"/>
</p>
</h:form>

  <!-- end content -->
      </body>
    </html>
  </f:view>
