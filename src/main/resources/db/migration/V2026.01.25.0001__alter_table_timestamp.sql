ALTER TABLE prod.board
ALTER COLUMN created_at TYPE varchar USING created_at::text,
    ALTER COLUMN updated_at TYPE varchar USING updated_at::text;


ALTER TABLE prod.story
ALTER COLUMN created_at TYPE varchar USING created_at::text,
    ALTER COLUMN updated_at TYPE varchar USING updated_at::text;

ALTER TABLE prod.persona
ALTER COLUMN created_at TYPE varchar USING created_at::text,
    ALTER COLUMN updated_at TYPE varchar USING updated_at::text;

ALTER TABLE prod.story
ALTER COLUMN created_at TYPE varchar USING created_at::text,
    ALTER COLUMN updated_at TYPE varchar USING updated_at::text