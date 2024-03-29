/**********************************************************************************
* $URL$
* $Id$
***********************************************************************************
*
* Copyright (c) 2005 The Regents of the University of Michigan, Trustees of Indiana University,
*                  Board of Trustees of the Leland Stanford, Jr., University, and The MIT Corporation
*
* Licensed under the Educational Community License Version 1.0 (the "License");
* By obtaining, using and/or copying this Original Work, you agree that you have read,
* understand, and will comply with the terms and conditions of the Educational Community License.
* You may obtain a copy of the License at:
*
*      http://cvs.sakaiproject.org/licenses/license_1_0.html
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
* INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
* AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
* DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
**********************************************************************************/
package test.org.sakaiproject.tool.assessment.business.entity.helper;

import java.io.InputStream;
import java.util.ArrayList;
//import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.w3c.dom.Document;
import org.sakaiproject.tool.assessment.facade.PublishedAssessmentFacade;
import org.sakaiproject.tool.assessment.qti.constants.QTIVersion;
import org.sakaiproject.tool.assessment.qti.helper.AuthoringHelper;
import org.sakaiproject.tool.assessment.qti.helper.AuthoringXml;
import org.sakaiproject.tool.assessment.services.assessment.AssessmentService;
import org.sakaiproject.tool.assessment.qti.util.XmlUtil;

/**
 * <p>Test bed for QTI utilities.</p>
 * <p> </p>
 * <p> </p>
 * @author Ed Smiley esmiley@stanford.edu
 * @version $Id$
 */

public class QTITester {
  private static Log log = LogFactory.getLog(QTITester.class);
//  private static boolean useContextPath = true;
  private static boolean useContextPath = false;
//  private static int version = QTIVersion.VERSION_1_2;
  private static int version = QTIVersion.VERSION_2_0;

  public static void main(String[] args) {
//    log.debug("testing: AuthoringHelper");
//    testAuthoringHelper();
//    log.debug("<!--testing: AuthoringXml templates-->");
//    testAuthoringXmlTemplates();
    log.debug("<!--testing: AuthoringXml routines-->");
    testAuthoringXmlRoutines();

    }
  
  /*
  private static void testAuthoringHelper()
  {
    AuthoringHelper authHelper = new AuthoringHelper(QTIVersion.VERSION_1_2);
    AssessmentService aService = new AssessmentService();
    ArrayList list = aService.getAllAssessments(1,1,"title");
    AuthoringXml ax = new AuthoringXml(version);

    for (int i = 0; i < list.size(); i++)
    {
      PublishedAssessmentFacade pub = (PublishedAssessmentFacade) list.get(
        i);
      String pubid = pub.getAssessmentId().toString();
      log.debug("testing: " + pubid);
      log.debug(
        "=======================================================");
      InputStream is = ax.getTemplateInputStream(AuthoringXml.ASSESSMENT);
      Document doc = authHelper.getAssessment(pubid, is);
      log.debug(doc.toString());
      log.debug(
        "=======================================================");
    }
  }
  
  private static void testAuthoringXmlTemplates()
  {
    AuthoringXml ax = new AuthoringXml(version);
    String[] template =
      {
      ax.ASSESSMENT, //   "assessmentTemplate.xml";
      ax.SECTION, //   "sectionTemplate.xml";
      ax.ITEM_AUDIO, //   "audioRecordingTemplate.xml";
      ax.ITEM_ESSAY, //   "essayTemplate.xml";
      ax.ITEM_FIB, //   "fibTemplate.xml";
      ax.ITEM_FIN, //   "finTemplate.xml";
      ax.ITEM_FILE, //   "fileUploadTemplate.xml";
      ax.ITEM_MATCH, //   "matchTemplate.xml";
      ax.ITEM_MCMC, //   "mcMCTemplate.xml";
      ax.ITEM_MCSC, //   "mcSCTemplate.xml";
      ax.ITEM_SURVEY, //   "mcSurveyTemplate.xml";
      ax.ITEM_TF, //   "trueFalseTemplate.xml";
      ax.SURVEY_10, //   SURVEY_PATH + "10.xml";
      ax.SURVEY_5, //   SURVEY_PATH + "5.xml";
      ax.SURVEY_AGREE, //   SURVEY_PATH + "AGREE.xml";
      ax.SURVEY_AVERAGE, //   SURVEY_PATH + "AVERAGE.xml";
      ax.SURVEY_EXCELLENT, //   SURVEY_PATH +        "EXCELLENT.xml";
      ax.SURVEY_STRONGLY, //   SURVEY_PATH +        "STRONGLY_AGREE.xml";
      ax.SURVEY_UNDECIDED, //   SURVEY_PATH +        "UNDECIDED.xml";
      ax.SURVEY_YES, //   SURVEY_PATH + "YES.xml";
      };

    for (int i = 0; i < template.length; i++)
    {
      log.debug("<!--=======================================================");
      log.debug("testing: " + template[i]);
      log.debug("=======================================================-->");
      InputStream is = null;

      if (useContextPath)
      {
        is = ax.getTemplateInputStream(template[i]);
      }
      else
      {
      is = ax.getTemplateInputStream(template[i]);
      }
      log.debug("<!--=======================================================-->");
      log.debug(ax.getTemplateAsString(is));
      log.debug("<!--=======================================================-->");

    }

  }
  */
  
  public static void testAuthoringXmlRoutines()
  {
    AuthoringXml ax = new AuthoringXml(version);
    Document assessmentXml = null;
    Document sectionXml = null;
    InputStream is = null;

    is = ax.getTemplateInputStream(ax.ASSESSMENT);

    assessmentXml = ax.readXMLDocument(is);
    log.debug("<!--============= assessment ==============================-->");
    log.debug(XmlUtil.getDOMString(assessmentXml));
    log.debug("<!--=======================================================-->");

    is = ax.getTemplateInputStream(ax.SECTION);

    sectionXml = ax.readXMLDocument(is);
    log.debug("<!--============= section ================================-->");
    log.debug(XmlUtil.getDOMString(sectionXml));
    log.debug("<!--=======================================================-->");
    try
    {
      assessmentXml = ax.update(assessmentXml, "questestinterop/assessment/@ident",
            "test_ident");
      assessmentXml = ax.update(assessmentXml, "questestinterop/assessment/@title",
            "this is a title");
    }
    catch (Exception ex)
    {
      log.error("oops: " + ex);
    }
    log.debug("<!--============= modified assessment =====================-->");
    log.debug(XmlUtil.getDOMString(assessmentXml));
    log.debug("<!--=======================================================-->");
    try
    {
      sectionXml = ax.update(sectionXml, "section/@ident",
            "test_section_ident");
      sectionXml = ax.update(sectionXml, "section/@title",
            "this is a section title");
    }
    catch (Exception ex)
    {
      log.error("oops: " + ex);
    }
    log.debug("<!--============= modified section ===============-->");
    log.debug(XmlUtil.getDOMString(sectionXml));
    log.debug("<!--=======================================================-->");

    try {
      ax.addElement(assessmentXml, "questestinterop/assessment",
                    sectionXml.getDocumentElement());
      ax.addAttribute(assessmentXml, "questestinterop/assessment",
                      "custom_attribute");
      sectionXml = ax.update(assessmentXml,
                             "questestinterop/assessment/@custom_attribute",
                             "custom_value");
    }
    catch (Exception ex) {
      log.error("oops: " + ex);
    }
    log.debug("<!--============= modified assessment and section ===============-->");
    log.debug(XmlUtil.getDOMString(sectionXml));
    log.debug("<!--=======================================================-->");


  }

}
