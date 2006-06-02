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
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.tool.assessment.facade.AssessmentTemplateFacade;
import org.sakaiproject.tool.assessment.services.assessment.AssessmentService;
import org.sakaiproject.tool.assessment.ui.bean.author.IndexBean;
import org.sakaiproject.tool.assessment.ui.bean.author.TemplateBean;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;
import org.sakaiproject.tool.assessment.util.BeanSort;

/**
 * <p>Title: Samigo</p>2
 * <p>Description: Sakai Assessment Manager</p>
 * <p>Copyright: Copyright (c) 2004 Sakai Project</p>
 * <p>Organization: Sakai Project</p>
 * @author Ed Smiley
 * @version $Id$
 */

public class TemplateListener extends TemplateBaseListener
    implements ActionListener
{
  private static Log log = LogFactory.getLog(TemplateListener.class);
  private static ContextUtil cu;
  private static BeanSort bs;

  public TemplateListener()
  {
  }

  public void processAction(ActionEvent ae) throws AbortProcessingException
  {
    FacesContext context = FacesContext.getCurrentInstance();
    Map reqMap = context.getExternalContext().getRequestMap();
    Map requestParams = context.getExternalContext().getRequestParameterMap();
    //log.info("debugging ActionEvent: " + ae);
    //log.info("debug requestParams: " + requestParams);
    //log.info("debug reqMap: " + reqMap);

    // get service and managed bean
    AssessmentService assessmentService = new AssessmentService();
    IndexBean templateIndex = (IndexBean) cu.lookupBean(
                       "templateIndex");

    // look for some sort information passed as parameters
    processSortInfo(templateIndex);

    ArrayList templates = new ArrayList();
    try
    {
	FacesContext.getCurrentInstance().
	getExternalContext().getSessionMap().put("template", new TemplateBean());
	ArrayList list = assessmentService.getBasicInfoOfAllActiveAssessmentTemplates("title");
        Iterator iter = list.iterator();
        while (iter.hasNext())
        {
	 AssessmentTemplateFacade facade = (AssessmentTemplateFacade) iter.next();
         TemplateBean bean = new TemplateBean();
         bean.setTemplateName(facade.getTitle());
         bean.setIdString(facade.getAssessmentBaseId().toString());
         bean.setLastModified(facade.getLastModifiedDate().toString());
         templates.add(bean);
        }
      } catch (Exception e) {
	e.printStackTrace();
      }

     String sortProperty = templateIndex.getTemplateOrderBy();
     boolean sortAscending = templateIndex.isTemplateAscending();

     bs = new BeanSort(templates, sortProperty);
     if (templates != null)
     if (sortProperty.equals("lastModified"))
     {
       bs.toDateSort();
     }
     else
     {
       bs.toStringSort();
     }
     templates = (ArrayList)bs.sort();

     if (sortAscending==false)
     {
	Collections.reverse(templates);
     }
/*
     // debug
     for (int i=0; i<templates.size();i++){
       System.out.println("*****"+((TemplateBean)templates.get(i)).getLastModified());
     }
*/

     // get the managed bean, author and set the list
     templateIndex.setSortTemplateList(templates);
  }


/**
   * look at sort info from post and set bean accordingly
   * @param bean the select index managed bean
   */
  private void processSortInfo(IndexBean bean) {
    bean.setTemplateOrderBy("templateName");
    bean.setTemplateAscending(true);
    String templateOrder = cu.lookupParam("templateSortType");
    String tempAscending = cu.lookupParam("templateAscending");

    if (templateOrder != null && !templateOrder.trim().equals("")) {
      bean.setTemplateOrderBy(templateOrder);
    }

    if (tempAscending != null && !tempAscending.trim().equals("")) {
      try {
        bean.setTemplateAscending((Boolean.valueOf(tempAscending)).booleanValue());
      }
      catch (Exception ex) { //skip
      }
    }
  }

}