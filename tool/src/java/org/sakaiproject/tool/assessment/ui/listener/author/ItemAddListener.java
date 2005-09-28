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

package org.sakaiproject.tool.assessment.ui.listener.author;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.tool.assessment.data.dao.assessment.Answer;
import org.sakaiproject.tool.assessment.data.dao.assessment.AnswerFeedback;
import org.sakaiproject.tool.assessment.data.dao.assessment.ItemMetaData;
import org.sakaiproject.tool.assessment.data.dao.assessment.ItemText;
import org.sakaiproject.tool.assessment.data.ifc.assessment.AnswerFeedbackIfc;
import org.sakaiproject.tool.assessment.facade.AgentFacade;
import org.sakaiproject.tool.assessment.facade.AssessmentFacade;
import org.sakaiproject.tool.assessment.facade.ItemFacade;
import org.sakaiproject.tool.assessment.facade.SectionFacade;
import org.sakaiproject.tool.assessment.facade.TypeFacade;
import org.sakaiproject.tool.assessment.services.ItemService;
import org.sakaiproject.tool.assessment.services.QuestionPoolService;
import org.sakaiproject.tool.assessment.services.assessment.AssessmentService;
import org.sakaiproject.tool.assessment.ui.bean.author.AnswerBean;
import org.sakaiproject.tool.assessment.ui.bean.author.AssessmentBean;
import org.sakaiproject.tool.assessment.ui.bean.author.ItemAuthorBean;
import org.sakaiproject.tool.assessment.ui.bean.author.ItemBean;
import org.sakaiproject.tool.assessment.ui.bean.author.MatchItemBean;
import org.sakaiproject.tool.assessment.ui.bean.questionpool.QuestionPoolBean;
import org.sakaiproject.tool.assessment.ui.listener.util.ContextUtil;

/**
 * <p>Title: Samigo</p>
 * <p>Description: Sakai Assessment Manager</p>
 * <p>Organization: Sakai Project</p>
 */

