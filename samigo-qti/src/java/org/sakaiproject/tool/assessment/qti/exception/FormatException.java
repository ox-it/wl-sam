/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/trunk/component/src/java/org/sakaiproject/tool/assessment/qti/exception/FormatException.java $
 * $Id: FormatException.java 9274 2006-05-10 22:50:48Z daisyf@stanford.edu $
 ***********************************************************************************
 *
 * Copyright (c) 2005, 2006 The Sakai Foundation.
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



package org.sakaiproject.tool.assessment.qti.exception;


/**
 * <p>Title: NavigoProject.org</p>
 * <p>Description: OKI based implementation</p>
 * <p>Copyright: Copyright 2003 Trustees of Indiana University</p>
 * <p>Company: </p>
 * @author <a href="mailto:lance@indiana.edu">Lance Speelmon</a>
 * @version $Id: FormatException.java 9274 2006-05-10 22:50:48Z daisyf@stanford.edu $
 */
public class FormatException
  extends RuntimeException
{
  /**
   * Creates a new FormatException object.
   *
   * @param message DOCUMENTATION PENDING
   */
  public FormatException(String message)
  {
    super(message);
  }

  /**
   * Creates a new FormatException object.
   *
   * @param message DOCUMENTATION PENDING
   * @param cause DOCUMENTATION PENDING
   */
  public FormatException(String message, Throwable cause)
  {
    super(message, cause);
  }

  /**
   * Creates a new FormatException object.
   *
   * @param cause DOCUMENTATION PENDING
   */
  public FormatException(Throwable cause)
  {
    super(cause);
  }
}
