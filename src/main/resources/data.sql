INSERT INTO users (userid, username, password, fname, lname, photopath)

VALUES (1000000001, 'mredekopp', 'password', 'mark', 'redekopp', 'C:\\users');
INSERT INTO users (userid, username, password, fname, lname, photopath)
VALUES (1000000002, 'ttrojan', 'password', 'tommy', 'trojan', 'C:\\users');
INSERT INTO users (userid, username, password, fname, lname, photopath)
VALUES (1000000003, 'zx99', 'password', 'billy', 'bruin', 'C:\\users');

INSERT INTO booking (bookingid, userid, reccenterid, timeslot, iswaitlist)
VALUES (1, 1000000001, 1, '1997-12-31 10:00:00', false);
INSERT INTO booking (bookingid, userid, reccenterid, timeslot, iswaitlist)
VALUES (2, 1000000002, 1, '1997-12-31 10:00:00', true);
INSERT INTO booking (bookingid, userid, reccenterid, timeslot, iswaitlist)
VALUES (3, 1000000002, 1, '1998-01-01 10:00:00', false);
INSERT INTO booking (bookingid, userid, reccenterid, timeslot, iswaitlist)
VALUES (4, 1000000002, 2, '1998-01-02 10:00:00', false);

INSERT INTO vacancy (vacancyid, reccenterid, timeslot, numvacant)
VALUES (1, 1, '1997-12-31 10:00:00', 0);
INSERT INTO vacancy (vacancyid, reccenterid, timeslot, numvacant)
VALUES (2, 1, '1998-01-01 10:00:00', 1);
INSERT INTO vacancy (vacancyid, reccenterid, timeslot, numvacant)
VALUES (3, 2, '1998-01-02 10:00:00', 3);