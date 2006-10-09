<%-- $Id: deliverShortAnswer.jsp 14269 2006-09-07 16:56:32Z daisyf@stanford.edu $
include file for delivering short answer essay questions
should be included in file importing DeliveryMessages
--%>
<!--
* $Id: deliverShortAnswer.jsp 14269 2006-09-07 16:56:32Z daisyf@stanford.edu $
<%--
***********************************************************************************
*
* Copyright (c) 2005 The Regents of the University of Michigan, Trustees of Indiana University,
*                  Board of Trustees of the Leland Stanford, Jr., University, and The MIT Corporation
*
* Licensed under the Educational Community License Version 1.0 (the "License");
* By obtaining, using and/or copying this Original Work, you agree that you have read,
* understand, and will comply with the terms and conditions of the Educational Community License.
* You may obtain a copy of the License at:
*
*      http://cvs.sakaiproject.org/licenses/license_1_0.html
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
* INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
* AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
* DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
**********************************************************************************/
--%>
-->
<h:outputText value="#{question.text}"  escape="false"/>

<!-- ATTACHMENTS -->
<%@ include file="/jsf/delivery/item/attachment.jsp" %>

<h:panelGroup 
     rendered="#{!(delivery.actionString=='reviewAssessment'
            || delivery.actionString=='gradeAssessment')}">
<f:verbatim><br/><br/></f:verbatim>
<h:outputText value="#{msg.maxSAText}"/>
</h:panelGroup> 
<f:verbatim><br/></f:verbatim>
<h:inputTextarea rows="20" cols="80" value="#{question.responseText}" 
   disabled="#{delivery.actionString=='reviewAssessment'
            || delivery.actionString=='gradeAssessment'}" >
<f:validateLength maximum="4000"/>
</h:inputTextarea>

<f:verbatim><br /></f:verbatim>
<h:selectBooleanCheckbox value="#{question.review}" id="mark_for_review"
   rendered="#{(delivery.actionString=='previewAssessment'
                || delivery.actionString=='takeAssessment'
                || delivery.actionString=='takeAssessmentViaUrl')
             && delivery.navigation ne '1'}" />

<h:outputLabel for="mark_for_review" value="#{msg.mark}"
  rendered="#{(delivery.actionString=='previewAssessment'
                || delivery.actionString=='takeAssessment'
                || delivery.actionString=='takeAssessmentViaUrl')
             && delivery.navigation ne '1'}" />

<h:panelGroup rendered="#{delivery.feedback eq 'true'}">
  <f:verbatim><br /></f:verbatim>
  <h:panelGroup rendered="#{delivery.feedbackComponent.showCorrectResponse && !delivery.noFeedback=='true'&& question.modelAnswerIsNotEmpty}" >
    <f:verbatim><b></f:verbatim>
    <h:outputLabel for="answerKeyMC" value="#{msg.model} " />
     <f:verbatim></b></f:verbatim>

	<h:outputLink title="#{msg.t_modelShortAnswer}"   value="#" onclick="javascript:window.open('modelShortAnswer.faces?idString=#{question.itemData.itemId}','modelShortAnswer','width=600,height=600,scrollbars=yes, resizable=yes');" onkeypress="javascript:window.open('modelShortAnswer.faces?idString=#{question.itemData.itemId}','modelShortAnswer','width=600,height=600,scrollbars=yes, resizable=yes');">
	<h:outputText  value="#{msg.click_here}"/>
    </h:outputLink>

<%-- alert screen is a problem 'cos comment often contains html tag added in WYSIWYG
    <h:outputLink title="#{msg.t_key}" value="#" onclick="javascript:window.alert('#{question.keyInUnicode}');"  onkeypress="javascript:window.alert('#{question.keyInUnicode}');" >
    <h:outputText  value="#{msg.click}" />
    </h:outputLink>
--%>

  </h:panelGroup>
  <h:panelGroup rendered="#{delivery.feedbackComponent.showItemLevel && !delivery.noFeedback=='true' && question.feedbackIsNotEmpty}">
    <f:verbatim><br /></f:verbatim>
    <f:verbatim><b></f:verbatim>
    <h:outputLabel for="feedSC" value="#{msg.feedback}#{msg.column} " />
    <f:verbatim></b></f:verbatim>
    <h:outputText id="feedSC" value="#{question.feedback}" escape="false" />
  </h:panelGroup>
  <h:panelGroup rendered="#{delivery.feedbackComponent.showGraderComment && !delivery.noFeedback=='true' && question.gradingCommentIsNotEmpty}">
    <f:verbatim><br /></f:verbatim>
    <f:verbatim><b></f:verbatim>
    <h:outputLabel for="commentSC" value="#{msg.comment}#{msg.column} " />
    <f:verbatim></b></f:verbatim>
    <h:outputText id="commentSC" value="#{question.gradingComment}"
      escape="false" />
  </h:panelGroup>
</h:panelGroup>