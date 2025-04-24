ALTER TABLE sleep_log ADD CONSTRAINT user_sleep_date_constraint UNIQUE (sleep_date, user_id);
