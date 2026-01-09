-- Academy Manager Database Schema
-- Version 1: Initial schema creation

-- ============================================
-- 1. ACADEMY TABLE
-- ============================================
CREATE TABLE academy (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address TEXT,
    phone VARCHAR(50),
    email VARCHAR(255),
    website VARCHAR(255)
);

CREATE INDEX idx_academy_name ON academy(name);

-- ============================================
-- 2. COACH TABLE
-- ============================================
CREATE TABLE coach (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    address TEXT,
    phone VARCHAR(50),
    email VARCHAR(255),
    sport_subject VARCHAR(255),
    gender VARCHAR(20),
    years_experience INT,
    qualifications TEXT,
    specialization TEXT,
    date_of_birth DATE,
    photo_url VARCHAR(512),
    CONSTRAINT fk_coach_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE
);

CREATE INDEX idx_coach_academy_id ON coach(academy_id);
CREATE INDEX idx_coach_email ON coach(email);
CREATE INDEX idx_coach_phone ON coach(phone);

-- ============================================
-- 3. STUDENT TABLE
-- ============================================
CREATE TABLE student (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    date_of_birth DATE,
    address TEXT,
    phone VARCHAR(50),
    email VARCHAR(255),
    emergency_contact VARCHAR(50),
    position VARCHAR(100),
    highest_education VARCHAR(255),
    gender VARCHAR(20),
    photo_url VARCHAR(512),
    CONSTRAINT fk_student_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE
);

CREATE INDEX idx_student_academy_id ON student(academy_id);
CREATE INDEX idx_student_email ON student(email);
CREATE INDEX idx_student_phone ON student(phone);

-- ============================================
-- 4. TRAINING CENTRE TABLE
-- ============================================
CREATE TABLE training_centre (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    google_maps_link VARCHAR(512),
    address TEXT,
    pin_code VARCHAR(20),
    state VARCHAR(100),
    country VARCHAR(100),
    registration_number VARCHAR(100),
    affiliation_body VARCHAR(255),
    established_year INT,
    city VARCHAR(100),
    email VARCHAR(255),
    website VARCHAR(255),
    number_of_pitches INT,
    pitch_type VARCHAR(50),
    pitch_dimensions VARCHAR(100),
    lighting_available BOOLEAN,
    gym_available BOOLEAN,
    physio_room BOOLEAN,
    changing_rooms BOOLEAN,
    hostel_facility BOOLEAN,
    CONSTRAINT fk_training_centre_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE
);

CREATE INDEX idx_training_centre_academy_id ON training_centre(academy_id);
CREATE INDEX idx_training_centre_city ON training_centre(city);
CREATE INDEX idx_training_centre_state ON training_centre(state);

-- ============================================
-- 5. TRAINING PROGRAM TABLE
-- ============================================
CREATE TABLE training_program (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    CONSTRAINT fk_training_program_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE
);

CREATE INDEX idx_training_program_academy_id ON training_program(academy_id);

-- ============================================
-- 6. PLAN TABLE
-- ============================================
CREATE TABLE plan (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    CONSTRAINT fk_plan_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE
);

CREATE INDEX idx_plan_academy_id ON plan(academy_id);

-- ============================================
-- 7. TRAINING SESSION TABLE
-- ============================================
CREATE TABLE training_session (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    duration_minutes INT NOT NULL,
    training_centre_id BIGINT,
    program_id BIGINT,
    plan_id BIGINT,
    session_owner_id BIGINT,
    notes TEXT,
    CONSTRAINT fk_training_session_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE,
    CONSTRAINT fk_training_session_centre FOREIGN KEY (training_centre_id) REFERENCES training_centre(id) ON DELETE SET NULL,
    CONSTRAINT fk_training_session_program FOREIGN KEY (program_id) REFERENCES training_program(id) ON DELETE SET NULL,
    CONSTRAINT fk_training_session_plan FOREIGN KEY (plan_id) REFERENCES plan(id) ON DELETE SET NULL,
    CONSTRAINT fk_training_session_owner FOREIGN KEY (session_owner_id) REFERENCES coach(id) ON DELETE SET NULL
);

