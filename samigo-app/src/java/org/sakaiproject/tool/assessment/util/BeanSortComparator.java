/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/trunk/component/src/java/org/sakaiproject/tool/assessment/util/BeanSortComparator.java $
 * $Id: BeanSortComparator.java 9273 2006-05-10 22:34:28Z daisyf@stanford.edu $
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


package org.sakaiproject.tool.assessment.util;

import java.io.Serializable;

import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.beanutils.BeanUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DOCUMENTATION PENDING
 *
 * @author $author$
 * @version $Id: BeanSortComparator.java 9273 2006-05-10 22:34:28Z daisyf@stanford.edu $
 */
public class BeanSortComparator
  implements Comparator
{
  private static Log log = LogFactory.getLog(BeanSortComparator.class);
  private String propertyName;

  /**
   * The only public constructor.  Requires a valid property name for a a Java
   * Bean as a sole parameter.
   *
   * @param propertyName the property name for Java Bean to sort by
   */
  public BeanSortComparator(String propertyName)
  {
    this.propertyName = propertyName;
  }

  /**
   * Creates a new BeanSortComparator object.
   */
  protected BeanSortComparator()
  {
  }
  ;

  /**
   * standard compare method
   *
   * @param o1 object
   * @param o2 object
   *
   * @return lt, eq, gt zero depending on whether o1 lt, eq, gt o2
   */
  public int compare(Object o1, Object o2)
  {
    Map m1 = describeBean(o1);
    Map m2 = describeBean(o2);
    String s1 = (String) m1.get(propertyName);
    String s2 = (String) m2.get(propertyName);

    // we do not want to use null values for sorting
    if(s1 == null)
    {
      s1 = "";
    }

    if(s2 == null)
    {
      s2 = "";
    }

    // Deal with n/a case
    if (s1.toLowerCase().startsWith("n/a")
        && !s2.toLowerCase().startsWith("n/a"))
      return 1;

    if (s2.toLowerCase().startsWith("n/a") &&
        !s1.toLowerCase().startsWith("n/a"))
      return -1;

    // Take out tags
    return s1.replaceAll("<.*?>", "").toLowerCase().compareTo
      (s2.replaceAll("<.*?>", "").toLowerCase());
  }

  /**
   * protected utility method to wrap BeanUtils
   *
   * @param o DOCUMENTATION PENDING
   *
   * @return DOCUMENTATION PENDING
   *
   * @throws java.lang.UnsupportedOperationException DOCUMENTATION PENDING
   */
  protected Map describeBean(Object o)
  {
    Map m= new HashMap();

    try
    {
      m = BeanUtils.describe((Serializable) o);
    }
    catch(Throwable t)
    {
      log.debug("Caught error in BeanUtils.describe(): " + t.getMessage());
      t.printStackTrace();
       
      throw new java.lang.UnsupportedOperationException(
        "Invalid describeBean. Objects may not be Java Beans.  " + t);
      
    }

    return m;
  }
}