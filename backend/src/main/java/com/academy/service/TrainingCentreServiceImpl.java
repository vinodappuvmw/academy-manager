package com.academy.service;

import com.academy.domain.Academy;
import com.academy.domain.TrainingCentre;
import com.academy.dto.request.TrainingCentreRequest;
import com.academy.dto.response.TrainingCentreResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.mapper.TrainingCentreMapper;
import com.academy.repository.AcademyRepository;
import com.academy.repository.TrainingCentreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainingCentreServiceImpl implements TrainingCentreService {

  private final TrainingCentreRepository trainingCentreRepository;
  private final AcademyRepository academyRepository;
  private final TrainingCentreMapper trainingCentreMapper;

  public TrainingCentreServiceImpl(
      TrainingCentreRepository trainingCentreRepository,
      AcademyRepository academyRepository,
      TrainingCentreMapper trainingCentreMapper) {
    this.trainingCentreRepository = trainingCentreRepository;
    this.academyRepository = academyRepository;
    this.trainingCentreMapper = trainingCentreMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TrainingCentreResponse> getAllTrainingCentres(Long academyId, Pageable pageable) {
    return trainingCentreRepository
        .findByAcademyId(academyId, pageable)
        .map(trainingCentreMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public TrainingCentreResponse getTrainingCentreById(Long academyId, Long id) {
    TrainingCentre trainingCentre =
        trainingCentreRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Training centre not found with id: " + id));
    return trainingCentreMapper.toResponse(trainingCentre);
  }

  @Override
  @Transactional
  public TrainingCentreResponse createTrainingCentre(
      Long academyId, TrainingCentreRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));
    TrainingCentre trainingCentre = trainingCentreMapper.toEntity(request);
    trainingCentre.setAcademy(academy);
    TrainingCentre saved = trainingCentreRepository.save(trainingCentre);
    return trainingCentreMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public TrainingCentreResponse updateTrainingCentre(
      Long academyId, Long id, TrainingCentreRequest request) {
    TrainingCentre trainingCentre =
        trainingCentreRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Training centre not found with id: " + id));
    trainingCentre.setName(request.name());
    trainingCentre.setGoogleMapsLink(request.googleMapsLink());
    trainingCentre.setAddress(request.address());
    trainingCentre.setPinCode(request.pinCode());
    trainingCentre.setState(request.state());
    trainingCentre.setCountry(request.country());
    trainingCentre.setRegistrationNumber(request.registrationNumber());
    trainingCentre.setAffiliationBody(request.affiliationBody());
    trainingCentre.setEstablishedYear(request.establishedYear());
    trainingCentre.setCity(request.city());
    trainingCentre.setEmail(request.email());
    trainingCentre.setWebsite(request.website());
    trainingCentre.setNumberOfPitches(request.numberOfPitches());
    trainingCentre.setPitchType(request.pitchType());
    trainingCentre.setPitchDimensions(request.pitchDimensions());
    trainingCentre.setLightingAvailable(request.lightingAvailable());
    trainingCentre.setGymAvailable(request.gymAvailable());
    trainingCentre.setPhysioRoom(request.physioRoom());
    trainingCentre.setChangingRooms(request.changingRooms());
    trainingCentre.setHostelFacility(request.hostelFacility());
    TrainingCentre updated = trainingCentreRepository.save(trainingCentre);
    return trainingCentreMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteTrainingCentre(Long academyId, Long id) {
    TrainingCentre trainingCentre =
        trainingCentreRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Training centre not found with id: " + id));
    trainingCentreRepository.delete(trainingCentre);
  }
}

