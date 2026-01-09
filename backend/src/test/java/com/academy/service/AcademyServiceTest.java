package com.academy.service;

import com.academy.domain.Academy;
import com.academy.domain.Sport;
import com.academy.dto.request.AcademyRequest;
import com.academy.dto.response.AcademyResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.mapper.AcademyMapper;
import com.academy.repository.AcademyRepository;
import com.academy.repository.SportRepository;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcademyServiceTest {

  @Mock private AcademyRepository academyRepository;
  @Mock private SportRepository sportRepository;
  @Mock private AcademyMapper academyMapper;

  @InjectMocks private AcademyServiceImpl academyService;

  private Academy academy;
  private AcademyRequest academyRequest;
  private AcademyResponse academyResponse;
  private Sport sport;

  @BeforeEach
  void setUp() {
    academy = new Academy();
    academy.setId(1L);
    academy.setName("Test Academy");
    academy.setAddress("123 Test St");
    academy.setPhoneNumber("1234567890");
    academy.setEmail("test@academy.com");

    academyRequest =
        new AcademyRequest(
            "Test Academy", "123 Test St", "1234567890", "test@academy.com", null, "http://logo.com/logo.png");

    academyResponse =
        new AcademyResponse(1L, "Test Academy", "123 Test St", "1234567890", "test@academy.com", "http://logo.com/logo.png", Collections.emptyList());

    sport = new Sport();
    sport.setId(1L);
    sport.setName("Football");
  }

  @Test
  void getAllAcademies_ShouldReturnPageOfAcademies() {
    // Given
    Pageable pageable = PageRequest.of(0, 10);
    Page<Academy> academyPage = new PageImpl<>(List.of(academy), pageable, 1);
    when(academyRepository.findAll(pageable)).thenReturn(academyPage);
    when(academyMapper.toResponse(academy)).thenReturn(academyResponse);

    // When
    Page<AcademyResponse> result = academyService.getAllAcademies(pageable);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0).name()).isEqualTo("Test Academy");
    verify(academyRepository).findAll(pageable);
  }

  @Test
  void getAcademyById_WhenExists_ShouldReturnAcademy() {
    // Given
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    when(academyMapper.toResponse(academy)).thenReturn(academyResponse);

    // When
    AcademyResponse result = academyService.getAcademyById(1L);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.name()).isEqualTo("Test Academy");
    verify(academyRepository).findById(1L);
  }

  @Test
  void getAcademyById_WhenNotExists_ShouldThrowException() {
    // Given
    when(academyRepository.findById(anyLong())).thenReturn(Optional.empty());

    // When/Then
    assertThatThrownBy(() -> academyService.getAcademyById(1L))
        .isInstanceOf(AcademyNotFoundException.class);
    verify(academyRepository).findById(1L);
  }

  @Test
  void createAcademy_ShouldSaveAndReturnAcademy() {
    // Given
    when(academyMapper.toEntity(academyRequest)).thenReturn(academy);
    when(academyRepository.save(any(Academy.class))).thenReturn(academy);
    when(academyMapper.toResponse(academy)).thenReturn(academyResponse);

    // When
    AcademyResponse result = academyService.createAcademy(academyRequest);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.name()).isEqualTo("Test Academy");
    verify(academyRepository).save(any(Academy.class));
  }

  @Test
  void updateAcademy_WhenExists_ShouldUpdateAndReturnAcademy() {
    // Given
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    when(academyRepository.save(any(Academy.class))).thenReturn(academy);
    when(academyMapper.toResponse(academy)).thenReturn(academyResponse);

    // When
    AcademyResponse result = academyService.updateAcademy(1L, academyRequest);

    // Then
    assertThat(result).isNotNull();
    verify(academyRepository).findById(1L);
    verify(academyRepository).save(academy);
  }

  @Test
  void updateAcademy_WhenNotExists_ShouldThrowException() {
    // Given
    when(academyRepository.findById(anyLong())).thenReturn(Optional.empty());

    // When/Then
    assertThatThrownBy(() -> academyService.updateAcademy(1L, academyRequest))
        .isInstanceOf(AcademyNotFoundException.class);
    verify(academyRepository, never()).save(any());
  }

  @Test
  void deleteAcademy_WhenExists_ShouldDelete() {
    // Given
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    doNothing().when(academyRepository).delete(academy);

    // When
    academyService.deleteAcademy(1L);

    // Then
    verify(academyRepository).findById(1L);
    verify(academyRepository).delete(academy);
  }

  @Test
  void deleteAcademy_WhenNotExists_ShouldThrowException() {
    // Given
    when(academyRepository.findById(anyLong())).thenReturn(Optional.empty());

    // When/Then
    assertThatThrownBy(() -> academyService.deleteAcademy(1L))
        .isInstanceOf(AcademyNotFoundException.class);
    verify(academyRepository, never()).delete(any());
  }

  @Test
  void addSportToAcademy_WhenBothExist_ShouldAddSport() {
    // Given
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    when(sportRepository.findById(1L)).thenReturn(Optional.of(sport));

    // When
    academyService.addSportToAcademy(1L, 1L);

    // Then
    verify(academyRepository).findById(1L);
    verify(sportRepository).findById(1L);
    verify(academyRepository).save(academy);
  }

  @Test
  void addSportToAcademy_WhenAcademyNotExists_ShouldThrowException() {
    // Given
    when(academyRepository.findById(anyLong())).thenReturn(Optional.empty());

    // When/Then
    assertThatThrownBy(() -> academyService.addSportToAcademy(1L, 1L))
        .isInstanceOf(AcademyNotFoundException.class);
    verify(sportRepository, never()).findById(anyLong());
  }

  @Test
  void addSportToAcademy_WhenSportNotExists_ShouldThrowException() {
    // Given
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    when(sportRepository.findById(anyLong())).thenReturn(Optional.empty());

    // When/Then
    assertThatThrownBy(() -> academyService.addSportToAcademy(1L, 1L))
        .isInstanceOf(ResourceNotFoundException.class);
  }

  @Test
  void removeSportFromAcademy_WhenBothExist_ShouldRemoveSport() {
    // Given
    academy.getSports().add(sport);
    when(academyRepository.findById(1L)).thenReturn(Optional.of(academy));
    when(sportRepository.findById(1L)).thenReturn(Optional.of(sport));

    // When
    academyService.removeSportFromAcademy(1L, 1L);

    // Then
    verify(academyRepository).findById(1L);
    verify(sportRepository).findById(1L);
    verify(academyRepository).save(academy);
  }
}

