/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/trunk/component/src/java/org/sakaiproject/tool/assessment/services/shared/TypeService.java $
 * $Id: TypeService.java 9273 2006-05-10 22:34:28Z daisyf@stanford.edu $
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


package org.sakaiproject.tool.assessment.services.shared;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.sakaiproject.tool.assessment.facade.TypeFacade;
import org.sakaiproject.tool.assessment.services.PersistenceService;


/**
 * The QuestionPoolService calls the service locator to reach the
 * manager on the back end.
 * @author Rachel Gollub <rgollub@stanford.edu>
 */
public class TypeService
{
  private static Log log = LogFactory.getLog(TypeService.class);

  /**
   * Creates a new QuestionPoolService object.
   */
  public TypeService()  {
  }

  public TypeFacade getTypeById(String typeId)
  {
    try{
      return PersistenceService.getInstance().getTypeFacadeQueries().
          getTypeFacadeById(new Long(typeId));
    }
    catch(Exception e)
    {
      log.error(e); throw new RuntimeException(e);
    }
  }

  public List getFacadeListByAuthorityDomain(String authority, String domain)
  {
    try{
      return PersistenceService.getInstance().getTypeFacadeQueries().
          getFacadeListByAuthorityDomain(authority,domain);
    }
    catch(Exception e)
    {
      log.error(e); throw new RuntimeException(e);
    }
  }

  public List getListByAuthorityDomain(String authority, String domain)
  {
    try{
      return PersistenceService.getInstance().getTypeFacadeQueries().
          getListByAuthorityDomain(authority,domain);
    }
    catch(Exception e)
    {
      log.error(e); throw new RuntimeException(e);
    }
  }

  public List getFacadeItemTypes() {
    try{
      return PersistenceService.getInstance().getTypeFacadeQueries().
          getFacadeItemTypes();
    }
    catch(Exception e)
    {
      log.error(e); throw new RuntimeException(e);
    }
  }
}
