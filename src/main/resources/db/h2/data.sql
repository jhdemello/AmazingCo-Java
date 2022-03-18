/*********************************************************************************/
/* Spring Boot Pet Clinic Employees (N-Ary Tree)                                 */
/**/

/* CEO */
INSERT INTO employees VALUES (1, 'Slighty McCheaty', 'Slighty McCheaty', '');

/* Vice-Presidents */
INSERT INTO employees VALUES (2, 'Ragnar', 'Slighty McCheaty', '');
INSERT INTO employees VALUES (3, 'Dagny', 'Slighty McCheaty', '');
INSERT INTO employees VALUES (4, 'Francisco', 'Slighty McCheaty', '');

/* Rangar */
INSERT INTO employees VALUES (5, 'Hank', 'Ragnar', '');
INSERT INTO employees VALUES (6, 'Eddie', 'Ragnar', '');
INSERT INTO employees VALUES (7, 'Ken', 'Ragnar', '');

/* Ragnar.Hank */
INSERT INTO employees VALUES (8, 'Hugo', 'Hank', '');
INSERT INTO employees VALUES (9, 'Austen', 'Hank', '');
INSERT INTO employees VALUES (10, 'Tolstoy', 'Hank', '');

/* Ragnar.Hank.Tolstoy */
INSERT INTO employees VALUES (11, 'Penn', 'Tolstoy', '');
INSERT INTO employees VALUES (12, 'Aurelius', 'Tolstoy', '');
INSERT INTO employees VALUES (13, 'Brown', 'Tolstoy', '');
INSERT INTO employees VALUES (14, 'Milton', 'Tolstoy', '');

/* Ragnar.Hank.Tolstoy.Aurelius */
INSERT INTO employees VALUES (15, 'Venus', 'Aurelius', '');
INSERT INTO employees VALUES (16, 'Mars', 'Aurelius', '');
INSERT INTO employees VALUES (17, 'Jupiter', 'Aurelius', '');
INSERT INTO employees VALUES (18, 'Saturn', 'Aurelius', '');

/* Dagny */
INSERT INTO employees VALUES (19, 'James', 'Dagny', '');

/* Dagny.James */
INSERT INTO employees VALUES (20, 'Axl', 'James', '');
INSERT INTO employees VALUES (21, 'Dizzy', 'James', '');
INSERT INTO employees VALUES (22, 'Slash', 'James', '');

/* Francisco */
INSERT INTO employees VALUES (23, 'Lillie', 'Francisco', '');
INSERT INTO employees VALUES (24, 'Robert', 'Francisco', '');
INSERT INTO employees VALUES (25, 'Cherryl', 'Francisco', '');

/* Francisco.Lillie */
INSERT INTO employees VALUES (26, 'Darwin', 'Lillie', '');
INSERT INTO employees VALUES (27, 'Plutarch', 'Lillie', '');
INSERT INTO employees VALUES (28, 'Virgil', 'Lillie', '');

/*********************************************************************************/
/* Spring Boot Pet Clinic (Vets, Owners, & Pets)                                 */
/**/

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

INSERT INTO pets VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits VALUES (4, 7, '2013-01-04', 'spayed');
