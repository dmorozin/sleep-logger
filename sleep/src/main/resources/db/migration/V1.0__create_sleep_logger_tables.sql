CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
    );


CREATE TABLE IF NOT EXISTS sleep_log (
    id SERIAL PRIMARY KEY,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    total_time INTEGER NOT NULL CHECK (total_time >= 0),
    user_feel VARCHAR(10) NOT NULL CHECK (user_feel IN ('BAD', 'OK', 'GOOD')),
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE
    );

CREATE INDEX idx_sleep_log_user_id ON sleep_log(user_id);
