INSERT INTO users (type, birthday, email, name, surname, privileged_type, username, password)
VALUES ('PRIVILEGED_USER', '1999-10-29', 'petarns99@yahoo.com', 'Petar', 'Markovic', 'MANAGER', 'markuza99',
        '$2a$04$61poZhk/GGJk.oqrZSJSB.GN.eMJbq71kiGbqF.2EO26/z80MhzcG'),
       ('PRIVILEGED_USER', '1999-10-29', 'petarns999@yahoo.com', 'Petar', 'Markovic', 'OWNER', 'markuza999',
        'petar123');

INSERT INTO menu (id)
VALUES ('420');
INSERT INTO article (name, description, type, menu_id, image)
VALUES ('Dobos tortaaa', 'Jako fina', 'DESSERT', 420, ''),
       ('Jagnjetina', 'Vruca', 'MAIN_COURSE', 420, ''),
       ('Mesano meso', '1kg mesa sa rostilja', 'MAIN_COURSE', 420, ''),
       ('Meze', 'Taze meze', 'APPETIZER', 420, ''),
       ('Pohovana piletina', 'Jako fina', 'MAIN_COURSE', 420, ''),
       ('Krempita', 'Jako fina', 'DESSERT', 420, ''),
       ('Cezar salata', 'Jako fina', 'MAIN_COURSE', 420, ''),
       ('Espreso', 'Kafa', 'DRINK', 420, ''),
       ('Coca cola', 'sokic', 'DRINK', 420, ''),
       ('Princes krofna', 'Ubica', 'DESSERT', 420, '');


INSERT INTO users (type, birthday, email, name, surname, employee_type, pincode)
VALUES ('EMPLOYEE', '1999-8-21 ', 'mateja99@yahoo.com', 'Mateja', 'Cosovic', 'WAITER', 1234),
       ('EMPLOYEE', '1999-3-05 ', 'suljak99@yahoo.com', 'Marko', 'Suljak', 'BARMAN', 4322),
       ('EMPLOYEE', '1999-11-25 ', 'cepic@yahoo.com', 'Aleksandar', 'Cepic', 'BARMAN', 5321),
       ('EMPLOYEE', '1999-11-25 ', 'perica@yahoo.com', 'Petar', 'Petrovic', 'COOK', 4269),
       ('EMPLOYEE', '1999-11-25 ', 'maric@yahoo.com', 'Marko', 'Maric', 'COOK', 2910);

INSERT INTO salaries (from_date, to_date, status, value, user_id)
VALUES ('2003-5-6', '2003-5-6', 'ACTIVE', 23000, 3),
       ('2003-5-6', '2003-5-6', 'ACTIVE', 23000, 4),
       ('2003-5-6', '2003-5-6', 'ACTIVE', 23000, 5),
       ('2003-5-6', '2003-5-6', 'ACTIVE', 23000, 6),
       ('2003-5-6', '2003-5-6', 'ACTIVE', 23000, 7),
       ('2003-5-6', '2003-5-6', 'ACTIVE', 23000, 2),
       ('2003-5-6', '2003-5-6', 'ACTIVE', 23000, 1);

INSERT INTO orders (description, order_date, order_status, table_number, employee_id, price) VALUES
  ('No mustard', '2021-1-3 12:43:33', 0, 1, 3, 1989),
  ('No ketchup', '2021-1-13 12:43:33',0, 1, 3, 1389),
  ('No ketchup', '2021-5-3 12:43:33',0, 1, 3, 1421),
  ('No ketchup', '2021-6-3 12:43:33',0, 1, 3, 5040),
  ('No ketchup', '2021-7-3 12:43:33',0, 1, 3, 3040),
  ('No ketchup', '2021-9-3 12:43:33',0, 1, 3, 2040),
  ('No ketchup', '2021-11-3 12:43:33',0, 1, 3, 1501),
  ('No ketchup', '2021-11-3 12:43:33',0, 1, 3, 10011),
  ('No ketchup', '2021-11-3 12:43:33',0, 1, 3, 10032),
  ('No ketchup', '2021-11-3 12:43:33',0, 1, 3, 10022),
  ('No ketchup', '2021-11-3 12:43:33',0, 1, 3, 1003),
  ('No ketchup', '2021-11-3 12:43:33',0, 1, 3, 1004),
  ('Extra mayo', '2021-12-3 15:21:00',0, 3, 3, 1005);

INSERT INTO ordered_article (description, status, order_id, article_id, employee_id)
VALUES ('bez buta', 'TAKEN', 1, 2, 6),
       ('bez cevapa', 'NOT_TAKEN', 1, 3, 7),
       ('bez sira', 'FINISHED', 1, 4, 6),
       ('duza sa mlekom', 'NOT_TAKEN', 2, 8, 4),
       ('zero', 'NOT_TAKEN', 2, 9, 5),
       ('bez buta', 'FINISHED', 2, 2, 7),
       ('bez kobasica', 'TAKEN', 3, 3, 6),
       ('bez sira', 'FINISHED', 4, 4, 7),
       ('kraca bez mleka', 'PREPARING', 5, 8, 4),
       (NULL, 'TAKEN', 6, 9, 4),
       ('duza bez', 'FINISHED', 6, 8, 5),
       (NULL, 'FINISHED', 7, 9, 5);

INSERT INTO ordered_article (description, status, order_id, article_id)
VALUES ('bez buta', 'NOT_TAKEN', 1, 2),
       (NULL, 'NOT_TAKEN', 7, 1);


INSERT INTO ingredient (name, is_allergen)
VALUES ('Vanila', FALSE),
       ('Cokolada', TRUE),
       ('Badem', TRUE),
       ('Puter', TRUE),
       ('Testo', TRUE),
       ('Prsuta', TRUE),
       ('Luk', TRUE),
       ('Slanina', TRUE);


INSERT INTO article_ingredient (article_id, ingredient_id)
VALUES (1, 1),
       (1, 2),
       (2, 1);

INSERT INTO prices (from_date, making_price, selling_price, status, to_date, article_id)
VALUES ('2003-5-6', 200, 350, 'ACTIVE', NULL, 1),
       ('2002-11-23', 100, 200, 'EXPIRED', '2003-5-6', 1),
       ('2002-11-23', 800, 1800, 'ACTIVE', NULL, 2),
       ('2002-11-23', 600, 1500, 'ACTIVE', NULL, 3),
       ('2002-11-23', 700, 1600, 'ACTIVE', NULL, 4),
       ('2002-11-23', 400, 800, 'ACTIVE', NULL, 5),
       ('2002-11-23', 100, 250, 'ACTIVE', NULL, 6),
       ('2002-11-23', 200, 500, 'ACTIVE', NULL, 7),
       ('2002-11-23', 30, 150, 'ACTIVE', NULL, 8),
       ('2002-11-23', 60, 150, 'ACTIVE', NULL, 9),
       ('2002-11-23', 100, 200, 'ACTIVE', NULL, 10);

INSERT INTO employee_login_info (type, employee_type, pincode)
values ('WAITER', 'ROLE_WAITER', '1111'),
       ('BARMAN', 'ROLE_BARMAN', '2222'),
       ('COOK', 'ROLE_COOK', '3333');

INSERT INTO roles (name)
values ('ROLE_MANAGER'),
       ('ROLE_OWNER'),
       ('ROLE_WAITER'),
       ('ROLE_BARMAN'),
       ('ROLE_COOK');

INSERT INTO user_role (username, role_id)
values ('markuza99', 1),
       ('markuza999', 2),
       ('WAITER', 3),
       ('BARMAN', 4),
       ('COOK', 5);
