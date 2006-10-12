<%-- $Id: $
include file for displaying fill in the numeric questions
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

<h:outputText value="#{question.description}" escape="false"/>
<f:verbatim><br /></f:verbatim>
<h:outputText value="#{question.text}"  escape="false"/>
<h:dataTable value="#{question.itemTextArray}" var="itemText">
 <h:column>
 <h:dataTable value="#{itemText.answerArraySorted}" var="answer">
  <h:column>
    <h:graphicImage id="image2" rendered="#{answer.isCorrect}"
      alt="#{msg.alt_correct}" url="/images/delivery/checkmark.gif" >
     </h:graphicImage>
    <h:graphicImage id="image3" rendered="#{!answer.isCorrect}"
      alt="" url="/images/delivery/spacer.gif" >
     </h:graphicImage>
  </h:column>
  <h:column>
   <h:outputText value="#{answer.sequence}#{msg.dot} #{answer.text}" escape="false" />
  </h:column>
 </h:dataTable>
 </h:column>
</h:dataTable>

