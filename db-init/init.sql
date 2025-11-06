CREATE TABLE Users (
                       user_id SERIAL PRIMARY KEY,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100),
                       email VARCHAR(255) NOT NULL UNIQUE,
                       phone_number VARCHAR(20),
                       password VARCHAR(255) NOT NULL,
                       qr_code_id VARCHAR(255) UNIQUE,
                       avatar_url VARCHAR(512),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Roles (
                       role_id SERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE User_Roles (
                            user_id INTEGER NOT NULL REFERENCES Users(user_id) ON DELETE CASCADE,
                            role_id INTEGER NOT NULL REFERENCES Roles(role_id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);

CREATE TABLE News (
                      news_id SERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      content TEXT NOT NULL,
                      image_url VARCHAR(512),
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      user_id INTEGER REFERENCES Users(user_id) ON DELETE SET NULL -- Зв'язок з адміном
);

CREATE TABLE Membership_Types (
                                  type_id SERIAL PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL,
                                  description TEXT,
                                  price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
                                  duration_days INTEGER NOT NULL CHECK (duration_days > 0)
);

CREATE TABLE Memberships (
                             membership_id SERIAL PRIMARY KEY,
                             start_date DATE NOT NULL,
                             end_date DATE NOT NULL,
                             is_active BOOLEAN DEFAULT true,
                             user_id INTEGER NOT NULL REFERENCES Users(user_id) ON DELETE CASCADE,
                             type_id INTEGER NOT NULL REFERENCES Membership_Types(type_id) ON DELETE RESTRICT,
                             CHECK (end_date > start_date)
);

CREATE TABLE Trainers (
                          trainer_id SERIAL PRIMARY KEY,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          specialization VARCHAR(255),
                          bio TEXT,
                          photo_url VARCHAR(512)
);

CREATE TABLE Group_Classes (
                               class_id SERIAL PRIMARY KEY,
                               name VARCHAR(255) NOT NULL,
                               description TEXT NOT NULL,
                               default_duration_minutes INTEGER CHECK (default_duration_minutes > 0)
);

CREATE TABLE Schedule (
                          schedule_id SERIAL PRIMARY KEY,
                          start_time TIMESTAMP NOT NULL,
                          end_time TIMESTAMP NOT NULL,
                          participant_limit INTEGER DEFAULT 0 CHECK (participant_limit >= 0),
                          class_id INTEGER NOT NULL REFERENCES Group_Classes(class_id) ON DELETE CASCADE,
                          trainer_id INTEGER NOT NULL REFERENCES Trainers(trainer_id) ON DELETE CASCADE,
                          CHECK (end_time > start_time)
);

CREATE TABLE Bookings (
                          booking_id SERIAL PRIMARY KEY,
                          booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          status VARCHAR(50) DEFAULT 'Підтверджено',
                          user_id INTEGER NOT NULL REFERENCES Users(user_id) ON DELETE CASCADE,
                          schedule_id INTEGER NOT NULL REFERENCES Schedule(schedule_id) ON DELETE CASCADE,
                          UNIQUE (user_id, schedule_id) -- Користувач не може записатись двічі на те саме заняття
);