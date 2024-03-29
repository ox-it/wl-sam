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



package org.sakaiproject.tool.assessment.ui.bean.evaluation;

import java.io.Serializable;
import java.util.ArrayList;

import org.sakaiproject.tool.assessment.ui.bean.util.Validator;

/**
 *
 * <p>Description: Helper bean for QuestionScoresBean getPartList.</p><p>
 * Note: All part and question numbers are strings for maximum flexibility.</p>
 * <p>Copyright: Copyright (c) 2004 Sakai Project</p>
 * <p> </p>
 * @author Ed Smiley
 * @version $Id$
 */
public class PartData
  implements Serializable
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -6113822805534954287L;
private ArrayList questionNumberList;
  private String partNumber;
  private String id;
  private boolean linked;
  private boolean isRandomDrawPart;
  private int numberQuestionsDraw;
  private int numberQuestionsTotal;
  private boolean noQuestions;

  /**
   * Returns a list of the question numbers as Strings.
   * @return ArrayList
   */
  public ArrayList getQuestionNumberList()
  {
    if (questionNumberList == null)
      return new ArrayList();
    return questionNumberList;
  }

  /**
   * Sets a list of question number Strings.
   * @param pquestionNumberList ArrayList
   */
  public void setQuestionNumberList(ArrayList pquestionNumberList)
  {
    questionNumberList = pquestionNumberList;
  }

  /**
   * set the part number for this part as a String
   * @param ppartNumber String
   */
  public void setPartNumber(String ppartNumber)
  {
    partNumber = ppartNumber;
  }

  public String getPartNumber()
  {
    return Validator.check(partNumber, "N/A");
  }

  public void setId(String pid)
  {
    id = pid;
  }

  public String getId()
  {
    return Validator.check(id, "0");
  }

  public boolean getLinked()
  {
    return linked;
  }

  public void setLinked(boolean newLinked)
  {
    linked = newLinked;
  }
  
  public boolean getIsRandomDrawPart() {
	  return isRandomDrawPart;
  }
  
  public void setIsRandomDrawPart(boolean isRandomDrawPart) {
	  this.isRandomDrawPart = isRandomDrawPart;
  }
  
  
  public boolean getNoQuestions() {
	  noQuestions = false;
	  if (questionNumberList.size() == 0) {
		  noQuestions = true;
	  }
	  return noQuestions;
  }
  
  public int getNumberQuestionsDraw() {
	  return numberQuestionsDraw;
  }
  
  public void setNumberQuestionsDraw(int numberQuestionsDraw) {
	  this.numberQuestionsDraw = numberQuestionsDraw;
  }
  
  public int getNumberQuestionsTotal() {
	  return this.questionNumberList.size();
  }
  
}
