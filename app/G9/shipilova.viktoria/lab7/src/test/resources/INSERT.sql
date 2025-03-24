INSERT INTO NextDestination (nextdestinationid, nextdestinationname, description)
VALUES (1, 'Auction', 'No description'),
       (2, 'Police', 'No description'),
       (3, 'City dump', 'No description');

INSERT INTO PropertyType (propertytypeid, propertytypename, nextdestinationid, storagedays, storagecost)
VALUES (1, 'ELECTRONICS', 1, 30, 100),
       (2, 'DOCUMENTS', 2, 7, 50),
       (3, 'JEWELRY', 1, 30, 100),
       (4, 'BANK_CARD', 2, 7, 50),
       (5, 'CLOTHES', 1, 30, 100),
       (6, 'ACCESSORIES', 1, 30, 100),
       (7, 'KEYS', 3, 30, 50),
       (8, 'BOOKS', 1, 30, 100),
       (9, 'MONEY', 2, 1, 0),
       (10, 'MEDICAL_SUPPLIES', 3, 30, 50),
       (11, 'BAGS', 2, 7, 200),
       (12, 'SMALL_ITEMS', 1, 30, 100),
       (13, 'BABY_ITEMS', 1, 30, 50),
       (14, 'SPORTING_GOODS', 1, 30, 100),
       (15, 'OFFICE_SUPPLIES', 1, 30, 100),
       (16, 'COSMETICS', 3, 30, 100),
       (17, 'HYGIENE_PRODUCTS', 3, 30, 100),
       (18, 'OTHER', 1, 14, 100);


INSERT INTO Users (Password, PhoneNumber, Email, Login)
VALUES ('111', '+72345678966', 'user1@gmail.com', 'User1'),
       ('222', '+78765432123', 'user2@gmail.com', 'User2'),
       ('333', '+79765432123', 'user2@gmail.com', 'User3');


INSERT INTO FoundProperties (PropertyTypeID, DateOfFinding, TimeOfFinding, ReturnStatus, PlaceOfFinding, Description, UserID)
VALUES (1, CURRENT_DATE, CURRENT_TIME, 'NOT_RETURNED', 'Park', 'Phone', 1),
       (2, CURRENT_DATE, CURRENT_TIME, 'NOT_RETURNED', 'Library', 'Passport', 2);
