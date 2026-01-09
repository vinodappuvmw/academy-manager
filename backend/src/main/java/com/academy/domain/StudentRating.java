package com.academy.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Map;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "student_rating")
public class StudentRating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sport_id", nullable = false)
  private Sport sport;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_session_id")
  private TrainingSession trainingSession;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rating_template_id", nullable = false)
  private RatingTemplate ratingTemplate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rated_by_coach_id")
  private Coach ratedByCoach;

  @Column(name = "rating_date", nullable = false)
  private LocalDate ratingDate;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(nullable = false, columnDefinition = "jsonb")
  private Map<String, Object> scores;

  @Column(columnDefinition = "TEXT")
  private String comments;

  public StudentRating() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Academy getAcademy() {
    return academy;
  }

  public void setAcademy(Academy academy) {
    this.academy = academy;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Sport getSport() {
    return sport;
  }

  public void setSport(Sport sport) {
    this.sport = sport;
  }

  public TrainingSession getTrainingSession() {
    return trainingSession;
  }

  public void setTrainingSession(TrainingSession trainingSession) {
    this.trainingSession = trainingSession;
  }

  public RatingTemplate getRatingTemplate() {
    return ratingTemplate;
  }

  public void setRatingTemplate(RatingTemplate ratingTemplate) {
    this.ratingTemplate = ratingTemplate;
  }

  public Coach getRatedByCoach() {
    return ratedByCoach;
  }

  public void setRatedByCoach(Coach ratedByCoach) {
    this.ratedByCoach = ratedByCoach;
  }

  public LocalDate getRatingDate() {
    return ratingDate;
  }

  public void setRatingDate(LocalDate ratingDate) {
    this.ratingDate = ratingDate;
  }

  public Map<String, Object> getScores() {
    return scores;
  }

  public void setScores(Map<String, Object> scores) {
    this.scores = scores;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }
}

