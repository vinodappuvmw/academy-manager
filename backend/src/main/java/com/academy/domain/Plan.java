package com.academy.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plan")
public class Plan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coach_id")
  private Coach coach;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "image_url", length = 512)
  private String imageUrl;

  @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
  private List<TrainingSession> sessions = new ArrayList<>();

  public Plan() {}

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

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public List<TrainingSession> getSessions() {
    return sessions;
  }

  public void setSessions(List<TrainingSession> sessions) {
    this.sessions = sessions;
  }
}

