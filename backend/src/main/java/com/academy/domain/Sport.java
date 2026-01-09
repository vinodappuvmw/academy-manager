package com.academy.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sport")
public class Sport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(length = 100)
  private String category;

  @ManyToMany(mappedBy = "sports")
  private List<Academy> academies = new ArrayList<>();

  @ManyToMany(mappedBy = "sports")
  private List<Student> students = new ArrayList<>();

  @ManyToMany(mappedBy = "sports")
  private List<Coach> coaches = new ArrayList<>();

  @OneToMany(mappedBy = "sport", cascade = CascadeType.ALL)
  private List<TrainingSession> trainingSessions = new ArrayList<>();

  public Sport() {}

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public List<Academy> getAcademies() {
    return academies;
  }

  public void setAcademies(List<Academy> academies) {
    this.academies = academies;
  }

  public List<Student> getStudents() {
    return students;
  }

  public void setStudents(List<Student> students) {
    this.students = students;
  }

  public List<Coach> getCoaches() {
    return coaches;
  }

  public void setCoaches(List<Coach> coaches) {
    this.coaches = coaches;
  }

  public List<TrainingSession> getTrainingSessions() {
    return trainingSessions;
  }

  public void setTrainingSessions(List<TrainingSession> trainingSessions) {
    this.trainingSessions = trainingSessions;
  }
}

