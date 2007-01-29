/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/trunk/component/src/java/org/sakaiproject/tool/assessment/qti/helper/AuthoringHelper.java $
 * $Id: AuthoringHelper.java 9274 2006-05-10 22:50:48Z daisyf@stanford.edu $
 ***********************************************************************************
 *
 * Copyright (c) 2007 The Sakai Foundation.
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

package org.sakaiproject.tool.assessment.qti.helper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.content.cover.ContentHostingService;
import org.sakaiproject.entity.api.ResourcePropertiesEdit;
import org.sakaiproject.exception.IdInvalidException;
import org.sakaiproject.exception.IdUsedException;
import org.sakaiproject.exception.InconsistentException;
import org.sakaiproject.exception.OverQuotaException;
import org.sakaiproject.exception.PermissionException;
import org.sakaiproject.exception.ServerOverloadException;
import org.sakaiproject.tool.cover.ToolManager;

public class AttachmentHelper {
	private static Log log = LogFactory.getLog(AttachmentHelper.class);

	public ContentResource createContentResource(String fullFilePath, String filename, String mimeType) {
		ContentResource contentResource = null;
		int BUFFER_SIZE = 2048;
		byte tempContent[] = new byte[BUFFER_SIZE];
		File file = null;
		FileInputStream fileInputStream = null;
		BufferedInputStream bufInputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		byte content[];
		int count = 0;

		try {
			if (mimeType.equalsIgnoreCase("text/url")) {
				content = filename.getBytes();
			}
			else {
				file = new File(fullFilePath);
				fileInputStream = new FileInputStream(file);
				bufInputStream = new BufferedInputStream(fileInputStream);
				byteArrayOutputStream = new ByteArrayOutputStream();
				while ((count = bufInputStream.read(tempContent, 0, BUFFER_SIZE)) != -1) {
					byteArrayOutputStream.write(tempContent, 0, count);
				}
				content = byteArrayOutputStream.toByteArray();
			}
			
			ResourcePropertiesEdit props = ContentHostingService.newResourceProperties();
			// Maybe we need to put in some properties?
			// props.addProperty(ResourceProperties.PROP_DISPLAY_NAME, name);
			// props.addProperty(ResourceProperties.PROP_DESCRIPTION, name);

			contentResource = ContentHostingService.addAttachmentResource(
					fullFilePath.substring(fullFilePath.lastIndexOf("/") + 1), ToolManager.getCurrentPlacement().getContext(), 
					ToolManager.getTool("sakai.samigo").getTitle(), mimeType, content, props);
		} catch (IdInvalidException e) {
			log.error(e.getMessage());
		} catch (PermissionException e) {
			log.error(e.getMessage());
		} catch (InconsistentException e) {
			log.error(e.getMessage());
		} catch (IdUsedException e) {
			log.error(e.getMessage());
		} catch (OverQuotaException e) {
			log.error(e.getMessage());
		} catch (ServerOverloadException e) {
			log.error(e.getMessage());
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		finally {
			if (bufInputStream != null) {
				try {
					bufInputStream.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}

		return contentResource;
	}
}