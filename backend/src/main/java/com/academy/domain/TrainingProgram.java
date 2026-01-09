package com.academy.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "training_program")
public class TrainingProgram {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
  private List<TrainingSession> sessions = new ArrayList<>();

  public TrainingProgram() {}

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<TrainingSession> getSessions() {
    return sessions;
  }

  public void setSessions(List<TrainingSession> sessions) {
    this.sessions = sessions;
  }
}

