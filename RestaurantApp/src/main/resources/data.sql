INSERT INTO users (type, birthday, email, name, surname, privileged_type, username, password) VALUES
  ('PRIVILEGED_USER', '1999-10-29', 'petarns99@yahoo.com', 'Petar', 'Markovic', 'OWNER', 'markuza99', 'petar123');
  
INSERT INTO users (type, birthday, email, name, surname, employee_type, pincode) VALUES
  ('EMPLOYEE', '1999-8-21 ', 'mateja99@yahoo.com', 'Mateja', 'Cosovic', 'WAITER', 1234),
  ('EMPLOYEE', '1999-3-05 ', 'suljak99@yahoo.com', 'Marko', 'Suljak', 'KITCHEN_WORKER', 4322),
  ('EMPLOYEE', '1999-11-25 ', 'cepic@yahoo.com', 'Aleksandar', 'Cepic', 'KITCHEN_WORKER', 5321);
  
INSERT INTO orders (description, order_date, table_number, employee_id) VALUES
  ('No ketchup', '2021-11-3 12:43:33', 1, 2),
  ('Extra mayo', '2021-12-3 15:21:00', 3, 2);