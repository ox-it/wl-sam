/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/trunk/component/src/java/org/sakaiproject/tool/assessment/qti/util/URIResolver.java $
 * $Id: URIResolver.java 9274 2006-05-10 22:50:48Z daisyf@stanford.edu $
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



package org.sakaiproject.tool.assessment.qti.util;

import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Organization: Sakai Project</p>
 * @author palcasi
 * @version $Id: URIResolver.java 9274 2006-05-10 22:50:48Z daisyf@stanford.edu $
 */
public class URIResolver implements javax.xml.transform.URIResolver
{
  private static Log log = LogFactory.getLog(URIResolver.class);

    public URIResolver()
    {
    }

    /* (non-Javadoc)
     * @see javax.xml.transform.URIResolver#resolve(java.lang.String, java.lang.String)
     */
    public Source resolve(String href, String base) throws TransformerException
    {
        Source source = null;
        String path = null;
        try
        {
            URI uri = new URI(base);
            path = uri.resolve(href).toString();
            source = new StreamSource(path);
        }
        catch (URISyntaxException e)
        {
            log.error(e); 
            throw new RuntimeException(e);
        }

        return source;
    }

}
