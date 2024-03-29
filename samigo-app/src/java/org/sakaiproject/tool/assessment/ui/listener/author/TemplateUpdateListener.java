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



package org.sakaiproject.tool.assessment.ui.listener.author;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.tool.assessment.data.dao.assessment.AssessmentAccessControl;
import org.sakaiproject.tool.assessment.data.dao.assessment.AssessmentFeedback;
import org.sakaiproject.tool.assessment.data.dao.assessment.AssessmentMetaData;
import org.sakaiproject.tool.assessment.data.dao.assessment.AssessmentTemplateData;
import org.sakaiproject.tool.assessment.data.dao.assessment.EvaluationModel;
import org.sakaiproject.tool.assessment.data.ifc.assessment.AssessmentAccessControlIfc;
import org.sakaiproject.tool.assessment.data.ifc.assessment.AssessmentBaseIfc;
import org.sakaiproject.tool.assessment.data.ifc.assessment.AssessmentFeedbackIfc;
import org.sakaiproject.tool.assessment.data.ifc.assessment.AssessmentTemplateIfc;
import org.sakaiproject.tool.assessment.data.ifc.assessment.EvaluationModelIfc;
import org.sakaiproject.tool.assessment.facade.AgentFacade;
import org.sakaiproject.tool.assessment.facade.TypeFacade;
import org.sakaiproject.tool.assessment.services.assessment.AssessmentService;
import org.sakaiproject.tool.assessment.ui.bean.author.TemplateBean;
import org.sakaiproject.tool.assessment.ui.bean.author.IndexBean;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;
import org.sakaiproject.tool.assessment.ui.listener.author.TemplateListener;
import org.sakaiproject.util.FormattedText;

/**
 * <p>Description: Action Listener for template updates</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organization: Sakai Project</p>
 * @author Ed Smiley
 * @version $Id$
 */

