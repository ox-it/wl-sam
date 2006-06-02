/**********************************************************************************
 * $URL$
 * $Id$
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


package org.sakaiproject.tool.assessment.data.dao.shared;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.db.api.SqlService;

/**
 * This class will be used to initialize the state of the application.
 * 
 * @author <a href="mailto:lance@indiana.edu">Lance Speelmon</a>
 * @version $Id$
 */
public class SakaiBootStrap
{
  private static final String SAKAI_SAMIGO_DDL_NAME = "sakai_samigo";

  private static final Log LOG = LogFactory.getLog(SakaiBootStrap.class);

  /** Dependency: SqlService. */
  private SqlService sqlService;

  /** Configuration: to run the ddl on init or not. */
  private boolean autoDdl = false;

  public SakaiBootStrap()
  {
    LOG.debug("new SakaiBootStrap()");

    ; // no behavior
  }

  public void init()
  {
    LOG.info("init()");

    sqlService = org.sakaiproject.db.cover.SqlService
        .getInstance();
    if (sqlService == null)
    {
      LOG.error("SqlService cannot be found!");
      throw new IllegalStateException("SqlService cannot be found!");
    }

    if (autoDdl)
    {
      LOG.debug("autoDdl enabled; running DDL...");
      sqlService.ddl(this.getClass().getClassLoader(), SAKAI_SAMIGO_DDL_NAME);
    }

    LOG.info("init() completed successfully");
  }

  /**
   * Configuration: to run the ddl on init or not.
   * 
   * @param value
   *        the auto ddl value.
   */
  public void setAutoDdl(String value)
  {
    if (LOG.isDebugEnabled())
    {
      LOG.debug("setAutoDdl(String " + value + ")");
    }

    autoDdl = new Boolean(value).booleanValue();
  }

}


