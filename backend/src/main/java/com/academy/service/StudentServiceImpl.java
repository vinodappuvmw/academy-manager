package com.academy.service;

import com.academy.domain.Academy;
import com.academy.domain.Sport;
import com.academy.domain.Student;
import com.academy.dto.request.StudentRequest;
import com.academy.dto.response.StudentResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.exception.StudentNotFoundException;
import com.academy.mapper.StudentMapper;
import com.academy.repository.AcademyRepository;
import com.academy.repository.SportRepository;
import com.academy.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;
  private final AcademyRepository academyRepository;
  private final SportRepository sportRepository;
  private final StudentMapper studentMapper;

  public StudentServiceImpl(
      StudentRepository studentRepository,
      AcademyRepository academyRepository,
      SportRepository sportRepository,
      StudentMapper studentMapper) {
    this.studentRepository = studentRepository;
    this.academyRepository = academyRepository;
    this.sportRepository = sportRepository;
    this.studentMapper = studentMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<StudentResponse> getAllStudents(Long academyId, Pageable pageable) {
    return studentRepository.findByAcademyId(academyId, pageable).map(studentMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public StudentResponse getStudentById(Long academyId, Long id) {
    Student student =
        studentRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(() -> new StudentNotFoundException(academyId, id));
    return studentMapper.toResponse(student);
  }

  @Override
  @Transactional
  public StudentResponse createStudent(Long academyId, StudentRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));
    Student student = studentMapper.toEntity(request);
    student.setAcademy(academy);
    Student saved = studentRepository.save(student);
    return studentMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public StudentResponse updateStudent(Long academyId, Long id, StudentRequest request) {
    Student student =
        studentRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(() -> new StudentNotFoundException(academyId, id));
    student.setName(request.name());
    student.setDateOfBirth(request.dateOfBirth());
    student.setAddress(request.address());
    student.setPhone(request.phone());
    student.setEmail(request.email());
    student.setEmergencyContact(request.emergencyContact());
    student.setPosition(request.position());
    student.setHighestEducation(request.highestEducation());
    student.setGender(request.gender());
    student.setPhotoUrl(request.photoUrl());
    Student updated = studentRepository.save(student);
    return studentMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteStudent(Long academyId, Long id) {
    Student student =
        studentRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(() -> new StudentNotFoundException(academyId, id));
    studentRepository.delete(student);
  }

  @Override
  @Transactional
  public void enrollStudentInSport(Long academyId, Long studentId, Long sportId) {
    Student student =
        studentRepository
            .findByAcademyIdAndId(academyId, studentId)
            .orElseThrow(() -> new StudentNotFoundException(academyId, studentId));
    Sport sport =
        sportRepository
            .findById(sportId)
            .orElseThrow(() -> new ResourceNotFoundException("Sport not found with id: " + sportId));
    if (!student.getSports().contains(sport)) {
      student.getSports().add(sport);
      studentRepository.save(student);
    }
  }

  @Override
  @Transactional
  public void unenrollStudentFromSport(Long academyId, Long studentId, Long sportId) {
    Student student =
        studentRepository
            .findByAcademyIdAndId(academyId, studentId)
            .orElseThrow(() -> new StudentNotFoundException(academyId, studentId));
    Sport sport =
        sportRepository
            .findById(sportId)
            .orElseThrow(() -> new ResourceNotFoundException("Sport not found with id: " + sportId));
    student.getSports().remove(sport);
    studentRepository.save(student);
  }
}

