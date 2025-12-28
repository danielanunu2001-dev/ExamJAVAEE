-- Add deleted flag for soft-delete
ALTER TABLE destinations
    ADD COLUMN IF NOT EXISTS deleted boolean NOT NULL DEFAULT false;
