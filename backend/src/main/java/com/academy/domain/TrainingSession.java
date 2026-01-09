package com.academy.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "training_session")
public class TrainingSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @Column(nullable = false)
  private LocalDate date;

  @Column(name = "start_time", nullable = false)
  private LocalTime startTime;

  @Column(name = "duration_minutes", nullable = false)
  private Integer durationMinutes;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_centre_id")
  private TrainingCentre trainingCentre;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "program_id")
  private TrainingProgram program;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "plan_id")
  private Plan plan;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "session_owner_id")
  private Coach sessionOwner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sport_id")
  private Sport sport;

  @Column(columnDefinition = "TEXT")
  private String notes;

  @ManyToMany
  @JoinTable(
      name = "training_session_coach",
      joinColumns = @JoinColumn(name = "training_session_id"),
      inverseJoinColumns = @JoinColumn(name = "coach_id"))
  private List<Coach> coaches = new ArrayList<>();

  @ManyToMany
  @JoinTable(
      name = "training_session_student",
      joinColumns = @JoinColumn(name = "training_session_id"),
      inverseJoinColumns = @JoinColumn(name = "student_id"))
  private List<Student> students = new ArrayList<>();

  @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StudentAttendance> attendances = new ArrayList<>();

  @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StudentRating> ratings = new ArrayList<>();

  public TrainingSession() {}

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

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public Integer getDurationMinutes() {
    return durationMinutes;
  }

  public void setDurationMinutes(Integer durationMinutes) {
    this.durationMinutes = durationMinutes;
  }

  public TrainingCentre getTrainingCentre() {
    return trainingCentre;
  }

  public void setTrainingCentre(TrainingCentre trainingCentre) {
    this.trainingCentre = trainingCentre;
  }

  public TrainingProgram getProgram() {
    return program;
  }

  public void setProgram(TrainingProgram program) {
    this.program = program;
  }

  public Plan getPlan() {
    return plan;
  }

  public void setPlan(Plan plan) {
    this.plan = plan;
  }

  public Coach getSessionOwner() {
    return sessionOwner;
  }

  public void setSessionOwner(Coach sessionOwner) {
    this.sessionOwner = sessionOwner;
  }

  public Sport getSport() {
    return sport;
  }

  public void setSport(Sport sport) {
    this.sport = sport;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public List<Coach> getCoaches() {
    return coaches;
  }

  public void setCoaches(List<Coach> coaches) {
    this.coaches = coaches;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  public List<StudentAttendance> getAttendances() {
    return attendances;
  }

  public void setAttendances(List<StudentAttendance> attendances) {
    this.attendances = attendances;
  }

  public List<StudentRating> getRatings() {
    return ratings;
  }

  public void setRatings(List<StudentRating> ratings) {
    this.ratings = ratings;
  }
}

