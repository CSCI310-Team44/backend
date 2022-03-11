CREATE TABLE users (
    userid BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    fname VARCHAR NOT NULL,
    lname VARCHAR NOT NULL,
    photopath VARCHAR
);
CREATE TABLE booking (
    bookingid BIGINT PRIMARY KEY AUTO_INCREMENT,
    userid BIGINT NOT NULL,
    reccenterid INTEGER NOT NULL,
    timeslot TIMESTAMP NOT NULL,
    iswaitlist BOOLEAN NOT NULL,
    -- foreign key constraint
    CONSTRAINT fk_booking_users FOREIGN KEY (userid) REFERENCES users(userid)
);
CREATE TABLE vacancy (
    vacancyid BIGINT PRIMARY KEY AUTO_INCREMENT,
    reccenterid INTEGER NOT NULL,
    timeslot TIMESTAMP NOT NULL,
    numvacant INTEGER NOT NULL
);