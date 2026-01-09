package com.academy.controller;

import com.academy.domain.Academy;
import com.academy.repository.AcademyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@Transactional
class AcademyControllerIntegrationTest {

  @Container
  static PostgreSQLContainer<?> postgres =
      new PostgreSQLContainer<>("postgres:14-alpine")
          .withDatabaseName("academy_test")
          .withUsername("test")
          .withPassword("test");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private AcademyRepository academyRepository;

  private Academy academy;

  @BeforeEach
  void setUp() {
    academyRepository.deleteAll();
    academy = new Academy();
    academy.setName("Test Academy");
    academy.setAddress("123 Test St");
    academy.setPhone("1234567890");
    academy.setEmail("test@academy.com");
    academy = academyRepository.save(academy);
  }

  @Test
  void getAllAcademies_ShouldReturn200() throws Exception {
    mockMvc
        .perform(get("/api/v1/academies").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content[0].name").value("Test Academy"));
  }

  @Test
  void getAcademyById_WhenExists_ShouldReturn200() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/academies/{id}", academy.getId())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(academy.getId()))
        .andExpect(jsonPath("$.name").value("Test Academy"));
  }

  @Test
  void getAcademyById_WhenNotExists_ShouldReturn404() throws Exception {
    mockMvc
        .perform(get("/api/v1/academies/{id}", 999L).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void createAcademy_WithValidData_ShouldReturn201() throws Exception {
    String requestBody =
        """
        {
          "name": "New Academy",
          "address": "456 New St",
          "phone": "9876543210",
          "email": "new@academy.com"
        }
        """;

    mockMvc
        .perform(
            post("/api/v1/academies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("New Academy"))
        .andExpect(jsonPath("$.id").exists());
  }

  @Test
  void createAcademy_WithInvalidData_ShouldReturn400() throws Exception {
    String requestBody = "{\"name\": \"\"}"; // Invalid: empty name

    mockMvc
        .perform(
            post("/api/v1/academies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateAcademy_WhenExists_ShouldReturn200() throws Exception {
    String requestBody =
        """
        {
          "name": "Updated Academy",
          "address": "789 Updated St",
          "phone": "1112223333",
          "email": "updated@academy.com"
        }
        """;

    mockMvc
        .perform(
            put("/api/v1/academies/{id}", academy.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated Academy"));
  }

  @Test
  void updateAcademy_WhenNotExists_ShouldReturn404() throws Exception {
    String requestBody =
        """
        {
          "name": "Updated Academy",
          "address": "789 Updated St",
          "phone": "1112223333",
          "email": "updated@academy.com"
        }
        """;

    mockMvc
        .perform(
            put("/api/v1/academies/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteAcademy_WhenExists_ShouldReturn204() throws Exception {
    mockMvc
        .perform(delete("/api/v1/academies/{id}", academy.getId()))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteAcademy_WhenNotExists_ShouldReturn404() throws Exception {
    mockMvc
        .perform(delete("/api/v1/academies/{id}", 999L))
        .andExpect(status().isNotFound());
  }
}

