/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/sam/trunk/component/src/java/org/sakaiproject/tool/assessment/qti/asi/Item.java $
 * $Id: Item.java 9274 2006-05-10 22:50:48Z daisyf@stanford.edu $
 ***********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006 The Sakai Foundation.
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



package org.sakaiproject.tool.assessment.qti.asi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.sakaiproject.tool.assessment.data.ifc.assessment.ItemDataIfc;
import org.sakaiproject.tool.assessment.data.ifc.assessment.ItemMetaDataIfc;
import org.sakaiproject.tool.assessment.data.ifc.shared.TypeIfc;
import org.sakaiproject.tool.assessment.qti.constants.AuthoringConstantStrings;
import org.sakaiproject.tool.assessment.qti.constants.QTIConstantStrings;
import org.sakaiproject.tool.assessment.qti.constants.QTIVersion;
import org.sakaiproject.tool.assessment.qti.helper.QTIHelperFactory;
import org.sakaiproject.tool.assessment.qti.helper.item.ItemHelperIfc;

/**
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organization: Sakai Project</p>
 * @author rshastri
 * @author Ed Smiley esmiley@stanford.edu
 * @version $Id: Item.java 9274 2006-05-10 22:50:48Z daisyf@stanford.edu $
 */
public class Item extends ASIBaseClass
{
  private static Log log = LogFactory.getLog(Item.class);
  private int qtiVersion;
  private ItemHelperIfc helper;


  /**
   * Explicitly setting serialVersionUID insures future versions can be
     * successfully restored. It is essential this variable name not be changed
     * to SERIALVERSIONUID, as the default serialization methods expects this
   * exact name.
   */
  private static final long serialVersionUID = 1;
  private String basePath;
  private String identity;

  /**
   * Creates a new Item object.
   */
  public Item(int qtiVersion)
  {
    super();
    initVersion(qtiVersion);
  }

  /**
   * Creates a new Item object.
   *
   * @param document an item XML document
   */
  public Item(Document document, int qtiVersion)
  {
    super(document);
    initVersion(qtiVersion);
  }

  private void initVersion(int qtiVersion)
  {
    if (!QTIVersion.isValid(qtiVersion))
    {
      throw new IllegalArgumentException("Invalid Item QTI version.");
    }
    this.qtiVersion = qtiVersion;
    switch (qtiVersion)
    {
      case QTIVersion.VERSION_1_2:
        basePath = QTIConstantStrings.ITEM; // for v 1.2
        identity = QTIConstantStrings.IDENT;
      case QTIVersion.VERSION_2_0:
        basePath = QTIConstantStrings.ASSESSMENTITEM;// for v 2.0
        identity = QTIConstantStrings.AITEM_IDENT;
      default:
        basePath = QTIConstantStrings.ITEM; // DEFAULT
        identity = QTIConstantStrings.IDENT;
    }

    QTIHelperFactory factory = new QTIHelperFactory();
    helper = factory.getItemHelperInstance(qtiVersion);
    log.debug("Item XML class.initVersion(int qtiVersion)");
    log.debug("qtiVersion="+qtiVersion);
    log.debug("basePath="+basePath);
    log.debug("identity="+identity);
  }

  /**
   * set identity attribute (ident/identioty)
   * @param ident the value
   */

  public void setIdent(String ident)
  {
    String xpath = basePath;
    List list = this.selectNodes(xpath);
    if (list.size() > 0)
    {
      Element element = (Element) list.get(0);
      element.setAttribute(identity, ident);
    }
  }

  /**
   * set title attribute
   * @param ident the value
   */
  public void setTitle(String title)
  {
    String xpath = basePath;
    List list = this.selectNodes(xpath);
    if (list.size() > 0)
    {
      Element element = (Element) list.get(0);
      element.setAttribute(QTIConstantStrings.TITLE, escapeXml(title));
    }
  }

  /**
   * Update XML from perisistence
   * @param item
   */
  public void update(ItemDataIfc item)
  {
    // metadata
    setFieldentry("ITEM_OBJECTIVE",
      item.getItemMetaDataByLabel(ItemMetaDataIfc.OBJECTIVE ));
    setFieldentry("ITEM_KEYWORD",
      item.getItemMetaDataByLabel(ItemMetaDataIfc.KEYWORD));
    setFieldentry("ITEM_RUBRIC", item.getItemMetaDataByLabel(ItemMetaDataIfc.RUBRIC ));
  
    // set TIMEALLOWED and NUM_OF_ATTEMPTS for audio recording questions:
    if (item.getDuration()!=null){
    	setFieldentry("TIMEALLOWED",
    			item.getDuration().toString()); 
    }
    if (item.getTriesAllowed()!=null){
    	setFieldentry("NUM_OF_ATTEMPTS",
    			item.getTriesAllowed().toString());
    }
    //  rshastri: SAK-1824
    if(item !=null &&(item.getTypeId().equals(TypeIfc.TRUE_FALSE) ||
    		item.getTypeId().equals(TypeIfc.MULTIPLE_CHOICE)||
    		item.getTypeId().equals(TypeIfc.MULTIPLE_CORRECT)) && item.getHasRationale() !=null)
    {	
    	setFieldentry("hasRationale", item.getHasRationale().toString());
    }
    //  rshastri: SAK-1824
    // item data
//    ItemHelper helper = new ItemHelper();
    if (!this.isSurvey()) //surveys are unscored
    {
      helper.addMaxScore(item.getScore(), this);
      helper.addMinScore(item.getScore(), this);
    }

    String instruction = item.getInstruction();
    if (this.isMatching() || this.isFIB())
    {
      if ( instruction != null)
        {
    	  helper.setItemText(instruction, this);
        }
    }
    ArrayList itemTexts = item.getItemTextArraySorted();

    setItemTexts(itemTexts);
    if (this.isTrueFalse()) // we know what the answers are (T/F)
    {
      Boolean isTrue = item.getIsTrue();
      if (isTrue == null)
        isTrue = Boolean.FALSE;
      setAnswerTrueFalse(isTrue.booleanValue());
    }
    else
    if (!this.isSurvey()) //answers for surveys are a stereotyped scale
    {
      setAnswers(itemTexts);
    }
    setFeedback(itemTexts);
  }

