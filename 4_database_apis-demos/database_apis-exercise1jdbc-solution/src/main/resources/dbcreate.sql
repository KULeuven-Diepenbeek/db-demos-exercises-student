DROP TABLE IF EXISTS student;
CREATE TABLE student(
    studnr INT NOT NULL PRIMARY KEY,
    naam VARCHAR(200) NOT NULL,
    voornaam VARCHAR(200),
    goedbezig BOOL
);
DROP TABLE IF EXISTS log;
CREATE TABLE log(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    foreign_id INT NOT NULL,
    msg TEXT
);
INSERT INTO student(studnr, naam, voornaam, goedbezig) VALUES (123, 'Trekhaak', 'Jaak', 0);
INSERT INTO student(studnr, naam, voornaam, goedbezig) VALUES (456, 'Peeters', 'Jos', 0);
INSERT INTO student(studnr, naam, voornaam, goedbezig) VALUES (890, 'Dongmans', 'Ding', 1);

DROP TABLE IF EXISTS cursus;
CREATE TABLE cursus(
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    ects INT NOT NULL
);

INSERT INTO cursus(id, name, ects) VALUES (1, 'dab', 4);

DROP TABLE IF EXISTS inschrijvingen;
CREATE TABLE inschrijvingen (
	id	INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT,
	cursus	INTEGER,
	student	INTEGER,
    FOREIGN KEY("cursus") REFERENCES "cursus"("id"),
    FOREIGN KEY("student") REFERENCES "student"("studnr")
);

INSERT INTO inschrijvingen(cursus, student) VALUES (1,456);