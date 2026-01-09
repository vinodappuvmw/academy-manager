package com.academy.domain;

import com.academy.util.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "student_fee")
public class StudentFee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @Column(nullable = false)
  private Integer year;

  @Column(nullable = false)
  private Integer month;

  @Column(name = "amount_due", nullable = false, precision = 12, scale = 2)
  private BigDecimal amountDue;

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

  public StudentFee() {}

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

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
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

  public BigDecimal getAmountDue() {
    return amountDue;
  }

  public void setAmountDue(BigDecimal amountDue) {
    this.amountDue = amountDue;
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

