-- Add sport support to academy system

-- ============================================
-- SPORT TABLE
-- ============================================
CREATE TABLE sport (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100)
);

CREATE INDEX idx_sport_name ON sport(name);
CREATE INDEX idx_sport_category ON sport(category);

-- ============================================
-- ACADEMY SPORT (Many-to-Many)
-- ============================================
CREATE TABLE academy_sport (
    academy_id BIGINT NOT NULL,
    sport_id BIGINT NOT NULL,
    PRIMARY KEY (academy_id, sport_id),
    CONSTRAINT fk_academy_sport_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE,
    CONSTRAINT fk_academy_sport_sport FOREIGN KEY (sport_id) REFERENCES sport(id) ON DELETE CASCADE
);

CREATE INDEX idx_academy_sport_sport_id ON academy_sport(sport_id);

-- ============================================
-- STUDENT SPORT ENROLLMENT (Many-to-Many)
-- ============================================
CREATE TABLE student_sport (
    student_id BIGINT NOT NULL,
    sport_id BIGINT NOT NULL,
    enrolled_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    PRIMARY KEY (student_id, sport_id),
    CONSTRAINT fk_student_sport_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    CONSTRAINT fk_student_sport_sport FOREIGN KEY (sport_id) REFERENCES sport(id) ON DELETE CASCADE
);

CREATE INDEX idx_student_sport_sport_id ON student_sport(sport_id);
CREATE INDEX idx_student_sport_status ON student_sport(status);

-- ============================================
-- COACH SPORT (Many-to-Many)
-- ============================================
CREATE TABLE coach_sport (
    coach_id BIGINT NOT NULL,
    sport_id BIGINT NOT NULL,
    PRIMARY KEY (coach_id, sport_id),
    CONSTRAINT fk_coach_sport_coach FOREIGN KEY (coach_id) REFERENCES coach(id) ON DELETE CASCADE,
    CONSTRAINT fk_coach_sport_sport FOREIGN KEY (sport_id) REFERENCES sport(id) ON DELETE CASCADE
);

CREATE INDEX idx_coach_sport_sport_id ON coach_sport(sport_id);

-- ============================================
-- ADD SPORT TO TRAINING SESSION
-- ============================================
ALTER TABLE training_session ADD COLUMN sport_id BIGINT;
ALTER TABLE training_session ADD CONSTRAINT fk_training_session_sport FOREIGN KEY (sport_id) REFERENCES sport(id) ON DELETE SET NULL;
CREATE INDEX idx_training_session_sport_id ON training_session(sport_id);

