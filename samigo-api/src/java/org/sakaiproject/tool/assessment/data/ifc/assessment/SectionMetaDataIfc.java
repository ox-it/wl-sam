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



package org.sakaiproject.tool.assessment.data.ifc.assessment;

public interface SectionMetaDataIfc
    extends java.io.Serializable
{
  public static String KEYWORDS = "SECTION_KEYWORDS";
  public static String OBJECTIVES = "SECTION_OBJECTIVES";
  public static String RUBRICS = "SECTION_RUBRICS";
  public static String ATTACHMENTS = "ATTACHMENTS";
  
  Long getId();

  void setId(Long id);

  SectionDataIfc getSection();

  void setSection(SectionDataIfc section);

  String getLabel();

  void setLabel(String label);

  String getEntry();

  void setEntry(String entry);

}
