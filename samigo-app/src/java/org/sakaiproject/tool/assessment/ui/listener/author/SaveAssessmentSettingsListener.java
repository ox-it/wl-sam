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

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.tool.assessment.data.ifc.assessment.AssessmentAccessControlIfc;
import org.sakaiproject.tool.assessment.services.assessment.AssessmentService;
import org.sakaiproject.tool.assessment.ui.bean.author.AssessmentSettingsBean;
import org.sakaiproject.tool.assessment.ui.bean.author.AuthorBean;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;

/**
 * <p>Title: Samigo</p>2
 * <p>Description: Sakai Assessment Manager</p>
 * <p>Copyright: Copyright (c) 2004 Sakai Project</p>
 * <p>Organization: Sakai Project</p>
 * @author Ed Smiley
 * @version $Id$
 */

public class SaveAssessmentSettingsListener
    implements ActionListener
{
  private static Log log = LogFactory.getLog(SaveAssessmentSettingsListener.class);
  //private static final GradebookServiceHelper gbsHelper = IntegrationContextFactory.getInstance().getGradebookServiceHelper();
  //private static final boolean integrated = IntegrationContextFactory.getInstance().isIntegrated();

  public SaveAssessmentSettingsListener()
  {
  }

  public void processAction(ActionEvent ae) throws AbortProcessingException
  {
    FacesContext context = FacesContext.getCurrentInstance();

    AssessmentSettingsBean assessmentSettings = (AssessmentSettingsBean) ContextUtil.
        lookupBean("assessmentSettings");
    boolean error=false;
    String assessmentId=String.valueOf(assessmentSettings.getAssessmentId()); 
    AssessmentService assessmentService = new AssessmentService();
    SaveAssessmentSettings s= new SaveAssessmentSettings();
    String assessmentName=ContextUtil.processFormattedText(log, assessmentSettings.getTitle());
 
    // check if name is empty
    if(assessmentName!=null &&(assessmentName.trim()).equals("")){
     	String nameEmpty_err=ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.AssessmentSettingsMessages","assessmentName_empty");
	context.addMessage(null,new FacesMessage(nameEmpty_err));
	error=true;
    }

    // check if name is unique 
    if(!assessmentService.assessmentTitleIsUnique(assessmentId,assessmentName,false)){
	String nameUnique_err=ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.AssessmentSettingsMessages","assessmentName_error");
	context.addMessage(null,new FacesMessage(nameUnique_err));
	error=true;
    }
    
    // check if start date is valid
    if(!assessmentSettings.getIsValidStartDate()){
    	String startDateErr = ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.GeneralMessages","invalid_start_date");
    	context.addMessage(null,new FacesMessage(startDateErr));
    	error=true;
    }
    // check if due date is valid
    if(!assessmentSettings.getIsValidDueDate()){
    	String dueDateErr = ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.GeneralMessages","invalid_due_date");
    	context.addMessage(null,new FacesMessage(dueDateErr));
    	error=true;
    }
    // check if retract date is valid
    if(!assessmentSettings.getIsValidRetractDate()){
    	String retractDateErr = ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.GeneralMessages","invalid_retrack_date");
    	context.addMessage(null,new FacesMessage(retractDateErr));
    	error=true;
    }
    
    //  if timed assessment, does it has value for time
    Object time=assessmentSettings.getValueMap().get("hasTimeAssessment");
    boolean isTime=false;
    try
    {
      if (time != null)
      {
        isTime = ( (Boolean) time).booleanValue();
      }
    }
    catch (Exception ex)
    {
      // keep default
      log.warn("Expecting Boolean hasTimeAssessment, got: " + time);

    }
    if((isTime) &&((assessmentSettings.getTimeLimit().intValue())==0)){
	String time_err=ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.AssessmentSettingsMessages","timeSelect_error");
	context.addMessage(null,new FacesMessage(time_err));
        error=true;
    }
    
    String ipString = assessmentSettings.getIpAddresses().trim();  
     String[]arraysIp=(ipString.split("\n"));
     boolean ipErr=false;
     for(int a=0;a<arraysIp.length;a++){
	 String currentString=arraysIp[a];
	 if(!currentString.trim().equals("")){
	     if(a<(arraysIp.length-1))
		 currentString=currentString.substring(0,currentString.length()-1);           
	     if(!s.isIpValid(currentString)){
		 ipErr=true;
		 break;
	     }
	 }
	
     }
	if(ipErr){
	    error=true;
	    String  ip_err=ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.AssessmentSettingsMessages","ip_error");
	    context.addMessage(null,new FacesMessage(ip_err));

	}

	String unlimitedSubmissions = assessmentSettings.getUnlimitedSubmissions();
	if (unlimitedSubmissions != null && unlimitedSubmissions.equals(AssessmentAccessControlIfc.LIMITED_SUBMISSIONS.toString())) {
		try {
			String submissionsAllowed = assessmentSettings.getSubmissionsAllowed().trim();
			int submissionAllowed = Integer.parseInt(submissionsAllowed);
			if (submissionAllowed < 1) {
				throw new RuntimeException();
			}
		}
		catch (RuntimeException e){
			error=true;
			String  submission_err = ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.AssessmentSettingsMessages","submissions_allowed_error");
			context.addMessage(null,new FacesMessage(submission_err));
		}
	}
	
    //check feedback - if at specific time then time should be defined.
    if((assessmentSettings.getFeedbackDelivery()).equals("2")) {
    	if (assessmentSettings.getFeedbackDateString()==null || assessmentSettings.getFeedbackDateString().equals("")) {
    		error=true;
    		String  date_err=ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.AssessmentSettingsMessages","date_error");
    		context.addMessage(null,new FacesMessage(date_err));
    	}
    	else if(!assessmentSettings.getIsValidFeedbackDate()){
        	String feedbackDateErr = ContextUtil.getLocalizedString("org.sakaiproject.tool.assessment.bundle.GeneralMessages","invalid_feedback_date");
        	context.addMessage(null,new FacesMessage(feedbackDateErr));
        	error=true;
        }
    }

    if (error){
      assessmentSettings.setOutcomeSave("editAssessmentSettings");
      return;
    }
 
    assessmentSettings.setOutcomeSave("author");
    s.save(assessmentSettings);
    // reset the core listing in case assessment title changes
    AuthorBean author = (AuthorBean) ContextUtil.lookupBean(
                       "author");
 
    ArrayList assessmentList = assessmentService.getBasicInfoOfAllActiveAssessments(
                      author.getCoreAssessmentOrderBy(),author.isCoreAscending());
    // get the managed bean, author and set the list
    author.setAssessments(assessmentList);

    // goto Question Authoring page
    EditAssessmentListener editA= new EditAssessmentListener();
    editA.processAction(null);

  }

}
