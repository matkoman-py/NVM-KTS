INSERT INTO users (type, birthday, email, name, surname, privileged_type, username, password) VALUES
  ('PRIVILEGED_USER', '1999-10-29', 'petarns99@yahoo.com', 'Petar', 'Markovic', 'OWNER', 'markuza99', 'petar123'),
  ('PRIVILEGED_USER', '1999-10-29', 'petarns999@yahoo.com', 'Petar', 'Markovic', 'MANAGER', 'markuza999', 'petar123');

INSERT INTO article (name, description, type) VALUES
  ('Dobos torta', 'Jako fina', 'APPETIZER'),
  ('Princes krofna', 'Ubica', 'APPETIZER');
  
INSERT INTO ingredient (name, is_allergen) VALUES
  ('Vanila', FALSE),
  ('Cokolada', TRUE),
  ('Badem', TRUE);
  
INSERT INTO article_ingredient (article_id, ingredient_id) VALUES
  (1, 1),
  (1, 2),
  (2, 1);
  
INSERT INTO prices (from_date, making_price, selling_price, status, to_date, article_id) VALUES
  ('2003-5-6', 200, 350, 'ACTIVE', NULL, 1),
  ('2002-11-23', 100, 200, 'EXPIRED', '2003-5-6', 1),
  ('2002-11-23', 100, 200, 'ACTIVE', NULL, 2);