public class ItemAddListener
    implements ActionListener {

  private static Log log = LogFactory.getLog(ItemAddListener.class);
  private static ContextUtil cu;
  private String scalename; // used for multiple choice Survey

  /**
   * Standard process action method.
   * @param ae ActionEvent
   * @throws AbortProcessingException
   */
  public void processAction(ActionEvent ae) throws AbortProcessingException {
      boolean correct=false;
    log.info("ItemAdd LISTENER.");
    ItemAuthorBean itemauthorbean = (ItemAuthorBean) cu.lookupBean("itemauthor");
    ItemBean item =itemauthorbean.getCurrentItem();
    String answer=item.getCorrAnswer();
    String iType=item.getItemType();
    //if((!iType.equals("1"))&&(!iType.equals("2"))){
    // only check this for Single Correct MC questions
   
    if(iType.equals(TypeFacade.MULTIPLE_CHOICE.toString())){
	if(!answer.equals("")&& answer!=null){
	    if (!saveItem(itemauthorbean)){
		throw new RuntimeException("failed to saveItem.");
	    }//end save

	}//end answer
    }//end if
    else if(iType.equals(TypeFacade.MULTIPLE_CORRECT.toString())){
	
	Iterator iter = item.getMultipleChoiceAnswers().iterator();
	if(item.getMultipleChoiceAnswers()!=null){
	    while (iter.hasNext()) {
		AnswerBean answerbean = (AnswerBean) iter.next();
		if (isCorrectChoice(item, answerbean.getLabel().trim()))
		    {
			correct=true;

			break;
		    }//end if isCorrect
	    }//end while
	    if(correct){
		if (!saveItem(itemauthorbean)) {
		    throw new RuntimeException("failed to saveItem.");
		}//end save
	    }//end correct
	}//end item test
    }//end else if
    else if(iType.equals(TypeFacade.FILL_IN_BLANK.toString())){
		FacesContext context=FacesContext.getCurrentInstance();
		ResourceBundle rb=ResourceBundle.getBundle("org.sakaiproject.tool.assessment.bundle.AuthorMessages", context.getViewRoot().getLocale());
		if(!isErrorFIB()){

		    item.setOutcomeFIB("FIBSuccess");
		    item.setOutcomePoolFIB("FIBPoolSuccess");
		    if(!saveItem(itemauthorbean)){
           
			throw new RuntimeException("failed to saveItem");
		    }
            
		}
		else{
		   String err=(String)rb.getObject("pool_missingBracket_error");
		    context.addMessage(null,new FacesMessage(err));
		    item.setOutcomeFIB("FIBFailure");
		    item.setOutcomePoolFIB("FIBPoolFailure");

		}
    }
    else{
	
	if (!saveItem(itemauthorbean)){
	    throw new RuntimeException("failed to saveItem.");
	}
    }
  }
    
	
public boolean isErrorFIB() {
ItemAuthorBean itemauthorbean = (ItemAuthorBean) cu.lookupBean("itemauthor");
    ItemBean item =itemauthorbean.getCurrentItem();
    int index=0;
     boolean error=false;
     String err="";
     boolean hasOpen=false;
     int opencount=0;
     int closecount=0;
     while(index<item.getItemText().length()){
	 char c=item.getItemText().charAt(index);
        if(c=='{'){
	    opencount++;
	    if(hasOpen){
		error=true;
		break;
	    }
	    else{ 
		hasOpen=true;
	    }
	}
	else if(c=='}'){ 
            closecount++;
	    if(!hasOpen){
		error=true;
		break;
	    }
            else{ 
		hasOpen=false;
	    }
	}
        else{}
	index++;
    }//end while
    if((hasOpen==true)||(opencount<1)||(opencount!=closecount)||(error==true)){
	return true;
    }
    else{ 
	return false;
    }
}
	
  

  public boolean saveItem(ItemAuthorBean itemauthor) {
    boolean update = false;
    try {
      ItemBean bean = itemauthor.getCurrentItem();
      ItemService delegate = new ItemService();
      ItemFacade item;

      // update not working yet, delete, then add
      if ( (bean.getItemId() != null) && (!bean.getItemId().equals("0"))) {
        update = true;
        // if modify ,itemid shouldn't be null , or 0.
        Long oldId = new Long(bean.getItemId());
        delegate.deleteItemContent(oldId, AgentFacade.getAgentString());
        item = delegate.getItem(oldId,AgentFacade.getAgentString());
      }
      else{
        item = new ItemFacade();
      }
      item.setScore(new Float(bean.getItemScore()));

      item.setStatus(ItemFacade.ACTIVE_STATUS);

      item.setTypeId(new Long(bean.getItemType()));

      item.setCreatedBy(AgentFacade.getAgentString());
      item.setCreatedDate(new Date());
      item.setLastModifiedBy(AgentFacade.getAgentString());
      item.setLastModifiedDate(new Date());

      if (bean.getInstruction() != null) {
        // for matching
        item.setInstruction(bean.getInstruction());
      }
      // update hasRationale
      if (bean.getRationale() != null) {
        item.setHasRationale(new Boolean(bean.getRationale()));
      }
      else {
        item.setHasRationale(Boolean.FALSE);
      }

      // update maxNumAttempts for audio
      if (bean.getNumAttempts() != null) {
        item.setTriesAllowed(new Integer(bean.getNumAttempts()));
      }

      // save timeallowed for audio recording
      if (bean.getTimeAllowed() != null) {
        item.setDuration(new Integer(bean.getTimeAllowed()));
      }

      if (update) {
// reset item contents for modify

        item.setItemTextSet(new HashSet());
        item.setItemMetaDataSet(new HashSet());
      }

      // prepare itemText, including answers
      if (!item.getTypeId().equals(TypeFacade.MATCHING)) {

        item.setItemTextSet(prepareText(item, bean, itemauthor));
      }
      else {
        item.setItemTextSet(prepareTextForMatching(item, bean, itemauthor));
      }

      // prepare MetaData
      item.setItemMetaDataSet(prepareMetaData(item, bean));
///////////////////////////////////////////////
// FEEDBACK
///////////////////////////////////////////////
/*
      log.info("**** FEEDBACK ****");
      log.info("**** bean.getCorrFeedback()=["
                         + bean.getCorrFeedback() + "] ***");
      log.info("**** bean.getIncorrFeedback()=["
                         + bean.getIncorrFeedback() + "] ***");
      log.info("**** bean.getGeneralFeedback()=["
                         + bean.getGeneralFeedback() + "] ***");
*/
///////////////////////////////////////////////

      // prepare feedback, only store if feedbacks are not empty
      if ( (bean.getCorrFeedback() != null) &&
          (!bean.getCorrFeedback().equals(""))) {
        item.setCorrectItemFeedback(stripPtags(bean.getCorrFeedback()));
      }
      if ( (bean.getIncorrFeedback() != null) &&
          (!bean.getIncorrFeedback().equals(""))) {
        item.setInCorrectItemFeedback(stripPtags(bean.getIncorrFeedback()));
      }
      if ( (bean.getGeneralFeedback() != null) &&
          (!bean.getGeneralFeedback().equals(""))) {
        item.setGeneralItemFeedback(stripPtags(bean.getGeneralFeedback()));
      }
      ///////////////////////////////////////////////
// FEEDBACK
///////////////////////////////////////////////
/*
      log.info("**** FEEDBACK ****");
      log.info("**** item.getCorrectItemFeedback()=["
                         + item.getCorrectItemFeedback() + "] ***");
      log.info("**** item.getInCorrectItemFeedback()=["
                         + item.getInCorrectItemFeedback() + "] ***");
      log.info("**** item.getGeneralItemFeedback()=["
                         + item.getGeneralItemFeedback() + "] ***");
*/
///////////////////////////////////////////////

      //ItemFacade savedItem =  null;

      if ( (itemauthor.getTarget() != null) &&
          (itemauthor.getTarget().equals(itemauthor.FROM_QUESTIONPOOL))) {
        // Came from Pool manager

        delegate.saveItem(item);
        QuestionPoolService qpdelegate = new QuestionPoolService();

        if (!qpdelegate.hasItem(item.getItemIdString(),
                                new Long(itemauthor.getQpoolId()))) {
          qpdelegate.addItemToPool(item.getItemIdString(),
                                   new Long(itemauthor.getQpoolId()));

        }

        QuestionPoolBean qpoolbean = (QuestionPoolBean) cu.lookupBean(
            "questionpool");

        qpoolbean.buildTree();

        /*
            // Reset question pool bean
            QuestionPoolFacade thepool= qpdelegate.getPool(new Long(itemauthor.getQpoolId()), AgentFacade.getAgentString());
            qpoolbean.getCurrentPool().setNumberOfQuestions(thepool.getQuestionSize().toString());
         */
        qpoolbean.startEditPoolAgain(itemauthor.getQpoolId());
        // return to edit pool
        itemauthor.setOutcome("editPool");
      }
      else {
        // Came from Assessment Authoring

        AssessmentService assessdelegate = new AssessmentService();
        // add the item to the specified part, otherwise add to default
        if (bean.getSelectedSection() != null) {
          SectionFacade section = assessdelegate.getSection(bean.
              getSelectedSection());

          item.setSection(section);

          if (update) {
	  // if Modify, need to reorder if assgned to different section '
            if ( (bean.getOrigSection() != null) &&
		(!bean.getOrigSection().equals(bean.getSelectedSection()))) {
                // if reassigned to different section
              Integer oldSeq = item.getSequence();
              item.setSequence(new Integer(section.getItemSet().size() + 1));

              // reorder the sequences of items in the OrigSection
    	      SectionFacade origsect= assessdelegate.getSection(bean.getOrigSection());
	      shiftItemsInOrigSection(origsect, oldSeq);


            }
            else {
              // no action needed
            }
          }

          if (!update) {
            if ( (itemauthor.getInsertPosition() == null) ||
                ("".equals(itemauthor.getInsertPosition()))) {
              // if adding to the end
              item.setSequence(new Integer(section.getItemSet().size() + 1));
            }
            else {
              // if inserting or a question
              String insertPos = itemauthor.getInsertPosition();
              shiftSequences(section, new Integer(insertPos));
              int insertPosInt = (new Integer(insertPos)).intValue() + 1;
              item.setSequence(new Integer(insertPosInt));
              // reset InsertPosition
              itemauthor.setInsertPosition("");
            }
          }

          delegate.saveItem(item);
          /*
               section.addItem(item);
                      assessdelegate.saveOrUpdateSection(section);
           */

        }

        QuestionPoolService qpdelegate = new QuestionPoolService();
	// removed the old pool-item mappings
          if ( (bean.getOrigPool() != null) && (!bean.getOrigPool().equals(""))) {
            qpdelegate.removeQuestionFromPool(item.getItemIdString(),
                                              new Long(bean.getOrigPool()));
          }

        // if assign to pool, add the item to the pool
        if ( (!bean.getSelectedPool().equals("")) && (bean.getSelectedPool() != null)) {
          qpdelegate.addItemToPool(item.getItemIdString(),
                                   new Long(bean.getSelectedPool()));

        }

        // #1a - goto editAssessment.jsp, so reset assessmentBean
        AssessmentBean assessmentBean = (AssessmentBean) cu.lookupBean(
            "assessmentBean");
        AssessmentFacade assessment = assessdelegate.getAssessment(
            assessmentBean.getAssessmentId());
        assessmentBean.setAssessment(assessment);

        itemauthor.setOutcome("editAssessment");

      }

      return true;
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private HashSet prepareTextForMatching(ItemFacade item, ItemBean bean,
                                         ItemAuthorBean itemauthor) {
    // looping through matchItemBean
    ArrayList matchItemBeanList = bean.getMatchItemBeanList();
    HashSet textSet = new HashSet();
    Iterator choiceiter = matchItemBeanList.iterator();
    while (choiceiter.hasNext()) {

      MatchItemBean choicebean = (MatchItemBean) choiceiter.next();

      ItemText choicetext = new ItemText();
      choicetext.setItem(item.getData()); // all set to the same ItemFacade
      choicetext.setSequence(choicebean.getSequence());

      choicetext.setText(stripPtags(choicebean.getChoice()));

      // need to loop through matches for in matchItemBean list
      // and add all possible matches to this choice

      //log.info(
      Iterator answeriter = matchItemBeanList.iterator();
      HashSet answerSet = new HashSet();
      Answer answer = null;
      while (answeriter.hasNext()) {

        MatchItemBean answerbean = (MatchItemBean) answeriter.next();

        if (answerbean.getSequence().equals(choicebean.getSequence())) {
          answer = new Answer(choicetext, stripPtags(answerbean.getMatch()),
                              answerbean.getSequence(),
			      AnswerBean.choiceLabels[answerbean.getSequence().intValue()-1],
                              Boolean.TRUE, null,
                              new Float(bean.getItemScore()));

          // only add feedback for correct pairs
        HashSet answerFeedbackSet = new HashSet();
        answerFeedbackSet.add(new AnswerFeedback(answer,
                                                 AnswerFeedbackIfc.
                                                 CORRECT_FEEDBACK,
                                                 stripPtags(answerbean.getCorrMatchFeedback())));
        answerFeedbackSet.add(new AnswerFeedback(answer,
                                                 AnswerFeedbackIfc.
                                                 INCORRECT_FEEDBACK,
                                                 stripPtags(answerbean.getIncorrMatchFeedback())));
        answer.setAnswerFeedbackSet(answerFeedbackSet);

        }
        else {
          answer = new Answer(choicetext, stripPtags(answerbean.getMatch()),
                              answerbean.getSequence(),
			      AnswerBean.choiceLabels[answerbean.getSequence().intValue()-1],
                              Boolean.FALSE, null, new Float(bean.getItemScore()));
        }

//      record answers for all combination of pairs

        HashSet answerFeedbackSet = new HashSet();
        answerFeedbackSet.add(new AnswerFeedback(answer,
                                                 AnswerFeedbackIfc.
                                                 CORRECT_FEEDBACK,
                                                 stripPtags(answerbean.getCorrMatchFeedback())));
        answerFeedbackSet.add(new AnswerFeedback(answer,
                                                 AnswerFeedbackIfc.
                                                 INCORRECT_FEEDBACK,
                                                 stripPtags(answerbean.getIncorrMatchFeedback())));
        answer.setAnswerFeedbackSet(answerFeedbackSet);

//
        answerSet.add(answer);

      }
      choicetext.setAnswerSet(answerSet);
      textSet.add(choicetext);

    }
    return textSet;
  }

  private HashSet prepareText(ItemFacade item, ItemBean bean,
                              ItemAuthorBean itemauthor) {
    HashSet textSet = new HashSet();
    HashSet answerSet1 = new HashSet();

/////////////////////////////////////////////////////////////
// 1. save Question Text for items with single Question Text
// (except matching)
/////////////////////////////////////////////////////////////
    ItemText text1 = new ItemText();
    text1.setItem(item.getData());
    text1.setSequence(new Long(1));
    text1.setText(bean.getItemText());

/////////////////////////////////////////////////////////////
//
// 2. save Answers
//
/////////////////////////////////////////////////////////////
    if (item.getTypeId().equals(TypeFacade.TRUE_FALSE)) {


// find correct answer

      Answer newanswer = null;
      for (int i = 0; i < bean.getAnswers().length; i++) {
        String theanswer = bean.getAnswers()[i];
        String thelabel = bean.getAnswerLabels()[i]; // store thelabel as the answer text
        if (theanswer.equals(bean.getCorrAnswer())) {
          // label is null because we don't use labels in true/false questions
          // labels are like a, b, c, or i, ii, iii, in multiple choice type

          newanswer = new Answer(text1, theanswer, new Long(i + 1), null,
                                 Boolean.TRUE, null,
                                 new Float(bean.getItemScore()));
        }
        else {
          newanswer = new Answer(text1, theanswer, new Long(i + 1), null,
                                 Boolean.FALSE, null,
                                 new Float(bean.getItemScore()));
        }
        answerSet1.add(newanswer);
      }

      text1.setAnswerSet(answerSet1);
      textSet.add(text1);
    }
    else if (item.getTypeId().equals(TypeFacade.ESSAY_QUESTION)) {

// Storing the model answer essay as an Answer, and feedback in the Answerfeedback

      String theanswer = bean.getCorrAnswer();
      if (theanswer == null) {
        theanswer = ""; // can be empty
      }

      // label is null because we don't use labels in essay questions
      //theanswer is the model answer used as a sample for student
      Answer modelanswer = new Answer(text1, theanswer, new Long(1), null,
                                      Boolean.TRUE, null,
                                      new Float(bean.getItemScore()));

      HashSet answerFeedbackSet1 = new HashSet();

      answerFeedbackSet1.add(new AnswerFeedback(modelanswer, "modelanswer",
                                                stripPtags(bean.getCorrFeedback())));
      modelanswer.setAnswerFeedbackSet(answerFeedbackSet1);

      answerSet1.add(modelanswer);
      text1.setAnswerSet(answerSet1);
      textSet.add(text1);
    }

    else if (item.getTypeId().equals(TypeFacade.MULTIPLE_CHOICE_SURVEY)) {

      /*
         TODO: need to use property file for the survey choices, to be able to internationalize.
              Properties p = null;
              // get properties file
              try{
                      p = ContextUtil.getProperties(filename);
            if (p == null)
            {
              throw new Error("Could not find properties file: " + filename);
            }
              }
              catch (Exception e){
                      e.printStackTrace();
              }
              String noprop = p.getProperty("no");
              String yesprop = p.getProperty("yes");
              String agreeprop = p.getProperty("agree");
              String disagreeprop = p.getProperty("disagree");
       */

      String scalename = bean.getScaleName();
      String[] choices = new String[2];
      // label is null because we don't use labels in survey
      if (scalename.equals("YESNO")) {
        choices = new String[2];
        choices[0] = "No";
        choices[1] = "Yes";
      }

      if (scalename.equals("AGREE")) {
        choices = new String[2];
        choices[0] = "Disagree";
        choices[1] = "Agree";
      }
      if (scalename.equals("UNDECIDED")) {
        choices = new String[3];
        choices[0] = "Disagree";
        choices[1] = "Undecided";
        choices[2] = "Agree";
      }

      if (scalename.equals("AVERAGE")) {
        choices = new String[3];
        choices[0] = "Below Average";
        choices[1] = "Average";
        choices[2] = "Above Average";
      }
      if (scalename.equals("STRONGLY_AGREE")) {
        choices = new String[5];
        choices[0] = "Strongly Disagree";
        choices[1] = "Disagree";
        choices[2] = "Undecided";
        choices[3] = "Agree";
        choices[4] = "Strongly Agree";
      }
      if (scalename.equals("EXCELLENT")) {
        choices = new String[5];
        choices[0] = "Unacceptable";
        choices[1] = "Below Average";
        choices[2] = "Average";
        choices[3] = "Above Average";
        choices[4] = "Excellent";
      }
      if (scalename.equals("SCALEFIVE")) {
        choices = new String[5];
        choices[0] = "1";
        choices[1] = "2";
        choices[2] = "3";
        choices[3] = "4";
        choices[4] = "5";
      }
      if (scalename.equals("SCALETEN")) {
        choices = new String[10];
        choices[0] = "1";
        choices[1] = "2";
        choices[2] = "3";
        choices[3] = "4";
        choices[4] = "5";
        choices[5] = "6";
        choices[6] = "7";
        choices[7] = "8";
        choices[8] = "9";
        choices[9] = "10";
      }

      for (int i = 0; i < choices.length; i++) {
        Answer answer1 = new Answer(text1, choices[i], new Long(i + 1), null, null, null,
                                    new Float(bean.getItemScore()));
        answerSet1.add(answer1);
      }
      text1.setAnswerSet(answerSet1);
      textSet.add(text1);
    }

    // not doing parsing in authoring

    else if (item.getTypeId().equals(TypeFacade.FILL_IN_BLANK)) {
// this is for fill in blank
      String entiretext = bean.getItemText();
      String fibtext = entiretext.replaceAll("[\\{][^\\}]*[\\}]", "{}");
      text1.setText(fibtext);
      //log.info(" new text without answer is = " + fibtext);
      Object[] fibanswers = getFIBanswers(entiretext).toArray();
      for (int i = 0; i < fibanswers.length; i++) {
        String oneanswer = (String) fibanswers[i];
        Answer answer1 = new Answer(text1, oneanswer,
                                      new Long(i + 1), null, Boolean.TRUE, null,
                                      new Float(bean.getItemScore()));
        answerSet1.add(answer1);
      }

      text1.setAnswerSet(answerSet1);
      textSet.add(text1);

    }

    else if ( (item.getTypeId().equals(TypeFacade.MULTIPLE_CHOICE)) ||
             (item.getTypeId().equals(TypeFacade.MULTIPLE_CORRECT))) {
// this is for both single/multiple correct multiple choice types

      // for single choice
      //String theanswer=bean.getCorrAnswer();
      Iterator iter = bean.getMultipleChoiceAnswers().iterator();
      Answer answer = null;
      while (iter.hasNext()) {
        AnswerBean answerbean = (AnswerBean) iter.next();
        if (isCorrectChoice(bean, answerbean.getLabel().trim())) {
          answer = new Answer(text1, stripPtags(answerbean.getText()),
                              answerbean.getSequence(), answerbean.getLabel(),
                              Boolean.TRUE, null, new Float(bean.getItemScore()));
        }
        else {
          answer = new Answer(text1, stripPtags(answerbean.getText()),
                              answerbean.getSequence(), answerbean.getLabel(),
                              Boolean.FALSE, null, new Float(bean.getItemScore()));
        }
        HashSet answerFeedbackSet1 = new HashSet();
        answerFeedbackSet1.add(new AnswerFeedback(answer,
                                                  AnswerFeedbackIfc.
                                                  GENERAL_FEEDBACK,
                                                  stripPtags(answerbean.getFeedback())));
        answer.setAnswerFeedbackSet(answerFeedbackSet1);

        answerSet1.add(answer);
      }

      text1.setAnswerSet(answerSet1);
      textSet.add(text1);

    }

    // for file Upload and audio recording
    else {
      // no answers need to be added
      textSet.add(text1);
    }

/////////////////////////////////////////////////////////////
// END
/////////////////////////////////////////////////////////////

    return textSet;
  }

  private HashSet prepareMetaData(ItemFacade item, ItemBean bean) {
    HashSet set = new HashSet();
    if (bean.getKeyword() != null) {
      set.add(new ItemMetaData(item.getData(), ItemMetaData.KEYWORD,
                               bean.getKeyword()));
    }
    if (bean.getRubric() != null) {
      set.add(new ItemMetaData(item.getData(), ItemMetaData.RUBRIC,
                               bean.getRubric()));
    }
    if (bean.getObjective() != null) {
      set.add(new ItemMetaData(item.getData(), ItemMetaData.OBJECTIVE,
                               bean.getObjective()));
    }
    // Randomize property got left out, added in  metadata
    if (bean.getRandomized() != null) {
      set.add(new ItemMetaData(item.getData(), ItemMetaData.RANDOMIZE,
                               bean.getRandomized()));
    }

    // save ScaleName for survey if it's a survey item
    if (bean.getScaleName() != null) {
      set.add(new ItemMetaData(item.getData(), ItemMetaData.SCALENAME,
                               bean.getScaleName()));
    }

    // save part id
    if (bean.getSelectedSection() != null) {
      set.add(new ItemMetaData(item.getData(), ItemMetaData.PARTID,
                               bean.getSelectedSection()));
    }

    // save pool id
    if (bean.getSelectedPool() != null) {
      set.add(new ItemMetaData(item.getData(), ItemMetaData.POOLID,
                               bean.getSelectedPool()));
    }

    // save timeallowed for audio recording
    /*
        // save them in ItemFacade
        if (bean.getTimeAllowed()!=null){
        set.add(new ItemMetaData(item.getData(), ItemMetaData.TIMEALLOWED, bean.getTimeAllowed()));
            }
     */
    // save timeallowed for audio recording
    /*
        // save them in ItemFacade
        if (bean.getNumAttempts()!=null){
        set.add(new ItemMetaData(item.getData(), ItemMetaData.NUMATTEMPTS, bean.getNumAttempts()));
            }
     */

    return set;
  }

  private static ArrayList getFIBanswers(String entiretext) {
    String[] tokens = entiretext.split("[\\}][^\\{]*[\\{]");
    ArrayList list = new ArrayList();
    if (tokens.length==1) {
        String[] afteropen= tokens[0].split("\\{");
        if (afteropen.length>1) {
// must have text in between {}
          String[] lastpart = afteropen[1].split("\\}");
          list.add(lastpart[0]);
        }
    }
    else {
      for (int i = 0; i < tokens.length; i++) {
      if (i == 0) {
        String[] firstpart = tokens[i].split("\\{");
	if (firstpart.length>1) {
          list.add(firstpart[1]);
        }
      }
      else if (i == (tokens.length - 1)) {
        String[] lastpart = tokens[i].split("\\}");
        list.add(lastpart[0]);
      }
      else {
        list.add(tokens[i]);
      }
      }
    } // token.length>1

    return list;

  }

  /**
   ** returns if the multile choice label is the correct choice,
   ** bean.getCorrAnswers() returns a string[] of labels
   ** bean.getCorrAnswer() returns a string of label
   **/
  public boolean isCorrectChoice(ItemBean bean, String label) {
    boolean returnvalue = false;
    if (!bean.getMultipleCorrect()) {
      String corranswer = ContextUtil.lookupParam("itemForm:selectedRadioBtn");
      if (corranswer.equals(label)) {
        returnvalue = true;
      }
      else {
        returnvalue = false;
      }
    }
    else {
      ArrayList corranswersList = ContextUtil.paramArrayValueLike(
          "mccheckboxes");
      Iterator iter = corranswersList.iterator();
      while (iter.hasNext()) {

        String currentcorrect = (String) iter.next();
        if (currentcorrect.trim().equals(label)) {
          returnvalue = true;
          break;
        }
        else {
          returnvalue = false;
        }
      }
    }

    return returnvalue;
  }

  /**
   ** shift sequence number down when inserting or reordering
   **/

  public void shiftSequences(SectionFacade sectfacade, Integer currSeq) {

    ItemService delegate = new ItemService();
    Set itemset = sectfacade.getItemFacadeSet();
    Iterator iter = itemset.iterator();
    while (iter.hasNext()) {
      ItemFacade itemfacade = (ItemFacade) iter.next();
      Integer itemfacadeseq = itemfacade.getSequence();
      if (itemfacadeseq.compareTo(currSeq) > 0) {
        itemfacade.setSequence(new Integer(itemfacadeseq.intValue() + 1));
        delegate.saveItem(itemfacade);
      }
    }
  }





  public void shiftItemsInOrigSection(SectionFacade sectfacade, Integer currSeq){
    ItemService delegate = new ItemService();
    Set itemset = sectfacade.getItemFacadeSet();
// should be size-1 now.
      Iterator iter = itemset.iterator();
      while (iter.hasNext()) {
        ItemFacade  itemfacade = (ItemFacade) iter.next();
        Integer itemfacadeseq = itemfacade.getSequence();
        if (itemfacadeseq.compareTo(currSeq) > 0 ){
          itemfacade.setSequence(new Integer(itemfacadeseq.intValue()-1) );
          delegate.saveItem(itemfacade);
        }
      }

  }

  private String stripPtags(String origtext) {
   // interim solution for the wywisyg bug. This will strip off the first <p> and last </p> if both exists.
    String newanswer = origtext;
    if ((origtext!= null)&& (origtext.startsWith("<p")) && (origtext.endsWith("</p>")) ){
       newanswer = origtext.substring(origtext.indexOf(">") + 1, origtext.lastIndexOf("</p>"));
       return newanswer.trim();
    }
    else {
      return newanswer;

    }
 }



}