public class TemplateUpdateListener
    extends TemplateBaseListener
    implements ActionListener
{
    private static Log log = LogFactory.getLog(TemplateUpdateListener.class);

  /**
   * Normal listener method.
   * @param ae
   * @throws AbortProcessingException
   */
  public void processAction(ActionEvent ae) throws AbortProcessingException
  {
    FacesContext context = FacesContext.getCurrentInstance();

    //log.info("DEBUG: TEMPLATE UPDATE LISTENER.");
    //log.info("debugging ActionEvent: " + ae);
    //log.info("debug requestParams: " + requestParams);
    //log.info("debug reqMap: " + reqMap);
    TemplateBean templateBean = lookupTemplateBean(context);
    IndexBean templateIndex = (IndexBean) ContextUtil.lookupBean("templateIndex");


    
    String tempName=templateBean.getTemplateName();
    AssessmentService assessmentService = new AssessmentService();

    boolean isUnique=assessmentService.assessmentTitleIsUnique(templateBean.getIdString(),tempName,true);
    //log.debug("*** is unique="+isUnique);
    if(tempName!=null && (tempName.trim()).equals("")){
     	String err1=ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.TemplateMessages","templateName_empty");
	context.addMessage(null,new FacesMessage(err1));
        templateIndex.setOutcome("editTemplate");
	return;
    }
    if (!isUnique){
      String error=ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.TemplateMessages","duplicateName_error");
      context.addMessage(null,new FacesMessage(error));
      templateIndex.setOutcome("editTemplate");
      return;
    }
    
    if (templateBean.getValueMap().get("submissionModel_isInstructorEditable") != null && ((Boolean) templateBean.getValueMap().get("submissionModel_isInstructorEditable")).booleanValue()) {
    	if (templateBean.getSubmissionModel().equals(AssessmentAccessControlIfc.LIMITED_SUBMISSIONS.toString())) {
    		try {
   	   			String submissionsAllowed = templateBean.getSubmissionNumber().trim();
   	   			int submissionAllowed = Integer.parseInt(submissionsAllowed);
   	   			if (submissionAllowed < 1) {
   	   				throw new RuntimeException();
   	   			}
   			}
    		catch (RuntimeException e){
    			String error=ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.TemplateMessages","submissions_allowed_error");
    			context.addMessage(null,new FacesMessage(error));
    			templateIndex.setOutcome("editTemplate");
    			return;
    		}
   		}
   	}
    
    updateAssessment(templateBean);

    // reset the sortedTemplateList in IndexBean - daisyf
    TemplateListener lis = new TemplateListener();
    lis.processAction(null);

    // reset templateBean
    templateBean.setNewName(null);
    templateIndex.setOutcome("template");
  }

  /**
   * Update an existing assessment.
   * @param templateBean
   * @param template
   * @param props
   * @return
   * @throws java.lang.Exception
   */
  /**
   * @param templateBean
   * @param templateId template id or "0" if create new
   * @return true on success
   * @throws java.lang.Exception
   */
  public boolean updateAssessment(TemplateBean templateBean)
  {
    try
    {
      String templateIdString =  templateBean.getIdString();
      AssessmentService delegate = new AssessmentService();
      AssessmentBaseIfc template = null;
      if (templateIdString.equals("0"))
      {
        template = new AssessmentTemplateData();
        template.setAssessmentBaseId(new Long(0));
        AssessmentAccessControl aac = new AssessmentAccessControl();
        template.setAssessmentAccessControl(aac);
        aac.setAssessmentBase(template);
        EvaluationModel em = new EvaluationModel();
        template.setEvaluationModel(em);
        em.setAssessmentBase(template);
        AssessmentFeedback feedback = new AssessmentFeedback();
        template.setAssessmentFeedback(feedback);
        feedback.setAssessmentBase(template);
        template.setTypeId(TypeFacade.TEMPLATE_HOMEWORK);

        // Dunno what these are for, but it won't work without them.
        template.setStatus(AssessmentTemplateIfc.ACTIVE_STATUS);
        template.setParentId(new Long(0));
        template.setComments("comments");
        template.setInstructorNotification(new Integer(1));
        template.setTesteeNotification(new Integer(1));
        template.setMultipartAllowed(new Integer(1));
      }
      else
      {
        template =
          (delegate.getAssessmentTemplate(templateIdString)).getData();
      }

      template.setTitle(ContextUtil.processFormattedText(log, templateBean.getTemplateName()));
      if (templateBean.getTemplateAuthor() != null)
        templateBean.getValueMap().put
          ("author", ContextUtil.processFormattedText(log, templateBean.getTemplateAuthor()));
      template.setDescription(templateBean.getTemplateDescription());

      // Assessment Access Control
      AssessmentAccessControlIfc aac = template.getAssessmentAccessControl();
      if (aac == null)
      {
        aac = new AssessmentAccessControl();
        template.setAssessmentAccessControl(aac);
        aac.setAssessmentBase(template);
      }
      aac.setItemNavigation(new Integer(templateBean.getItemAccessType()));
      aac.setAssessmentFormat(new Integer(templateBean.getDisplayChunking()));
      aac.setItemNumbering(new Integer(templateBean.getQuestionNumbering()));
      aac.setSubmissionsSaved(new Integer(templateBean.getSubmissionModel()));
      
      if (templateBean.getValueMap().get("submissionModel_isInstructorEditable") != null && ((Boolean) templateBean.getValueMap().get("submissionModel_isInstructorEditable")).booleanValue()) {
    	  if (templateBean.getSubmissionModel().equals(AssessmentAccessControlIfc.UNLIMITED_SUBMISSIONS.toString()))
    	  {
    		  aac.setSubmissionsAllowed(null);
    		  aac.setUnlimitedSubmissions(Boolean.TRUE);
    	  }
    	  else{
    		  aac.setSubmissionsAllowed(new Integer(templateBean.getSubmissionNumber()));
    		  aac.setUnlimitedSubmissions(Boolean.FALSE);
    	  }
      }
      else { // if "Number of Submissions Allowed" is not editable, just default to unlimited
		  aac.setSubmissionsAllowed(null);
		  aac.setUnlimitedSubmissions(Boolean.TRUE);
      }
      aac.setLateHandling(new Integer(templateBean.getLateHandling()));
      aac.setAutoSubmit(new Integer(templateBean.getAutoSave()));

      // Evaluation Model
      EvaluationModelIfc model = template.getEvaluationModel();
      if (model == null)
      {
        model = new EvaluationModel();
        model.setAssessmentBase(template);
        template.setEvaluationModel(model);
      }
      model.setAssessmentBase(template);
      model.setAnonymousGrading
        (new Integer(templateBean.getAnonymousGrading()));
      model.setToGradeBook(templateBean.getToGradebook());
      model.setScoringType(new Integer(templateBean.getRecordedScore()));

      // Assessment Feedback
      AssessmentFeedbackIfc feedback = template.getAssessmentFeedback();
      if (feedback == null)
      {
        feedback = new AssessmentFeedback();
        feedback.setAssessmentBase(template);
        template.setAssessmentFeedback(feedback);
      }
      feedback.setFeedbackDelivery(new Integer(templateBean.getFeedbackType()));
      feedback.setFeedbackAuthoring(new Integer(templateBean.getFeedbackAuthoring()));

      Boolean canEditFeedbackComponent=(Boolean)templateBean.getValue("feedbackComponents_isInstructorEditable");
      // SAK-3573: looks like at some point the "feedbackComponents_isInstructorEditable" were being used
      // in place of "EditComponent" but 
      // 1) the changes was not done all the way (see TemplateBean, line 99 is missing) and 
      // 2) "EditComponent" was always set to 1 instead of being updated
      // correctly to provide backward compatibility to old data. -daisyf
      if (canEditFeedbackComponent.booleanValue())
	  feedback.setEditComponents(new Integer("1"));
      else
	  feedback.setEditComponents(new Integer("0"));

      feedback.setShowQuestionText
        (templateBean.getFeedbackComponent_QuestionText());
      feedback.setShowStudentResponse
        (templateBean.getFeedbackComponent_StudentResp());
      feedback.setShowCorrectResponse
        (templateBean.getFeedbackComponent_CorrectResp());
      feedback.setShowStudentScore
        (templateBean.getFeedbackComponent_StudentScore());
      feedback.setShowStudentQuestionScore
        (templateBean.getFeedbackComponent_StudentQuestionScore());
      feedback.setShowQuestionLevelFeedback
        (templateBean.getFeedbackComponent_QuestionLevel());
      feedback.setShowSelectionLevelFeedback
        (templateBean.getFeedbackComponent_SelectionLevel());
      feedback.setShowGraderComments
        (templateBean.getFeedbackComponent_GraderComments());
      feedback.setShowStatistics
        (templateBean.getFeedbackComponent_Statistics());

      //log.info("templateId = " + templateIdString);
      if (templateIdString.equals("0")) // New template
      {
        template.setCreatedBy(AgentFacade.getAgentString());
        template.setCreatedDate(new Date());
      }
      else
      {
        template.setCreatedBy(ContextUtil.lookupParam("createdBy"));
        SimpleDateFormat format = new SimpleDateFormat();
        //log.info("Date is " + templateBean.getCreatedDate());
        template.setCreatedDate(format.parse
          (ContextUtil.lookupParam("createdDate")));
      }
      template.setLastModifiedBy(AgentFacade.getAgentString());
      template.setLastModifiedDate(new Date());

      //** save template before dealing with meta data set
      delegate.save((AssessmentTemplateData)template);

      delegate.deleteAllMetaData((AssessmentTemplateData)template);

      log.debug("**** after deletion of meta data");
      HashSet set = new HashSet();
      Iterator iter = templateBean.getValueMap().keySet().iterator();
      while (iter.hasNext())
      {
        String label = (String) iter.next();
        String value = (String) templateBean.getValueMap().get(label).toString();
        //log.info("Label: " + label + ", Value: " + value);
        AssessmentMetaData data =
          new AssessmentMetaData(template, label, value);
        set.add(data);
      }
      template.setAssessmentMetaDataSet(set);

      delegate.save((AssessmentTemplateData)template);

    }
    catch (RuntimeException ex)
    {
      log.error(ex.getMessage(), ex);
      return false;
    } 
    catch (ParseException e) {
    	log.error(e.getMessage(), e);
        return false;
	}

    return true;
  }
}
