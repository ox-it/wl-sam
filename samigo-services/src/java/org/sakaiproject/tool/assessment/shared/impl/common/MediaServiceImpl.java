/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2005, 2006 The Sakai Foundation.
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


package org.sakaiproject.tool.assessment.shared.impl.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.sakaiproject.tool.assessment.services.shared.MediaService;
import org.sakaiproject.tool.assessment.services.CommonServiceException;
import org.sakaiproject.tool.assessment.shared.api.common.MediaServiceAPI;


/**
 * Implements the shared interface to control media information.
 * @author Ed Smiley <esmiley@stanford.edu>
 */
public class MediaServiceImpl implements MediaServiceAPI
{
  private static Log log = LogFactory.getLog(MediaServiceImpl.class);

  public void remove(String mediaId)
  {
    try
    {
      MediaService service = new MediaService();
      service.remove(mediaId);
    }
    catch (Exception ex)
    {
      throw new CommonServiceException(ex);
    }
  }

}
