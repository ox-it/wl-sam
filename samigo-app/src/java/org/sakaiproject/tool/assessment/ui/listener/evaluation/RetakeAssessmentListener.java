/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/trunk/samigo-app/src/java/org/sakaiproject/tool/assessment/ui/listener/author/ConfirmRemovePartListener.java $
 * $Id: ConfirmRemovePartListener.java 16897 2006-10-09 00:28:33Z ktsao@stanford.edu $
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

package org.sakaiproject.tool.assessment.ui.listener.evaluation;

import java.util.Date;
import java.util.List;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.sakaiproject.tool.assessment.data.dao.grading.StudentGradingSummaryData;
import org.sakaiproject.tool.assessment.facade.AgentFacade;
import org.sakaiproject.tool.assessment.services.GradingService;
import org.sakaiproject.tool.assessment.ui.bean.evaluation.RetakeAssessmentBean;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;

/**
 * <p>Title: Samigo</p>
 * <p>Description: Sakai Assessment Manager</p>
 * <p>Copyright: Copyright (c) 2007 Sakai Project</p>
 * <p>Organization: Sakai Project</p>
 * @author Ed Smiley
 * @version $Id: ConfirmRemovePartListener.java 16897 2006-10-09 00:28:33Z ktsao@stanford.edu $
 */

public class RetakeAssessmentListener implements ActionListener {
	//private static Log log = LogFactory.getLog(ConfirmRemovePartListener.class);

	public RetakeAssessmentListener() {
	}

	public void processAction(ActionEvent ae) throws AbortProcessingException {
		RetakeAssessmentBean retakeAssessment = (RetakeAssessmentBean) ContextUtil.lookupBean("retakeAssessment");
		GradingService gradingService = new GradingService();
		StudentGradingSummaryData studentGradingSummaryData = (StudentGradingSummaryData) retakeAssessment.getStudentGradingSummaryDataMap().get(retakeAssessment.getAgentId());
		if (studentGradingSummaryData == null) {
			studentGradingSummaryData = new StudentGradingSummaryData();
			studentGradingSummaryData.setPublishedAssessmentId(retakeAssessment.getPublishedAssessmentId());
			studentGradingSummaryData.setAgentId(retakeAssessment.getAgentId());
			studentGradingSummaryData.setNumberRetake(Integer.valueOf(1));
		    studentGradingSummaryData.setCreatedBy(AgentFacade.getAgentString());
			studentGradingSummaryData.setCreatedDate(new Date());
			studentGradingSummaryData.setLastModifiedBy(AgentFacade.getAgentString());
			studentGradingSummaryData.setLastModifiedDate(new Date());
		}
		else {
			int updatedNumberRetake = studentGradingSummaryData.getNumberRetake().intValue() + 1;
			studentGradingSummaryData.setNumberRetake(Integer.valueOf(updatedNumberRetake));
			studentGradingSummaryData.setLastModifiedBy(AgentFacade.getAgentString());
			studentGradingSummaryData.setLastModifiedDate(new Date());
		}
		
		gradingService.saveStudentGradingSummaryData(studentGradingSummaryData);
	}

}
