package com.academy.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "academy")
public class Academy {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String address;

  @Column(length = 50)
  private String phone;

  @Column(length = 255)
  private String email;

  @Column(length = 255)
  private String website;

  @Column(name = "logo_url", length = 512)
  private String logoUrl;

  @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Coach> coaches = new ArrayList<>();

  @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Student> students = new ArrayList<>();

  @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TrainingCentre> trainingCentres = new ArrayList<>();

  @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TrainingProgram> trainingPrograms = new ArrayList<>();

  @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Plan> plans = new ArrayList<>();

  @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TrainingSession> trainingSessions = new ArrayList<>();

  @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Expense> expenses = new ArrayList<>();

  @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StudentFee> studentFees = new ArrayList<>();

  @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CoachSalary> coachSalaries = new ArrayList<>();

  @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StudentAttendance> studentAttendances = new ArrayList<>();

  @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RatingTemplate> ratingTemplates = new ArrayList<>();

  @OneToMany(mappedBy = "academy", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StudentRating> studentRatings = new ArrayList<>();

  @ManyToMany
  @JoinTable(
      name = "academy_sport",
      joinColumns = @JoinColumn(name = "academy_id"),
      inverseJoinColumns = @JoinColumn(name = "sport_id"))
  private List<Sport> sports = new ArrayList<>();

  public Academy() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
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

  public List<TrainingCentre> getTrainingCentres() {
    return trainingCentres;
  }

  public void setTrainingCentres(List<TrainingCentre> trainingCentres) {
    this.trainingCentres = trainingCentres;
  }

  public List<TrainingProgram> getTrainingPrograms() {
    return trainingPrograms;
  }

  public void setTrainingPrograms(List<TrainingProgram> trainingPrograms) {
    this.trainingPrograms = trainingPrograms;
  }

  public List<Plan> getPlans() {
    return plans;
  }

  public void setPlans(List<Plan> plans) {
    this.plans = plans;
  }

  public List<TrainingSession> getTrainingSessions() {
    return trainingSessions;
  }

  public void setTrainingSessions(List<TrainingSession> trainingSessions) {
    this.trainingSessions = trainingSessions;
  }

  public List<Expense> getExpenses() {
    return expenses;
  }

  public void setExpenses(List<Expense> expenses) {
    this.expenses = expenses;
  }

  public List<StudentFee> getStudentFees() {
    return studentFees;
  }

  public void setStudentFees(List<StudentFee> studentFees) {
    this.studentFees = studentFees;
  }

  public List<CoachSalary> getCoachSalaries() {
    return coachSalaries;
  }

  public void setCoachSalaries(List<CoachSalary> coachSalaries) {
    this.coachSalaries = coachSalaries;
  }

  public List<StudentAttendance> getStudentAttendances() {
    return studentAttendances;
  }

  public void setStudentAttendances(List<StudentAttendance> studentAttendances) {
    this.studentAttendances = studentAttendances;
  }

  public List<RatingTemplate> getRatingTemplates() {
    return ratingTemplates;
  }

  public void setRatingTemplates(List<RatingTemplate> ratingTemplates) {
    this.ratingTemplates = ratingTemplates;
  }

  public List<StudentRating> getStudentRatings() {
    return studentRatings;
  }

  public void setStudentRatings(List<StudentRating> studentRatings) {
    this.studentRatings = studentRatings;
  }

  public List<Sport> getSports() {
    return sports;
  }

  public void setSports(List<Sport> sports) {
    this.sports = sports;
  }
}

