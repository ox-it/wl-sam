/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/trunk/samigo-app/src/java/org/sakaiproject/tool/assessment/ui/listener/author/ExportAssessmentListener.java $
 * $Id: ExportAssessmentListener.java 9268 2006-05-10 21:27:24Z daisyf@stanford.edu $
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.tool.assessment.facade.AssessmentFacade;
import org.sakaiproject.tool.assessment.services.assessment.AssessmentService;
import org.sakaiproject.tool.assessment.ui.bean.author.AssessmentBean;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;

/**
 * <p>Title: Samigo</p>
 * <p>Description: Sakai Assessment Manager</p>
 * <p>Copyright: Copyright (c) 2004, 2005, 206, 2007 Sakai Project</p>
 * <p>Organization: Sakai Project</p>
 * @author Ed Smiley
 * @version $Id: ExportAssessmentListener.java 9268 2006-05-10 21:27:24Z daisyf@stanford.edu $
 */

public class ChooseExportTypeListener implements ActionListener
{
  private static Log log = LogFactory.getLog(ExportAssessmentListener.class);

  public ChooseExportTypeListener()
  {
  }

  public void processAction(ActionEvent ae) throws AbortProcessingException
  {
    String assessmentId = (String) ContextUtil.lookupParam("assessmentId");
    log.info("ExportAssessmentListener assessmentId="+assessmentId);
    
    AssessmentBean assessmentBean = (AssessmentBean) ContextUtil.lookupBean(
    "assessmentBean");
    AssessmentService assessmentService = new AssessmentService();
    AssessmentFacade assessment = assessmentService.getBasicInfoOfAnAssessment(assessmentId);
    assessmentBean.setAssessmentId(assessment.getAssessmentBaseId().toString());
    assessmentBean.setTitle(assessment.getTitle());
  }

}
