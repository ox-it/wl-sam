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




package org.sakaiproject.tool.assessment.jsf.tag;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

/**
 * <p> </p>
 * <p>Description:<br />
 * This class is the tag handler that evaluates the <code>alphaIndex</code>
 * custom tag.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organization: Sakai Project</p>
 * @author Ed Smiley
 * @version $Id$
 */


public class AlphaIndexTag
    extends UIComponentTag
{

  private String initials = null;

  public String getInitials()
  {
    return initials;
  }

  public void setInitials(String initials)
  {
    this.initials = initials;
  }


  public String getComponentType()
  {
    return ("javax.faces.Output");
  }

  public String getRendererType()
  {
    return "AlphaIndex";
  }

  /**
   * set the component properties
   * @param component
   */
  protected void setProperties(UIComponent component)
  {

    super.setProperties(component);

    if (initials != null)
    {
      if (initials.startsWith("#"))
      {
        component.setValueBinding("initials",
          getFacesContext().getApplication().createValueBinding(getInitials()));
      }
      else
      {
        component.getAttributes().put("initials", initials);
      }
    }
  }

}
