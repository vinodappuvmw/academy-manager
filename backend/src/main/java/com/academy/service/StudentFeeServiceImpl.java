package com.academy.service;

import com.academy.domain.Academy;
import com.academy.domain.Student;
import com.academy.domain.StudentFee;
import com.academy.dto.request.StudentFeeRequest;
import com.academy.dto.response.StudentFeeResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.DuplicateResourceException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.exception.StudentNotFoundException;
import com.academy.mapper.StudentFeeMapper;
import com.academy.repository.AcademyRepository;
import com.academy.repository.StudentFeeRepository;
import com.academy.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class StudentFeeServiceImpl implements StudentFeeService {

  private final StudentFeeRepository studentFeeRepository;
  private final AcademyRepository academyRepository;
  private final StudentRepository studentRepository;
  private final StudentFeeMapper studentFeeMapper;

  public StudentFeeServiceImpl(
      StudentFeeRepository studentFeeRepository,
      AcademyRepository academyRepository,
      StudentRepository studentRepository,
      StudentFeeMapper studentFeeMapper) {
    this.studentFeeRepository = studentFeeRepository;
    this.academyRepository = academyRepository;
    this.studentRepository = studentRepository;
    this.studentFeeMapper = studentFeeMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<StudentFeeResponse> getAllStudentFees(Long academyId, Pageable pageable) {
    return studentFeeRepository
        .findByAcademyId(academyId, pageable)
        .map(studentFeeMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public StudentFeeResponse getStudentFeeById(Long academyId, Long id) {
    StudentFee studentFee =
        studentFeeRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Student fee not found with id: " + id));
    return studentFeeMapper.toResponse(studentFee);
  }

  @Override
  @Transactional
  public StudentFeeResponse createStudentFee(Long academyId, StudentFeeRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));

    Student student =
        studentRepository
            .findByAcademyIdAndId(academyId, request.studentId())
            .orElseThrow(() -> new StudentNotFoundException(academyId, request.studentId()));

    // Check for duplicate
    studentFeeRepository
        .findByAcademyIdAndStudentIdAndYearAndMonth(
            academyId, request.studentId(), request.year(), request.month())
        .ifPresent(
            existing -> {
              throw new DuplicateResourceException(
                  "Student fee already exists for student "
                      + request.studentId()
                      + " for "
                      + request.year()
                      + "-"
                      + request.month());
            });

    StudentFee studentFee = studentFeeMapper.toEntity(request);
    studentFee.setAcademy(academy);
    studentFee.setStudent(student);
    if (request.amountPaid() == null) {
      studentFee.setAmountPaid(BigDecimal.ZERO);
    }

    StudentFee saved = studentFeeRepository.save(studentFee);
    return studentFeeMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public StudentFeeResponse updateStudentFee(Long academyId, Long id, StudentFeeRequest request) {
    StudentFee studentFee =
        studentFeeRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Student fee not found with id: " + id));

    studentFee.setYear(request.year());
    studentFee.setMonth(request.month());
    studentFee.setAmountDue(request.amountDue());
    studentFee.setAmountPaid(request.amountPaid() != null ? request.amountPaid() : BigDecimal.ZERO);
    studentFee.setDueDate(request.dueDate());
    studentFee.setPaidDate(request.paidDate());
    studentFee.setStatus(request.status());
    studentFee.setNotes(request.notes());

    StudentFee updated = studentFeeRepository.save(studentFee);
    return studentFeeMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteStudentFee(Long academyId, Long id) {
    StudentFee studentFee =
        studentFeeRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Student fee not found with id: " + id));
    studentFeeRepository.delete(studentFee);
  }
}

