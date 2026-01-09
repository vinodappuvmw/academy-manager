package com.academy.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coach")
public class Coach {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String address;

  @Column(length = 50)
  private String phone;

  @Column(length = 255)
  private String email;

  @Column(length = 255)
  private String sportSubject;

  @Column(length = 20)
  private String gender;

  private Integer yearsExperience;

  @Column(columnDefinition = "TEXT")
  private String qualifications;

  @Column(columnDefinition = "TEXT")
  private String specialization;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(name = "photo_url", length = 512)
  private String photoUrl;

  @OneToMany(mappedBy = "sessionOwner", cascade = CascadeType.ALL)
  private List<TrainingSession> ownedSessions = new ArrayList<>();

  @ManyToMany(mappedBy = "coaches")
  private List<TrainingSession> sessions = new ArrayList<>();

  @ManyToMany
  @JoinTable(
      name = "coach_sport",
      joinColumns = @JoinColumn(name = "coach_id"),
      inverseJoinColumns = @JoinColumn(name = "sport_id"))
  private List<Sport> sports = new ArrayList<>();

  @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CoachSalary> salaries = new ArrayList<>();

  @OneToMany(mappedBy = "markedByCoach", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StudentAttendance> markedAttendances = new ArrayList<>();

  @OneToMany(mappedBy = "ratedByCoach", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StudentRating> ratings = new ArrayList<>();

  public Coach() {}

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSportSubject() {
    return sportSubject;
  }

  public void setSportSubject(String sportSubject) {
    this.sportSubject = sportSubject;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Integer getYearsExperience() {
    return yearsExperience;
  }

  public void setYearsExperience(Integer yearsExperience) {
    this.yearsExperience = yearsExperience;
  }

  public String getQualifications() {
    return qualifications;
  }

  public void setQualifications(String qualifications) {
    this.qualifications = qualifications;
  }

  public String getSpecialization() {
    return specialization;
  }

  public void setSpecialization(String specialization) {
    this.specialization = specialization;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public List<TrainingSession> getOwnedSessions() {
    return ownedSessions;
  }

  public void setOwnedSessions(List<TrainingSession> ownedSessions) {
    this.ownedSessions = ownedSessions;
  }

  public List<TrainingSession> getSessions() {
    return sessions;
  }

  public void setSessions(List<TrainingSession> sessions) {
    this.sessions = sessions;
  }

  public List<CoachSalary> getSalaries() {
    return salaries;
  }

  public void setSalaries(List<CoachSalary> salaries) {
    this.salaries = salaries;
  }

  public List<StudentAttendance> getMarkedAttendances() {
    return markedAttendances;
  }

  public void setMarkedAttendances(List<StudentAttendance> markedAttendances) {
    this.markedAttendances = markedAttendances;
  }

  public List<StudentRating> getRatings() {
    return ratings;
  }

  public void setRatings(List<StudentRating> ratings) {
    this.ratings = ratings;
  }

  public List<Sport> getSports() {
    return sports;
  }

  public void setSports(List<Sport> sports) {
    this.sports = sports;
  }
}

