/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/trunk/component/src/java/org/sakaiproject/tool/assessment/facade/AuthorizationFacade.java $
 * $Id: AuthorizationFacade.java 9273 2006-05-10 22:34:28Z daisyf@stanford.edu $
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

package org.sakaiproject.tool.assessment.facade;

import java.io.Serializable;

/**
 *
 * An Authorization Facade
 *
 * @author Rachel Gollub <rgollub@stanford.edu>
 * @version 1.0
 */
public class AuthorizationFacade implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 4982008711609686144L;

public static boolean isAuthorized
    (String agentId, String function, String qualifier)
  {
    return true;
  }

  public static boolean createAuthorization
    (String agentId, String function, String qualifier)
  {
    return true;
  }
}
