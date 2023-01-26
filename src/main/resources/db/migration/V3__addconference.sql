CREATE TABLE IF NOT EXISTS conference (
  id serial,
  title VARCHAR (100) NOT NULL,
  speaker VARCHAR(100) NOT NULL,
  hora VARCHAR(100),
  dia VARCHAR(100),
  total_attendees INT,
  event_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (event_id) REFERENCES event(id)
);
