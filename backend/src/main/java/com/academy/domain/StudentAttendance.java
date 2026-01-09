package com.academy.domain;

import com.academy.util.AttendanceStatus;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "student_attendance")
public class StudentAttendance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_session_id", nullable = false)
  private TrainingSession trainingSession;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private AttendanceStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "marked_by_coach_id")
  private Coach markedByCoach;

  @Column(name = "marked_at", nullable = false)
  private OffsetDateTime markedAt;

  @Column(columnDefinition = "TEXT")
  private String notes;

  @PrePersist
  protected void onCreate() {
    if (markedAt == null) {
      markedAt = OffsetDateTime.now();
    }
  }

  public StudentAttendance() {}

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

  public TrainingSession getTrainingSession() {
    return trainingSession;
  }

  public void setTrainingSession(TrainingSession trainingSession) {
    this.trainingSession = trainingSession;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public AttendanceStatus getStatus() {
    return status;
  }

  public void setStatus(AttendanceStatus status) {
    this.status = status;
  }

  public Coach getMarkedByCoach() {
    return markedByCoach;
  }

  public void setMarkedByCoach(Coach markedByCoach) {
    this.markedByCoach = markedByCoach;
  }

  public OffsetDateTime getMarkedAt() {
    return markedAt;
  }

  public void setMarkedAt(OffsetDateTime markedAt) {
    this.markedAt = markedAt;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}

