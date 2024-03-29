<%-- $Id$
include file for displaying short answer essay questions
--%>
<!--
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

<h:panelGrid columns="1" width="100%">	
<h:outputText value="#{question.text}"  escape="false"/>
<h:panelGroup rendered="#{questionScores.haveModelShortAnswer}">
<h:outputText value="#{evaluationMessages.model}"  escape="false"/>
<h:outputLink title="#{evaluationMessages.t_modelShortAnswer}"   value="#" onclick="javascript:window.open('modelShortAnswerQS.faces?idString=#{question.itemId}','modelShortAnswer','width=600,height=600,scrollbars=yes, resizable=yes');" onkeypress="javascript:window.open('modelShortAnswerQS.faces?idString=#{question.itemId}','modelShortAnswer','width=600,height=600,scrollbars=yes, resizable=yes');">
<h:outputText  value=" #{evaluationMessages.click_here}"/>
</h:outputLink>
</h:panelGroup>
</h:panelGrid>


