-- V2__add_price_and_active.sql

ALTER TABLE destinations
  ADD COLUMN IF NOT EXISTS price numeric(10,2);

ALTER TABLE destinations
  ADD COLUMN IF NOT EXISTS active boolean DEFAULT true NOT NULL;
