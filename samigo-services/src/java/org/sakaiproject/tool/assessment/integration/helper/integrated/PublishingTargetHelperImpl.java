/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/trunk/component/src/java/org/sakaiproject/tool/assessment/integration/helper/integrated/PublishingTargetHelperImpl.java $
 * $Id: PublishingTargetHelperImpl.java 9273 2006-05-10 22:34:28Z daisyf@stanford.edu $
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

package org.sakaiproject.tool.assessment.integration.helper.integrated;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.sakaiproject.tool.assessment.facade.AgentFacade;
import org.sakaiproject.tool.assessment.integration.helper.ifc.PublishingTargetHelper;

/**
 *
 * <p>Description:
 * This is an integrated context implementation helper delegate class for
 * the PublishingTarget class.
 * "Integrated" means that Samigo (Tests and Quizzes)
 * is running within the context of the Sakai portal and authentication
 * mechanisms, and therefore makes calls on Sakai for things it needs.</p>
 * <p>Note: To customize behavior you can add your own helper class to the
 * Spring injection via the integrationContext.xml for your context.
 * The particular integrationContext.xml to be used is selected by the
 * build process.
 * </p>
 * <p>Sakai Project Copyright (c) 2005</p>
 * <p> </p>
 * @author Ed Smiley <esmiley@stanford.edu>
 */

public class PublishingTargetHelperImpl implements PublishingTargetHelper
{
  private static Log log = LogFactory.getLog(PublishingTargetHelperImpl.class);

    //private org.sakaiproject.component.api.ComponentManager cm;

  public PublishingTargetHelperImpl()
  {
  }


  /**
   * Gets to whom you can publish.
   * @return map of key value pairs:
   *  e.g. "Authenticated Users"->"AUTHENTICATED_USERS"
   */
  public HashMap getTargets()
  {

     HashMap map = new HashMap();
     map.put("Anonymous Users", "ANONYMOUS_USERS");
     map.put(AgentFacade.getCurrentSiteName(), AgentFacade.getCurrentSiteId());
     return map;
  }
}
