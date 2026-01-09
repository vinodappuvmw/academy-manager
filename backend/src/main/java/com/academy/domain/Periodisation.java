package com.academy.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "periodisation")
public class Periodisation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id")
  private Student student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_program_id")
  private TrainingProgram trainingProgram;

  @Column(name = "weekly_plan", columnDefinition = "TEXT")
  private String weeklyPlan;

  @Column(name = "monthly_plan", columnDefinition = "TEXT")
  private String monthlyPlan;

  @Column(name = "yearly_plan", columnDefinition = "TEXT")
  private String yearlyPlan;

  @Column(name = "weekly_plan_image_url", length = 512)
  private String weeklyPlanImageUrl;

  @Column(name = "monthly_plan_image_url", length = 512)
  private String monthlyPlanImageUrl;

  @Column(name = "yearly_plan_image_url", length = 512)
  private String yearlyPlanImageUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by_coach_id")
  private Coach createdByCoach;

  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @Column(name = "updated_at")
  private OffsetDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    if (createdAt == null) {
      createdAt = OffsetDateTime.now();
    }
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = OffsetDateTime.now();
  }

  public Periodisation() {}

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

  public TrainingProgram getTrainingProgram() {
    return trainingProgram;
  }

  public void setTrainingProgram(TrainingProgram trainingProgram) {
    this.trainingProgram = trainingProgram;
  }

  public String getWeeklyPlan() {
    return weeklyPlan;
  }

  public void setWeeklyPlan(String weeklyPlan) {
    this.weeklyPlan = weeklyPlan;
  }

  public String getMonthlyPlan() {
    return monthlyPlan;
  }

  public void setMonthlyPlan(String monthlyPlan) {
    this.monthlyPlan = monthlyPlan;
  }

  public String getYearlyPlan() {
    return yearlyPlan;
  }

  public void setYearlyPlan(String yearlyPlan) {
    this.yearlyPlan = yearlyPlan;
  }

  public String getWeeklyPlanImageUrl() {
    return weeklyPlanImageUrl;
  }

  public void setWeeklyPlanImageUrl(String weeklyPlanImageUrl) {
    this.weeklyPlanImageUrl = weeklyPlanImageUrl;
  }

  public String getMonthlyPlanImageUrl() {
    return monthlyPlanImageUrl;
  }

  public void setMonthlyPlanImageUrl(String monthlyPlanImageUrl) {
    this.monthlyPlanImageUrl = monthlyPlanImageUrl;
  }

  public String getYearlyPlanImageUrl() {
    return yearlyPlanImageUrl;
  }

  public void setYearlyPlanImageUrl(String yearlyPlanImageUrl) {
    this.yearlyPlanImageUrl = yearlyPlanImageUrl;
  }

  public Coach getCreatedByCoach() {
    return createdByCoach;
  }

  public void setCreatedByCoach(Coach createdByCoach) {
    this.createdByCoach = createdByCoach;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}

