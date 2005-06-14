package org.sakaiproject.tool.assessment.ui.bean.evaluation;

import org.sakaiproject.tool.assessment.data.ifc.assessment.PublishedAssessmentIfc;
import org.sakaiproject.tool.assessment.ui.bean.util.Validator;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * A set of information for an agent.  This contains both totalScores
 * and questionScores information.
 * @author Rachel Gollub <rgollub@stanford.edu>
 */

public class AgentResults
    implements Serializable
{
  private Long assessmentGradingId;
  private Long itemGradingId;
  private String agentId;
  private String firstName;
  private String lastName;
  private String lastInitial;
  private String idString;
  private String role;
  private PublishedAssessmentIfc publishedAssessment;
  private Date submittedDate;
  private Date attemptDate;
  private Boolean isLate;
  private Boolean forGrade;
  private String totalAutoScore;
  private String totalOverrideScore;
  private String finalScore; // final total score
  private String answer; // The text or link of the answer
  private String comments;
  private Integer status;
  private String gradedBy;
  private Date gradedDate;
  private Set itemGradingSet;

  public AgentResults() {
  }

  public Long getAssessmentGradingId() {
    return assessmentGradingId;
  }
  public void setAssessmentGradingId(Long assessmentGradingId) {
    this.assessmentGradingId = assessmentGradingId;
  }
  public Long getItemGradingId() {
    return itemGradingId;
  }
  public void setItemGradingId(Long itemGradingId) {
    this.itemGradingId = itemGradingId;
  }
  public PublishedAssessmentIfc getPublishedAssessment() {
    return publishedAssessment;
  }
  public void setPublishedAssessment(PublishedAssessmentIfc publishedAssessment) {
    this.publishedAssessment = publishedAssessment;
  }
  public String getAgentId() {
    return Validator.check(agentId, "N/A");
  }
  public void setAgentId(String agentId) {
    this.agentId = agentId;
  }

  public String getFirstName() {
    return Validator.check(firstName, "");
  }

  public void setFirstName(String name) {
    firstName = name;
  }

  public String getLastName() {
    return Validator.check(lastName, "");
  }

  public void setLastName(String name) {
    lastName = name;
  }

  public String getLastInitial() {
    return Validator.check(lastInitial, "A"); 
  }

  public void setLastInitial(String init) {
    lastInitial = init;
  }

  public String getIdString() {
    return Validator.check(idString, "N/A");
  }
 
  public void setIdString(String id) {
    idString = id;
  }

  public String getRole() {
    return Validator.check(role, "N/A");
  }

  public void setRole(String newrole) {
    role = newrole;
  }

  public Date getSubmittedDate() {
    return submittedDate;
  }
  public void setSubmittedDate(Date submittedDate) {
    this.submittedDate = submittedDate;
  }

  public Date getAttemptDate() {
    return attemptDate;
  }
  public void setAttemptDate(Date attemptDate) {
    this.attemptDate = attemptDate;
  }

  public Boolean getIsLate() {
    return Validator.bcheck(isLate, false);
  }
  public void setIsLate(Boolean isLate) {
    this.isLate = isLate;
  }
  public Boolean getForGrade() {
    return Validator.bcheck(forGrade, true);
  }
  public void setForGrade(Boolean forGrade) {
    this.forGrade = forGrade;
  }
  public String getTotalAutoScore() {
    return Validator.check(totalAutoScore, "0");
  }
  public void setTotalAutoScore(String totalAutoScore) {
    this.totalAutoScore = totalAutoScore;
  }
  public String getTotalOverrideScore() {
    return Validator.check(totalOverrideScore, "0");
  }
  public void setTotalOverrideScore(String totalOverrideScore) {
    this.totalOverrideScore = totalOverrideScore;
  }
  public String getFinalScore() {
    return Validator.check(finalScore, "0");
  }
  public void setFinalScore(String finalScore) {
    this.finalScore = finalScore;
  }
  public String getAnswer() {
    return Validator.check(answer, "N/A");
  }
  public void setAnswer(String answer) {
    this.answer = answer;
  }
  public String getComments() {
    return Validator.check(comments, "");
  }
  public void setComments(String comments) {
    this.comments = comments;
  }
  public String getGradedBy() {
    return Validator.check(gradedBy, "");
  }
  public void setGradedBy(String gradedBy) {
    this.gradedBy = gradedBy;
  }
  public Date getGradedDate() {
    return gradedDate;
  }
  public void setGradedDate(Date gradedDate) {
    this.gradedDate = gradedDate;
  }

  /**
   * In some cases, students are allowed to submit multiple assessment
   * for grading. However, the grader has the choice to select one to
   * represent how well the student does overall. status = 1 means
   * this submitted assessment is selected.
   */
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }

  public Set getItemGradingSet() {
    return itemGradingSet;
  }

  public void setItemGradingSet(Set itemGradingSet) {
    this.itemGradingSet = itemGradingSet;
  }

}
