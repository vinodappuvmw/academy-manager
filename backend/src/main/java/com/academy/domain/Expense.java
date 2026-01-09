package com.academy.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expense")
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_centre_id")
  private TrainingCentre trainingCentre;

  @Column(nullable = false)
  private LocalDate date;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false, length = 100)
  private String category;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(length = 255)
  private String payee;

  @Column(name = "payment_method", length = 50)
  private String paymentMethod;

  @Column(name = "reference_number", length = 100)
  private String referenceNumber;

  public Expense() {}

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

  public TrainingCentre getTrainingCentre() {
    return trainingCentre;
  }

  public void setTrainingCentre(TrainingCentre trainingCentre) {
    this.trainingCentre = trainingCentre;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPayee() {
    return payee;
  }

  public void setPayee(String payee) {
    this.payee = payee;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }

  public void setReferenceNumber(String referenceNumber) {
    this.referenceNumber = referenceNumber;
  }
}