CREATE INDEX idx_training_session_academy_id ON training_session(academy_id);
CREATE INDEX idx_training_session_date ON training_session(date);
CREATE INDEX idx_training_session_centre_id ON training_session(training_centre_id);
CREATE INDEX idx_training_session_program_id ON training_session(program_id);
CREATE INDEX idx_training_session_owner_id ON training_session(session_owner_id);

-- ============================================
-- 8. TRAINING SESSION COACH (Many-to-Many)
-- ============================================
CREATE TABLE training_session_coach (
    training_session_id BIGINT NOT NULL,
    coach_id BIGINT NOT NULL,
    PRIMARY KEY (training_session_id, coach_id),
    CONSTRAINT fk_tsc_session FOREIGN KEY (training_session_id) REFERENCES training_session(id) ON DELETE CASCADE,
    CONSTRAINT fk_tsc_coach FOREIGN KEY (coach_id) REFERENCES coach(id) ON DELETE CASCADE
);

CREATE INDEX idx_tsc_coach_id ON training_session_coach(coach_id);

-- ============================================
-- 9. TRAINING SESSION STUDENT (Many-to-Many)
-- ============================================
CREATE TABLE training_session_student (
    training_session_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    PRIMARY KEY (training_session_id, student_id),
    CONSTRAINT fk_tss_session FOREIGN KEY (training_session_id) REFERENCES training_session(id) ON DELETE CASCADE,
    CONSTRAINT fk_tss_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);

CREATE INDEX idx_tss_student_id ON training_session_student(student_id);

-- ============================================
-- 10. EXPENSE TABLE
-- ============================================
CREATE TABLE expense (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    training_centre_id BIGINT,
    date DATE NOT NULL,
    amount NUMERIC(12,2) NOT NULL,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    payee VARCHAR(255),
    payment_method VARCHAR(50),
    reference_number VARCHAR(100),
    CONSTRAINT fk_expense_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE,
    CONSTRAINT fk_expense_centre FOREIGN KEY (training_centre_id) REFERENCES training_centre(id) ON DELETE SET NULL
);

CREATE INDEX idx_expense_academy_id ON expense(academy_id);
CREATE INDEX idx_expense_centre_id ON expense(training_centre_id);
CREATE INDEX idx_expense_date ON expense(date);
CREATE INDEX idx_expense_category ON expense(category);

-- ============================================
-- 11. STUDENT FEE TABLE
-- ============================================
CREATE TABLE student_fee (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    year INT NOT NULL,
    month INT NOT NULL CHECK (month >= 1 AND month <= 12),
    amount_due NUMERIC(12,2) NOT NULL,
    amount_paid NUMERIC(12,2) NOT NULL DEFAULT 0,
    due_date DATE,
    paid_date DATE,
    status VARCHAR(20) NOT NULL,
    notes TEXT,
    CONSTRAINT fk_student_fee_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE,
    CONSTRAINT fk_student_fee_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    CONSTRAINT uk_student_fee_unique UNIQUE (academy_id, student_id, year, month)
);

CREATE INDEX idx_student_fee_academy_id ON student_fee(academy_id);
CREATE INDEX idx_student_fee_student_id ON student_fee(student_id);
CREATE INDEX idx_student_fee_status ON student_fee(status);
CREATE INDEX idx_student_fee_year_month ON student_fee(year, month);

