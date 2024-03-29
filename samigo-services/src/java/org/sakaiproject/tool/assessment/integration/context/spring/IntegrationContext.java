/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/trunk/component/src/java/org/sakaiproject/tool/assessment/integration/context/spring/IntegrationContext.java $
 * $Id: IntegrationContext.java 9347 2006-05-13 04:13:19Z daisyf@stanford.edu $
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

package org.sakaiproject.tool.assessment.integration.context.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.sakaiproject.tool.assessment.integration.context.IntegrationContextFactory;
import org.sakaiproject.tool.assessment.integration.helper.ifc.AgentHelper;
import org.sakaiproject.tool.assessment.integration.helper.ifc.GradebookHelper;
import org.sakaiproject.tool.assessment.integration.helper.ifc.GradebookServiceHelper;
import org.sakaiproject.tool.assessment.integration.helper.ifc.PublishingTargetHelper;
import org.sakaiproject.tool.assessment.integration.helper.ifc.SectionAwareServiceHelper;
import org.sakaiproject.tool.assessment.integration.helper.ifc.ServerConfigurationServiceHelper;

/**
 * IntegrationContext is an internal implementation of IntegrationContextFactory.
 * It is the implementation class actually used by Spring and returned by its
 * abstract superclasses' (IntegrationContextFactory) getInstance method.
 * @author Ed Smiley
 */
public class IntegrationContext extends IntegrationContextFactory
{
  private static Log log = LogFactory.getLog(IntegrationContext.class);

  private boolean integrated;
  private AgentHelper agentHelper;
  private GradebookHelper gradebookHelper;
  private GradebookServiceHelper gradebookServiceHelper;
  private PublishingTargetHelper publishingTargetHelper;
  private SectionAwareServiceHelper sectionAwareServiceHelper;
  private ServerConfigurationServiceHelper serverConfigurationServiceHelper;

  // plain old Java bean properties, nothing mysterious here
  // just that we add mutators for Spring to hook, these are not
  // part of the factory api, so IntegrationContextFactory doesn't have
  // the setXXX() methods.
  public boolean isIntegrated()
  {
    return integrated;
  }
  public void setIntegrated(boolean integrated)
  {
    this.integrated = integrated;
  }
  public AgentHelper getAgentHelper()
  {
    return agentHelper;
  }
  public void setAgentHelper(AgentHelper agentHelper)
  {
    this.agentHelper = agentHelper;
  }
  public GradebookHelper getGradebookHelper()
  {
    return gradebookHelper;
  }
  public void setGradebookHelper(GradebookHelper gradebookHelper)
  {
    this.gradebookHelper = gradebookHelper;
  }
  public GradebookServiceHelper getGradebookServiceHelper()
  {
    return gradebookServiceHelper;
  }
  public void setGradebookServiceHelper(GradebookServiceHelper gradebookServiceHelper)
  {
    this.gradebookServiceHelper = gradebookServiceHelper;
  }
  public PublishingTargetHelper getPublishingTargetHelper()
  {
    return publishingTargetHelper;
  }
  public void setPublishingTargetHelper(PublishingTargetHelper publishingTargetHelper)
  {
    this.publishingTargetHelper = publishingTargetHelper;
  }

  public SectionAwareServiceHelper getSectionAwareServiceHelper()
  {
    return sectionAwareServiceHelper;
  }
  public void setSectionAwareServiceHelper(SectionAwareServiceHelper sectionAwareServiceHelper)
  {
    this.sectionAwareServiceHelper = sectionAwareServiceHelper;
  }

  public ServerConfigurationServiceHelper getServerConfigurationServiceHelper()
  {
    return serverConfigurationServiceHelper;
  }
  public void setServerConfigurationServiceHelper(ServerConfigurationServiceHelper serverConfigurationServiceHelper)
  {
    this.serverConfigurationServiceHelper = serverConfigurationServiceHelper;
  }
}
