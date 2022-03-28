INSERT INTO users (userid, username, password, fname, lname, photopath)
VALUES (1, '001122334455', 'password', 'mark', 'redekopp', 'C:\\users');
INSERT INTO users (userid, username, password, fname, lname, photopath)
VALUES (2, '1234567890', 'password', 'tommy', 'trojan', 'C:\\users');
INSERT INTO users (userid, username, password, fname, lname, photopath)
VALUES (3, '3333344444', 'password', 'billy', 'bruin', 'C:\\users');

INSERT INTO booking (bookingid, userid, reccenterid, timeslot, iswaitlist)
VALUES (1, 1, 1, '1997-12-31 10:00:00', false);
INSERT INTO booking (bookingid, userid, reccenterid, timeslot, iswaitlist)
VALUES (2, 2, 1, '1997-12-31 10:00:00', true);
INSERT INTO booking (bookingid, userid, reccenterid, timeslot, iswaitlist)
VALUES (3, 2, 1, '1998-01-01 10:00:00', false);
INSERT INTO booking (bookingid, userid, reccenterid, timeslot, iswaitlist)
VALUES (4, 2, 2, '1998-01-02 10:00:00', false);

INSERT INTO vacancy (vacancyid, reccenterid, timeslot, numvacant)
VALUES (1, 1, '1997-12-31 10:00:00', 0);
INSERT INTO vacancy (vacancyid, reccenterid, timeslot, numvacant)
VALUES (2, 1, '1998-01-01 10:00:00', 1);
INSERT INTO vacancy (vacancyid, reccenterid, timeslot, numvacant)
VALUES (3, 2, '1998-01-02 10:00:00', 3);