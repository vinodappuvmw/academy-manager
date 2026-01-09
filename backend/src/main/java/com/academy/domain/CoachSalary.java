package com.academy.domain;

import com.academy.util.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "coach_salary")
public class CoachSalary {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coach_id", nullable = false)
  private Coach coach;

  @Column(nullable = false)
  private Integer year;

  @Column(nullable = false)
  private Integer month;

  @Column(name = "base_salary", nullable = false, precision = 12, scale = 2)
  private BigDecimal baseSalary;

  @Column(name = "bonus_amount", nullable = false, precision = 12, scale = 2)
  private BigDecimal bonusAmount = BigDecimal.ZERO;

  @Column(name = "amount_paid", nullable = false, precision = 12, scale = 2)
  private BigDecimal amountPaid = BigDecimal.ZERO;

  @Column(name = "due_date")
  private LocalDate dueDate;

  @Column(name = "paid_date")
  private LocalDate paidDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private PaymentStatus status;

  @Column(columnDefinition = "TEXT")
  private String notes;

  public CoachSalary() {}

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

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getMonth() {
    return month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  public BigDecimal getBaseSalary() {
    return baseSalary;
  }

  public void setBaseSalary(BigDecimal baseSalary) {
    this.baseSalary = baseSalary;
  }

  public BigDecimal getBonusAmount() {
    return bonusAmount;
  }

  public void setBonusAmount(BigDecimal bonusAmount) {
    this.bonusAmount = bonusAmount;
  }

  public BigDecimal getAmountPaid() {
    return amountPaid;
  }

  public void setAmountPaid(BigDecimal amountPaid) {
    this.amountPaid = amountPaid;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public LocalDate getPaidDate() {
    return paidDate;
  }

  public void setPaidDate(LocalDate paidDate) {
    this.paidDate = paidDate;
  }

  public PaymentStatus getStatus() {
    return status;
  }

  public void setStatus(PaymentStatus status) {
    this.status = status;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}

