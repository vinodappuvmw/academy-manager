package com.academy.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @Column(nullable = false)
  private String name;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(columnDefinition = "TEXT")
  private String address;

  @Column(length = 50)
  private String phone;

  @Column(length = 255)
  private String email;

  @Column(name = "emergency_contact", length = 50)
  private String emergencyContact;

  @Column(length = 100)
  private String position;

  @Column(name = "highest_education", length = 255)
  private String highestEducation;

  @Column(length = 20)
  private String gender;

  @Column(name = "photo_url", length = 512)
  private String photoUrl;

  @ManyToMany(mappedBy = "students")
  private List<TrainingSession> sessions = new ArrayList<>();

  @ManyToMany
  @JoinTable(
      name = "student_sport",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "sport_id"))
  private List<Sport> sports = new ArrayList<>();

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StudentFee> fees = new ArrayList<>();

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StudentAttendance> attendances = new ArrayList<>();

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StudentRating> ratings = new ArrayList<>();

  public Student() {}

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

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
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

  public String getEmergencyContact() {
    return emergencyContact;
  }

  public void setEmergencyContact(String emergencyContact) {
    this.emergencyContact = emergencyContact;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public String getHighestEducation() {
    return highestEducation;
  }

  public void setHighestEducation(String highestEducation) {
    this.highestEducation = highestEducation;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public List<TrainingSession> getSessions() {
    return sessions;
  }

  public void setSessions(List<TrainingSession> sessions) {
    this.sessions = sessions;
  }

  public List<StudentFee> getFees() {
    return fees;
  }

  public void setFees(List<StudentFee> fees) {
    this.fees = fees;
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

  public List<Sport> getSports() {
    return sports;
  }

  public void setSports(List<Sport> sports) {
    this.sports = sports;
  }
}

