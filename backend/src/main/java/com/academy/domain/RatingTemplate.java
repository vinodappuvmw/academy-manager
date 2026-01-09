package com.academy.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "rating_template")
public class RatingTemplate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @Column(nullable = false)
  private String name;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(nullable = false, columnDefinition = "jsonb")
  private Map<String, Object> schema;

  @OneToMany(mappedBy = "ratingTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<StudentRating> ratings = new ArrayList<>();

  public RatingTemplate() {}

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

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public Map<String, Object> getSchema() {
    return schema;
  }

  public void setSchema(Map<String, Object> schema) {
    this.schema = schema;
  }

  public List<StudentRating> getRatings() {
    return ratings;
  }

  public void setRatings(List<StudentRating> ratings) {
    this.ratings = ratings;
  }
}

