/**********************************************************************************
* $URL$
* $Id$
***********************************************************************************
*
* Copyright (c) 2004-2005 The Regents of the University of Michigan, Trustees of Indiana University,
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

package org.sakaiproject.tool.assessment.ui.listener.delivery;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.sakaiproject.tool.assessment.ui.bean.delivery.DeliveryBean;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;
import javax.faces.component.UIOutput;

/**
 * <p>Title: Samigo</p>
 * <p>Purpose:  When student makes a recording for an audio question type
 * the audio recorder applet makes a copy of the local recording and posts it
 * to a special servlet,
 * @see org.sakaiproject.tool.assessment.ui.servlet.delivery.UploadAudioMediaServlet,
 *  that copies it to a designated file on the server.
 * When that student then posts the answer by pressing the Update button, the
 * actual grading record is made.
 * </p>
 * <p>Description: Sakai Assessment Manager</p>
 * <p>Copyright: Copyright (c) 2004 Sakai Project</p>
 * <p>Organization: Sakai Project</p>
 * @author Ed Smiley
 * @version $Id$
 */

public class AudioUploadActionListener implements ActionListener
{
  private static Log log = LogFactory.getLog(AudioUploadActionListener.class);
  private static ContextUtil cu;

  /**
   * ACTION.
   * @param ae the action event triggering the processAction method
   * @throws AbortProcessingException
   */
  public void processAction(ActionEvent ae) throws
    AbortProcessingException
  {
    log.info("AudioUploadActionListener.processAction() ");

    try {
      // get managed bean
      DeliveryBean delivery = (DeliveryBean) cu.lookupBean("delivery");

      // now find what component fired the event
      UIComponent component = ae.getComponent();
      // get the subview containing the audio question
      UIComponent parent = component.getParent();

      // get the its peer components from the parent
      List peers = parent.getChildren();

      // we're looking for the correct file upload
      // we are using debugging code for now.
      for (int i = 0; i < peers.size(); i++)
      {
        UIComponent peer = (UIComponent) peers.get(i);
        log.info("Component i: " + i);
        log.info("peer.getId(): " + peer.getId());
        log.info("peer.getFamily(): " + peer.getFamily());
        log.info("peer.getClass().getName(): " + peer.getClass().getName());
        if (peer instanceof UIOutput)
        {
          String value = "" + ((UIOutput) peer).getValue();
          log.info("peer is UIOutput: value = " + value);
        }
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
