CREATE TABLE Users (
                       id BIGSERIAL PRIMARY KEY,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100),
                       email VARCHAR(255) NULL,
                       phone_number VARCHAR(30) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       qr_code_id VARCHAR(255) UNIQUE,
                       avatar_url VARCHAR(512),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE Roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE User_Roles (
                            user_id BIGINT NOT NULL REFERENCES Users(id) ON DELETE CASCADE,
                            role_id BIGINT NOT NULL REFERENCES Roles(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);

CREATE TABLE News (
                      id BIGSERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      content TEXT NOT NULL,
                      image_url VARCHAR(512),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      user_id BIGINT REFERENCES Users(id) ON DELETE SET NULL
);

CREATE TABLE Membership_Types (
                                  id BIGSERIAL PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL,
                                  description TEXT,
                                  price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
                                  duration_days INTEGER NOT NULL CHECK (duration_days > 0)
);

CREATE TABLE Memberships (
                             id BIGSERIAL PRIMARY KEY,
                             start_date DATE NOT NULL,
                             end_date DATE NOT NULL,
                             is_active BOOLEAN DEFAULT true,
                             user_id BIGINT NOT NULL REFERENCES Users(id) ON DELETE CASCADE,
                             type_id BIGINT NOT NULL REFERENCES Membership_Types(id) ON DELETE RESTRICT,
                             CHECK (end_date > start_date)
);

CREATE TABLE Trainers (
                          id BIGSERIAL PRIMARY KEY,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          specialization VARCHAR(255),
                          bio TEXT,
                          photo_url VARCHAR(512)
);

CREATE TABLE Group_Classes (
                               id BIGSERIAL PRIMARY KEY,
                               name VARCHAR(255) NOT NULL,
                               description TEXT NOT NULL,
                               default_duration_minutes INTEGER CHECK (default_duration_minutes > 0)
);

CREATE TABLE Schedule (
                          id BIGSERIAL PRIMARY KEY,
                          start_time TIMESTAMP NOT NULL,
                          end_time TIMESTAMP NOT NULL,
                          participant_limit INTEGER DEFAULT 0 CHECK (participant_limit >= 0),
                          class_id BIGINT NOT NULL REFERENCES Group_Classes(id) ON DELETE CASCADE,
                          trainer_id BIGINT NOT NULL REFERENCES Trainers(id) ON DELETE CASCADE,
                          CHECK (end_time > start_time)
);

CREATE TABLE Bookings (
                          id BIGSERIAL PRIMARY KEY,
                          booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          status VARCHAR(50) DEFAULT 'Підтверджено',
                          user_id BIGINT NOT NULL REFERENCES Users(id) ON DELETE CASCADE,
                          schedule_id BIGINT NOT NULL REFERENCES Schedule(id) ON DELETE CASCADE,
                          UNIQUE (user_id, schedule_id)
);

INSERT INTO roles(name) VALUES('ROLE_CLIENT');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');