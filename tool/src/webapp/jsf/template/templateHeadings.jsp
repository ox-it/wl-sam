<p class="navIntraTool">
  <h:panelGroup rendered="#{authorization.adminQuestionPool or authorization.adminAssessment}">
   <h:commandLink accesskey="#{genMsg.a_assessment}" title="#{genMsg.t_assessment}" action="author" id="authorlink" immediate="true" rendered="#{authorization.adminAssessment}">
      <h:outputText value="#{genMsg.assessment}" />
       <f:actionListener
         type="org.sakaiproject.tool.assessment.ui.listener.author.AuthorActionListener" />
   </h:commandLink>
    <h:outputText value=" #{genMsg.separator} " rendered="#{authorization.adminAssessment}"/>
      <h:outputText value="#{genMsg.template}" />
    <h:outputText value=" #{genMsg.separator} " rendered="#{authorization.adminQuestionPool}"/>
    <h:commandLink accesskey="#{genMsg.a_pool}" title="#{genMsg.t_questionPool}" action="poolList" id="poolLink" immediate="true" rendered="#{authorization.adminQuestionPool}">
      <h:outputText value="#{genMsg.questionPool}" />
    </h:commandLink>
  </h:panelGroup>
</p>
