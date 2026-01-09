package com.academy.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "training_centre")
public class TrainingCentre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "academy_id", nullable = false)
  private Academy academy;

  @Column(nullable = false)
  private String name;

  @Column(name = "google_maps_link", length = 512)
  private String googleMapsLink;

  @Column(columnDefinition = "TEXT")
  private String address;

  @Column(name = "pin_code", length = 20)
  private String pinCode;

  @Column(length = 100)
  private String state;

  @Column(length = 100)
  private String country;

  @Column(name = "registration_number", length = 100)
  private String registrationNumber;

  @Column(name = "affiliation_body", length = 255)
  private String affiliationBody;

  @Column(name = "established_year")
  private Integer establishedYear;

  @Column(length = 100)
  private String city;

  @Column(length = 255)
  private String email;

  @Column(length = 255)
  private String website;

  @Column(name = "number_of_pitches")
  private Integer numberOfPitches;

  @Column(name = "pitch_type", length = 50)
  private String pitchType;

  @Column(name = "pitch_dimensions", length = 100)
  private String pitchDimensions;

  @Column(name = "lighting_available")
  private Boolean lightingAvailable;

  @Column(name = "gym_available")
  private Boolean gymAvailable;

  @Column(name = "physio_room")
  private Boolean physioRoom;

  @Column(name = "changing_rooms")
  private Boolean changingRooms;

  @Column(name = "hostel_facility")
  private Boolean hostelFacility;

  @OneToMany(mappedBy = "trainingCentre", cascade = CascadeType.ALL)
  private List<TrainingSession> sessions = new ArrayList<>();

  @OneToMany(mappedBy = "trainingCentre", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Expense> expenses = new ArrayList<>();

  public TrainingCentre() {}

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

  public String getGoogleMapsLink() {
    return googleMapsLink;
  }

  public void setGoogleMapsLink(String googleMapsLink) {
    this.googleMapsLink = googleMapsLink;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPinCode() {
    return pinCode;
  }

  public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public String getAffiliationBody() {
    return affiliationBody;
  }

  public void setAffiliationBody(String affiliationBody) {
    this.affiliationBody = affiliationBody;
  }

  public Integer getEstablishedYear() {
    return establishedYear;
  }

  public void setEstablishedYear(Integer establishedYear) {
    this.establishedYear = establishedYear;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public Integer getNumberOfPitches() {
    return numberOfPitches;
  }

  public void setNumberOfPitches(Integer numberOfPitches) {
    this.numberOfPitches = numberOfPitches;
  }

  public String getPitchType() {
    return pitchType;
  }

  public void setPitchType(String pitchType) {
    this.pitchType = pitchType;
  }

  public String getPitchDimensions() {
    return pitchDimensions;
  }

  public void setPitchDimensions(String pitchDimensions) {
    this.pitchDimensions = pitchDimensions;
  }

  public Boolean getLightingAvailable() {
    return lightingAvailable;
  }

  public void setLightingAvailable(Boolean lightingAvailable) {
    this.lightingAvailable = lightingAvailable;
  }

  public Boolean getGymAvailable() {
    return gymAvailable;
  }

  public void setGymAvailable(Boolean gymAvailable) {
    this.gymAvailable = gymAvailable;
  }

  public Boolean getPhysioRoom() {
    return physioRoom;
  }

  public void setPhysioRoom(Boolean physioRoom) {
    this.physioRoom = physioRoom;
  }

  public Boolean getChangingRooms() {
    return changingRooms;
  }

  public void setChangingRooms(Boolean changingRooms) {
    this.changingRooms = changingRooms;
  }

  public Boolean getHostelFacility() {
    return hostelFacility;
  }

  public void setHostelFacility(Boolean hostelFacility) {
    this.hostelFacility = hostelFacility;
  }

  public List<TrainingSession> getSessions() {
    return sessions;
  }

  public void setSessions(List<TrainingSession> sessions) {
    this.sessions = sessions;
  }

  public List<Expense> getExpenses() {
    return expenses;
  }

  public void setExpenses(List<Expense> expenses) {
    this.expenses = expenses;
  }
}

