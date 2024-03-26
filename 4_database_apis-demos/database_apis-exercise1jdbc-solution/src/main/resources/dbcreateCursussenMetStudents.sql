DROP TABLE IF EXISTS cursus;
CREATE TABLE cursus(
    id INT NOT NULL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    ects INT NOT NULL,
);

INSERT INTO student(id, name, ects) VALUES (1, 'dab', 4);

DROP TABLE IF EXISTS inschrijvingen;
CREATE TABLE inschrijvingen (
	id	INTEGER NOT NULL UNIQUE,
	cursus	INTEGER,
	student	INTEGER,
	FOREIGN KEY("cursus") REFERENCES "cursus"("id"),
	FOREIGN KEY("student") REFERENCES "student"("studnr"),
	PRIMARY KEY("id" AUTOINCREMENT)
);

INSERT INTO inschrijvingen(cursus, student) VALUES (1,456);