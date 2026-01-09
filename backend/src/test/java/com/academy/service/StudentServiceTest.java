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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock private StudentRepository studentRepository;
  @Mock private AcademyRepository academyRepository;
  @Mock private SportRepository sportRepository;
  @Mock private StudentMapper studentMapper;

  @InjectMocks private StudentServiceImpl studentService;

  private Academy academy;
  private Student student;
  private StudentRequest studentRequest;
  private StudentResponse studentResponse;
  private Sport sport;

  @BeforeEach
  void setUp() {
    academy = new Academy();
    academy.setId(1L);
    academy.setName("Test Academy");

    student = new Student();
    student.setId(1L);
    student.setName("John Doe");
    student.setDateOfBirth(LocalDate.of(2010, 1, 1));
    student.setAcademy(academy);

    studentRequest =
        new StudentRequest(
            "John Doe",
            LocalDate.of(2010, 1, 1),
            "123 Main St",
            "1234567890",
            "john@example.com",
            "9876543210",
            "Forward",
            "High School",
            "Male",
            null,
            Collections.emptyList());

    studentResponse =
        new StudentResponse(
            1L,
            1L,
            "John Doe",
            LocalDate.of(2010, 1, 1),
            "123 Main St",
            "1234567890",
            "john@example.com",
            "9876543210",
            "Forward",
            "High School",
            "Male",
            null,
            Collections.emptyList());

    sport = new Sport();
    sport.setId(1L);
    sport.setName("Football");
  }

  @Test
  void getAllStudents_WhenAcademyExists_ShouldReturnPageOfStudents() {
    // Given
    Pageable pageable = PageRequest.of(0, 10);
    Page<Student> studentPage = new PageImpl<>(List.of(student), pageable, 1);
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    when(studentRepository.findByAcademyId(1L, pageable)).thenReturn(studentPage);
    when(studentMapper.toResponse(student)).thenReturn(studentResponse);

    // When
    Page<StudentResponse> result = studentService.getAllStudents(1L, pageable);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0).name()).isEqualTo("John Doe");
    verify(studentRepository).findByAcademyId(1L, pageable);
  }

  @Test
  void getAllStudents_WhenAcademyNotExists_ShouldThrowException() {
    // Given
    Pageable pageable = PageRequest.of(0, 10);
    when(academyRepository.findById(anyLong())).thenReturn(Optional.empty());

    // When/Then
    assertThatThrownBy(() -> studentService.getAllStudents(1L, pageable))
        .isInstanceOf(AcademyNotFoundException.class);
  }

  @Test
  void getStudentById_WhenExists_ShouldReturnStudent() {
    // Given
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    when(studentRepository.findByAcademyIdAndId(1L, 1L)).thenReturn(Optional.of(student));
    when(studentMapper.toResponse(student)).thenReturn(studentResponse);

    // When
    StudentResponse result = studentService.getStudentById(1L, 1L);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.name()).isEqualTo("John Doe");
    verify(studentRepository).findByAcademyIdAndId(1L, 1L);
  }

  @Test
  void getStudentById_WhenNotExists_ShouldThrowException() {
    // Given
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    when(studentRepository.findByAcademyIdAndId(anyLong(), anyLong()))
        .thenReturn(Optional.empty());

    // When/Then
    assertThatThrownBy(() -> studentService.getStudentById(1L, 1L))
        .isInstanceOf(StudentNotFoundException.class);
  }

  @Test
  void createStudent_ShouldSaveAndReturnStudent() {
    // Given
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    when(studentMapper.toEntity(studentRequest)).thenReturn(student);
    when(studentRepository.save(any(Student.class))).thenReturn(student);
    when(studentMapper.toResponse(student)).thenReturn(studentResponse);

    // When
    StudentResponse result = studentService.createStudent(1L, studentRequest);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.name()).isEqualTo("John Doe");
    verify(studentRepository).save(any(Student.class));
  }

  @Test
  void enrollStudentInSport_WhenBothExist_ShouldEnroll() {
    // Given
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    when(studentRepository.findByAcademyIdAndId(1L, 1L)).thenReturn(Optional.of(student));
    when(sportRepository.findById(1L)).thenReturn(Optional.of(sport));

    // When
    studentService.enrollStudentInSport(1L, 1L, 1L);

    // Then
    verify(studentRepository).findByAcademyIdAndId(1L, 1L);
    verify(sportRepository).findById(1L);
    verify(studentRepository).save(student);
  }

  @Test
  void enrollStudentInSport_WhenStudentNotExists_ShouldThrowException() {
    // Given
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    when(studentRepository.findByAcademyIdAndId(anyLong(), anyLong()))
        .thenReturn(Optional.empty());

    // When/Then
    assertThatThrownBy(() -> studentService.enrollStudentInSport(1L, 1L, 1L))
        .isInstanceOf(StudentNotFoundException.class);
  }

  @Test
  void enrollStudentInSport_WhenSportNotExists_ShouldThrowException() {
    // Given
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    when(studentRepository.findByAcademyIdAndId(1L, 1L)).thenReturn(Optional.of(student));
    when(sportRepository.findById(anyLong())).thenReturn(Optional.empty());

    // When/Then
    assertThatThrownBy(() -> studentService.enrollStudentInSport(1L, 1L, 1L))
        .isInstanceOf(ResourceNotFoundException.class);
  }
}

