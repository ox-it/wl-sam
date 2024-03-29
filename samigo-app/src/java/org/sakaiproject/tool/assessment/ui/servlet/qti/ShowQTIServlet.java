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



package org.sakaiproject.tool.assessment.ui.servlet.qti;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.sakaiproject.tool.assessment.ui.bean.qti.XMLDisplay;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;

/**
 * <p>Title: Samigo</p>
 * <p>Description: Sakai Assessment Manager Export to QTI</p>
 * <p>Copyright: Copyright (c) 2004 Sakai Project</p>
 * <p>Organization: Sakai Project</p>
 * @author Ed Smiley
 * @version $Id: ShowMediaServlet.java 244 2005-06-24 04:06:14Z daisyf@stanford.edu $
 */

public class ShowQTIServlet extends HttpServlet
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 3855448630209417469L;
private static Log log = LogFactory.getLog(ShowQTIServlet.class);
  //private String xmlData;

  /**
   * passthu to post
   * @param req
   * @param res
   * @throws ServletException
   * @throws IOException
   */
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws
    ServletException, IOException
  {
    doPost(req, res);
  }

  /**
   * Get the faces context and display the contents of the XMLDisplay bean
   * @param req
   * @param res
   * @throws ServletException
   * @throws IOException
   */
  public void doPost(HttpServletRequest req, HttpServletResponse res) throws
    ServletException, IOException
  {
    XMLDisplay xmlDisp = (XMLDisplay)
      ContextUtil.lookupBeanFromExternalServlet("xml", req, res);
    String xml = xmlDisp.getXml();
    String fileName = xmlDisp.getName() + "." + xml;

    res.setHeader("Content-Disposition",
                  "inline" + ";filename=\"" + fileName + "\";");
    PrintWriter out = res.getWriter();
    log.info("debug show qti:");
    log.info("xml");
    out.print(xml);
  }

}