-- ============================================
-- 12. COACH SALARY TABLE
-- ============================================
CREATE TABLE coach_salary (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    coach_id BIGINT NOT NULL,
    year INT NOT NULL,
    month INT NOT NULL CHECK (month >= 1 AND month <= 12),
    base_salary NUMERIC(12,2) NOT NULL,
    bonus_amount NUMERIC(12,2) NOT NULL DEFAULT 0,
    amount_paid NUMERIC(12,2) NOT NULL DEFAULT 0,
    due_date DATE,
    paid_date DATE,
    status VARCHAR(20) NOT NULL,
    notes TEXT,
    CONSTRAINT fk_coach_salary_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE,
    CONSTRAINT fk_coach_salary_coach FOREIGN KEY (coach_id) REFERENCES coach(id) ON DELETE CASCADE,
    CONSTRAINT uk_coach_salary_unique UNIQUE (academy_id, coach_id, year, month)
);

CREATE INDEX idx_coach_salary_academy_id ON coach_salary(academy_id);
CREATE INDEX idx_coach_salary_coach_id ON coach_salary(coach_id);
CREATE INDEX idx_coach_salary_status ON coach_salary(status);
CREATE INDEX idx_coach_salary_year_month ON coach_salary(year, month);

-- ============================================
-- 13. STUDENT ATTENDANCE TABLE
-- ============================================
CREATE TABLE student_attendance (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    training_session_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    marked_by_coach_id BIGINT,
    marked_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    notes TEXT,
    CONSTRAINT fk_student_attendance_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE,
    CONSTRAINT fk_student_attendance_session FOREIGN KEY (training_session_id) REFERENCES training_session(id) ON DELETE CASCADE,
    CONSTRAINT fk_student_attendance_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    CONSTRAINT fk_student_attendance_coach FOREIGN KEY (marked_by_coach_id) REFERENCES coach(id) ON DELETE SET NULL,
    CONSTRAINT uk_student_attendance_unique UNIQUE (training_session_id, student_id)
);

CREATE INDEX idx_student_attendance_academy_id ON student_attendance(academy_id);
CREATE INDEX idx_student_attendance_session_id ON student_attendance(training_session_id);
CREATE INDEX idx_student_attendance_student_id ON student_attendance(student_id);
CREATE INDEX idx_student_attendance_status ON student_attendance(status);

-- ============================================
-- 14. RATING TEMPLATE TABLE
-- ============================================
CREATE TABLE rating_template (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    schema JSONB NOT NULL,
    CONSTRAINT fk_rating_template_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE
);

CREATE INDEX idx_rating_template_academy_id ON rating_template(academy_id);
CREATE INDEX idx_rating_template_active ON rating_template(academy_id, is_active);
CREATE INDEX idx_rating_template_schema ON rating_template USING GIN (schema);

-- ============================================
-- 15. STUDENT RATING TABLE
-- ============================================
CREATE TABLE student_rating (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    training_session_id BIGINT,
    rating_template_id BIGINT NOT NULL,
    rated_by_coach_id BIGINT,
    rating_date DATE NOT NULL,
    scores JSONB NOT NULL,
    comments TEXT,
    CONSTRAINT fk_student_rating_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE,
    CONSTRAINT fk_student_rating_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    CONSTRAINT fk_student_rating_session FOREIGN KEY (training_session_id) REFERENCES training_session(id) ON DELETE SET NULL,
    CONSTRAINT fk_student_rating_template FOREIGN KEY (rating_template_id) REFERENCES rating_template(id) ON DELETE RESTRICT,
    CONSTRAINT fk_student_rating_coach FOREIGN KEY (rated_by_coach_id) REFERENCES coach(id) ON DELETE SET NULL,
    CONSTRAINT uk_student_rating_unique UNIQUE (academy_id, student_id, training_session_id, rating_template_id)
);

CREATE INDEX idx_student_rating_academy_id ON student_rating(academy_id);
CREATE INDEX idx_student_rating_student_id ON student_rating(student_id);
CREATE INDEX idx_student_rating_session_id ON student_rating(training_session_id);
CREATE INDEX idx_student_rating_template_id ON student_rating(rating_template_id);
CREATE INDEX idx_student_rating_date ON student_rating(rating_date);
CREATE INDEX idx_student_rating_scores ON student_rating USING GIN (scores);

