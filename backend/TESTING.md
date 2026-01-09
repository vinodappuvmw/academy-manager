# Testing Guide

## Test Structure

The project includes comprehensive unit and integration tests:

- **Unit Tests**: Test individual service methods with mocked dependencies
- **Integration Tests**: Test full request/response cycle with real database (Testcontainers)

## Running Tests

### Run All Tests
```bash
./gradlew test
```

### Run Unit Tests Only
```bash
./gradlew test --tests "*Test"
```

### Run Integration Tests Only
```bash
./gradlew test --tests "*IntegrationTest"
```

### Run Specific Test Class
```bash
./gradlew test --tests "AcademyServiceTest"
```

### Run Tests with Coverage
```bash
./gradlew test jacocoTestReport
# View report: build/reports/jacoco/test/html/index.html
```

## Test Configuration

### Test Profile
Tests use the `test` profile defined in `application-test.yml`:
- Uses Testcontainers for PostgreSQL
- Random server port
- Reduced logging

### Testcontainers
Integration tests use Testcontainers to spin up real PostgreSQL instances:
- Automatically starts/stops containers
- Isolated test database
- No manual database setup required

## Writing Tests

### Unit Test Example
```java
@ExtendWith(MockitoExtension.class)
class MyServiceTest {
  @Mock private MyRepository repository;
  @InjectMocks private MyServiceImpl service;
  
  @Test
  void testMethod_ShouldDoSomething() {
    // Given
    when(repository.findById(1L)).thenReturn(Optional.of(entity));
    
    // When
    Result result = service.doSomething(1L);
    
    // Then
    assertThat(result).isNotNull();
    verify(repository).findById(1L);
  }
}
```

### Integration Test Example
```java
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class MyControllerIntegrationTest {
  @Container
  static PostgreSQLContainer<?> postgres = 
      new PostgreSQLContainer<>("postgres:14-alpine");
  
  @Autowired private MockMvc mockMvc;
  
  @Test
  void testEndpoint_ShouldReturn200() throws Exception {
    mockMvc.perform(get("/api/v1/endpoint"))
        .andExpect(status().isOk());
  }
}
```

## Test Coverage

Current test coverage includes:
- ✅ AcademyService (unit tests)
- ✅ StudentService (unit tests)
- ✅ AcademyController (integration tests)

### Adding More Tests

To add tests for other services/controllers:

1. **Service Unit Test**: Create `*ServiceTest.java` in `src/test/java/com/academy/service/`
2. **Controller Integration Test**: Create `*ControllerIntegrationTest.java` in `src/test/java/com/academy/controller/`

Follow the existing patterns for consistency.

## CI/CD Integration

Tests run automatically in CI:
- All tests must pass before merge
- Code quality checks run before tests
- Test results are uploaded as artifacts

## Best Practices

1. **Test Naming**: Use descriptive names: `methodName_condition_expectedResult`
2. **Arrange-Act-Assert**: Structure tests clearly
3. **Mock External Dependencies**: Use mocks for repositories, external services
4. **Test Edge Cases**: Include null checks, not found scenarios
5. **Keep Tests Fast**: Unit tests should be very fast, integration tests reasonable
6. **Isolation**: Each test should be independent
7. **Cleanup**: Use `@Transactional` in integration tests for automatic rollback

## Troubleshooting

### Tests Fail with Database Connection
- Ensure Docker is running (for Testcontainers)
- Check PostgreSQL container logs

### Tests Timeout
- Increase timeout in test configuration
- Check for hanging database connections

### Mockito Errors
- Ensure `@ExtendWith(MockitoExtension.class)` is present
- Verify mocks are properly initialized

