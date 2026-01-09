-- Update student_rating table to support sport-based ratings and history

-- Add sport_id column (required)
-- Note: Adding as nullable first, then making NOT NULL after ensuring all records have sport_id
ALTER TABLE student_rating ADD COLUMN sport_id BIGINT;
ALTER TABLE student_rating ADD CONSTRAINT fk_student_rating_sport FOREIGN KEY (sport_id) REFERENCES sport(id) ON DELETE RESTRICT;

-- Remove the unique constraint that prevents rating history
ALTER TABLE student_rating DROP CONSTRAINT IF EXISTS uk_student_rating_unique;

-- Create new index for sport_id
CREATE INDEX idx_student_rating_sport_id ON student_rating(sport_id);

-- Create composite index for efficient queries by student and sport
CREATE INDEX idx_student_rating_student_sport ON student_rating(student_id, sport_id);

-- Create index for rating history queries (student, sport, date)
CREATE INDEX idx_student_rating_history ON student_rating(student_id, sport_id, rating_date DESC);

-- Update existing records: set sport_id to NULL temporarily (will need to be updated via application)
-- Note: This allows the column to be added, but existing ratings will need sport_id to be set
-- For production, you may want to set a default sport or handle this differently

-- Make sport_id NOT NULL after data migration (commented out - uncomment after data is migrated)
-- ALTER TABLE student_rating ALTER COLUMN sport_id SET NOT NULL;

