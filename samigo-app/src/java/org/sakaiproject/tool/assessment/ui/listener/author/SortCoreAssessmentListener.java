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
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.tool.assessment.facade.AssessmentFacadeQueries;
import org.sakaiproject.tool.assessment.services.assessment.AssessmentService;
import org.sakaiproject.tool.assessment.ui.bean.author.AuthorBean;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;

/**
 * <p>Description: SortCoreAssessmentListener</p>
 */

public class SortCoreAssessmentListener
    implements ActionListener
{
  private static Log log = LogFactory.getLog(SortCoreAssessmentListener.class);

  public SortCoreAssessmentListener()
  {
  }

  public void processAction(ActionEvent ae) throws AbortProcessingException
  {
    // get service and managed bean
    AssessmentService assessmentService = new AssessmentService();
    AuthorBean author = (AuthorBean) ContextUtil.lookupBean(
                       "author");

    // look for some sort information passed as parameters
    processSortInfo(author);

    //String orderBy = (String) FacesContext.getCurrentInstance().
    //   getExternalContext().getRequestParameterMap().get("coreOrderBy");
    //author.setCoreAssessmentOrderBy(orderBy);

    ArrayList assessmentList = new ArrayList();

      assessmentList = assessmentService.getBasicInfoOfAllActiveAssessments(
        this.getCoreOrderBy(author), author.isCoreAscending());

    // get the managed bean, author and set the list
    author.setAssessments(assessmentList);

  }

/**
   * get orderby parameter for takable table
   * @param select the SelectAssessment bean
   * @return
   */
  private String getCoreOrderBy(AuthorBean author) {
    String sort = author.getCoreAssessmentOrderBy();
    String returnType =  AssessmentFacadeQueries.TITLE;
    if (sort != null && sort.equals("lastModifiedDate"))
    {
	returnType = AssessmentFacadeQueries.LASTMODIFIEDDATE;
    }

    return returnType;
  }

/**
   * look at sort info from post and set bean accordingly
   * @param bean the select index managed bean
   */
  private void processSortInfo(AuthorBean bean) {
    String coreOrder = ContextUtil.lookupParam("coreSortType");
    String coreAscending = ContextUtil.lookupParam("coreAscending");

    if (coreOrder != null && !coreOrder.trim().equals("")) {
      bean.setCoreAssessmentOrderBy(coreOrder);
    }

    if (coreAscending != null && !coreAscending.trim().equals("")) {
      try {
        bean.setCoreAscending((Boolean.valueOf(coreAscending)).booleanValue());
      }
      catch (Exception ex) { //skip
        log.warn(ex.getMessage());
      }
    }
    else
    {
	bean.setCoreAscending(true);
    }

  }

}
