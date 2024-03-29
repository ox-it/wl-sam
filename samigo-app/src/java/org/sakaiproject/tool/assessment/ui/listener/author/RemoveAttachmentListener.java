/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/trunk/samigo-app/src/java/org/sakaiproject/tool/assessment/ui/listener/shared/RemoveMediaListener.java $
 * $Id: RemoveMediaListener.java 9268 2006-05-10 21:27:24Z daisyf@stanford.edu $
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


package org.sakaiproject.tool.assessment.ui.listener.author;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.sakaiproject.tool.assessment.data.ifc.assessment.AttachmentIfc;
import org.sakaiproject.tool.assessment.services.assessment.AssessmentService;
import org.sakaiproject.tool.assessment.ui.bean.author.AttachmentBean;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;

/**
 * <p>Title: Samigo</p>
 * <p>Description: Sakai Assessment Manager</p>
 * <p>Copyright: Copyright (c) 2004 Sakai Project</p>
 * <p>Organization: Sakai Project</p>
 * @version $Id: RemoveMediaListener.java 9268 2006-05-10 21:27:24Z daisyf@stanford.edu $
 */

public class RemoveAttachmentListener implements ActionListener
{
  //private static Log log = LogFactory.getLog(RemoveAttachmentListener.class);

  public RemoveAttachmentListener()
  {
  }

  public void processAction(ActionEvent ae) throws AbortProcessingException
  {
    AttachmentBean attachmentBean = (AttachmentBean) ContextUtil.lookupBean(
        "attachmentBean");
    AssessmentService assessmentService = new AssessmentService();

    // #1. get all the info need from bean
    String attachmentId = attachmentBean.getAttachmentId().toString();
    Long attachmentType = attachmentBean.getAttachmentType();
    if ((AttachmentIfc.ITEM_ATTACHMENT).equals(attachmentType))
      assessmentService.removeItemAttachment(attachmentId);
    else if ((AttachmentIfc.SECTION_ATTACHMENT).equals(attachmentType))
      assessmentService.removeSectionAttachment(attachmentId);
    else if ((AttachmentIfc.ASSESSMENT_ATTACHMENT).equals(attachmentType))
      assessmentService.removeAssessmentAttachment(attachmentId);
  }

}
