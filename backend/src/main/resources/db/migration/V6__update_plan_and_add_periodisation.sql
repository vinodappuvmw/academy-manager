-- Update plan table to be session-specific with coach and image
-- Add periodisation table for development plans

-- ============================================
-- UPDATE PLAN TABLE
-- ============================================
ALTER TABLE plan ADD COLUMN coach_id BIGINT;
ALTER TABLE plan ADD COLUMN image_url VARCHAR(512);
ALTER TABLE plan ADD CONSTRAINT fk_plan_coach FOREIGN KEY (coach_id) REFERENCES coach(id) ON DELETE SET NULL;
CREATE INDEX idx_plan_coach_id ON plan(coach_id);

-- ============================================
-- PERIODISATION TABLE
-- ============================================
CREATE TABLE periodisation (
    id BIGSERIAL PRIMARY KEY,
    academy_id BIGINT NOT NULL,
    student_id BIGINT,
    training_program_id BIGINT,
    weekly_plan TEXT,
    monthly_plan TEXT,
    yearly_plan TEXT,
    weekly_plan_image_url VARCHAR(512),
    monthly_plan_image_url VARCHAR(512),
    yearly_plan_image_url VARCHAR(512),
    created_by_coach_id BIGINT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    CONSTRAINT fk_periodisation_academy FOREIGN KEY (academy_id) REFERENCES academy(id) ON DELETE CASCADE,
    CONSTRAINT fk_periodisation_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
    CONSTRAINT fk_periodisation_program FOREIGN KEY (training_program_id) REFERENCES training_program(id) ON DELETE CASCADE,
    CONSTRAINT fk_periodisation_coach FOREIGN KEY (created_by_coach_id) REFERENCES coach(id) ON DELETE SET NULL,
    CONSTRAINT chk_periodisation_target CHECK (
        (student_id IS NOT NULL AND training_program_id IS NULL) OR
        (student_id IS NULL AND training_program_id IS NOT NULL)
    )
);

CREATE INDEX idx_periodisation_academy_id ON periodisation(academy_id);
CREATE INDEX idx_periodisation_student_id ON periodisation(student_id);
CREATE INDEX idx_periodisation_program_id ON periodisation(training_program_id);
CREATE INDEX idx_periodisation_coach_id ON periodisation(created_by_coach_id);
CREATE INDEX idx_periodisation_created_at ON periodisation(created_at DESC);

