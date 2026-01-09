-- Add home workout table for coach-suggested workouts by sport

-- ============================================
-- HOME WORKOUT TABLE
-- ============================================
CREATE TABLE home_workout (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    coach_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    sport_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    video_links JSONB,
    image_url VARCHAR(512),
    difficulty_level VARCHAR(50),
    duration_minutes INT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_home_workout_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE,
    CONSTRAINT fk_home_workout_coach FOREIGN KEY (coach_id) REFERENCES coach(id) ON DELETE CASCADE,
    CONSTRAINT fk_home_workout_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    CONSTRAINT fk_home_workout_sport FOREIGN KEY (sport_id) REFERENCES sport(id) ON DELETE RESTRICT
);

CREATE INDEX idx_home_workout_academy_id ON home_workout(academy_id);
CREATE INDEX idx_home_workout_coach_id ON home_workout(coach_id);
CREATE INDEX idx_home_workout_student_id ON home_workout(student_id);
CREATE INDEX idx_home_workout_sport_id ON home_workout(sport_id);
CREATE INDEX idx_home_workout_student_sport ON home_workout(student_id, sport_id);
CREATE INDEX idx_home_workout_created_at ON home_workout(created_at DESC);

