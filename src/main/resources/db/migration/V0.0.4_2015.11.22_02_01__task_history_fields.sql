ALTER TABLE task ADD COLUMN created_by BIGINT NOT NULL;

ALTER TABLE task ADD COLUMN created_at TIMESTAMP NOT NULL;

ALTER TABLE task ADD COLUMN updated_by BIGINT NOT NULL;

ALTER TABLE task ADD COLUMN updated_at TIMESTAMP NOT NULL;