  /**
   * Set the answer texts for item.
   * @param itemTextList the text(s) for item
   */
  public void setAnswerTrueFalse(boolean isTrue)
  {
    log.debug("isTrue="+isTrue);
    if (isTrue)
    {
      helper.addCorrectAnswer("A", this);
      helper.addIncorrectAnswer("B", this);
    }
    else
    {
      helper.addCorrectAnswer("B", this);
      helper.addIncorrectAnswer("A", this);

    }
  }


  /**
   * method for meta data
   *
   * @param fieldlabel to get
   *
   * @return the value
   */
  public String getFieldentry(String fieldlabel)
  {
    if (log.isDebugEnabled())
    {
      log.debug("getFieldentry(String " + fieldlabel + ")");
    }
    String xpath = helper.getMetaLabelXPath(fieldlabel);
    return super.getFieldentry(xpath);
  }

  /**
   * method for meta data
   *
   * @param fieldlabel to get
   *
   * @param setValue the value
   */
  public void setFieldentry(String fieldlabel, String setValue)
  {
    if (log.isDebugEnabled())
    {
      log.debug(
        "setFieldentry(String " + fieldlabel + ", String " + setValue +
        ")");
    }

    String xpath = helper.getMetaLabelXPath(fieldlabel);
    super.setFieldentry(xpath, setValue);
  }

  /**
   * Create a metadata field entry
   *
   * @param fieldlabel the field label
   */
  public void createFieldentry(String fieldlabel)
  {
    if (log.isDebugEnabled())
    {
      log.debug("createFieldentry(String " + fieldlabel + ")");
    }

    String xpath = helper.getMetaXPath();
    super.createFieldentry(xpath, fieldlabel);
  }


  public String getItemType()
  {
    String type = this.getFieldentry("qmd_itemtype");

    return type;
  }

  /**
   * Set the item texts.
   * Valid for single and multiple texts.
   * @param itemText text to be updated
   */
  public void setItemTexts(ArrayList itemTextList)
  {
    helper.setItemTexts(itemTextList, this);
  }

  public boolean  isEssay()
  {
    boolean essay =
      AuthoringConstantStrings.ESSAY.equals(this.getItemType()) ||
      AuthoringConstantStrings.ESSAY_ALT.equals(this.getItemType());
    return essay ? true : false;
  }

  public boolean  isSurvey()
  {
    return AuthoringConstantStrings.SURVEY.equals(this.getItemType()) ? true : false;
  }

  public boolean  isAudio()
  {
    return AuthoringConstantStrings.AUDIO.equals(this.getItemType()) ? true : false;
  }

  public boolean  isFile()
  {
    return AuthoringConstantStrings.FILE.equals(this.getItemType()) ? true : false;
  }

  public boolean  isMatching()
  {
    return AuthoringConstantStrings.MATCHING.equals(this.getItemType()) ? true : false;
  }

  public boolean  isFIB()
  {
    return AuthoringConstantStrings.FIB.equals(this.getItemType()) ? true : false;
  }

  public boolean  isMCMC()
  {
    return AuthoringConstantStrings.MCMC.equals(this.getItemType()) ? true : false;
  }

  public boolean  isMCSC()
  {
    return AuthoringConstantStrings.MCSC.equals(this.getItemType()) ? true : false;
  }

  private boolean isTrueFalse()
  {
    return AuthoringConstantStrings.TF.equals(this.getItemType()) ? true : false;
  }



  /**
   * Set the answer texts for item.
   * @param itemTextList the text(s) for item
   */
  public void setAnswers(ArrayList itemTextList)
  {
    helper.setAnswers(itemTextList, this);
  }

  /**
   * Set the feedback texts for item.
   * @param itemTextList the text(s) for item
   */
  public void setFeedback(ArrayList itemTextList)
  {
    helper.setFeedback(itemTextList, this);
  }


  /**
   * Get the text for the item
   * @return the text
   */
  public String getItemText()
  {
    return helper.getText(this);
  }


  public String getBasePath()
  {
    return basePath;
  }

  public void setBasePath(String basePath)
  {
    this.basePath = basePath;
  }
}

