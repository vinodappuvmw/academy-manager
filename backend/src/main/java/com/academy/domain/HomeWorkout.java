package com.academy.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "home_workout")
public class HomeWorkout {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coach_id", nullable = false)
  private Coach coach;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sport_id", nullable = false)
  private Sport sport;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "video_links", columnDefinition = "jsonb")
  private List<String> videoLinks;

  @Column(name = "image_url", length = 512)
  private String imageUrl;

  @Column(name = "difficulty_level", length = 50)
  private String difficultyLevel;

  @Column(name = "duration_minutes")
  private Integer durationMinutes;

  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    if (createdAt == null) {
      createdAt = OffsetDateTime.now();
    }
  }

  public HomeWorkout() {}

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

  public Coach getCoach() {
    return coach;
  }

  public void setCoach(Coach coach) {
    this.coach = coach;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getVideoLinks() {
    return videoLinks;
  }

  public void setVideoLinks(List<String> videoLinks) {
    this.videoLinks = videoLinks;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getDifficultyLevel() {
    return difficultyLevel;
  }

  public void setDifficultyLevel(String difficultyLevel) {
    this.difficultyLevel = difficultyLevel;
  }

  public Integer getDurationMinutes() {
    return durationMinutes;
  }

  public void setDurationMinutes(Integer durationMinutes) {
    this.durationMinutes = durationMinutes;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }
}

