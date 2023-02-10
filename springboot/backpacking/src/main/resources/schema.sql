CREATE TABLE User (
  email VARCHAR(50) PRIMARY KEY,
  password VARCHAR(20) NOT NULL,
  username VARCHAR(20) NOT NULL
);

CREATE TABLE Moderator (
  email VARCHAR(50) PRIMARY KEY,
  FOREIGN KEY (email) REFERENCES user(email)
);

CREATE TABLE Itinerary (
  id SERIAL PRIMARY KEY,
  writer_email VARCHAR(50) NOT NULL REFERENCES user(email),
  written_date DATE NOT NULL,
  estimated_time TIME,
  itinerary_description VARCHAR(255),
  rating INT DEFAULT 0,
  image BYTEA  --binary data
);

CREATE TABLE Rating (
  user_email VARCHAR(50) NOT NULL REFERENCES user(email),
  itinerary_id INT NOT NULL REFERENCES Itinerary(id),
  PRIMARY KEY (user_email, itinerary_id),
  rating INT NOT NULL,
  rating_comment VARCHAR(255)
);

CREATE TABLE SavedJourneys (
  user_email VARCHAR(50) NOT NULL REFERENCES user(email),
  itinerary_id INT NOT NULL REFERENCES Itinerary(id),
  PRIMARY KEY (user_email, itinerary_id)
);

CREATE TABLE Continents (
  continent_name VARCHAR(30) NOT NULL PRIMARY KEY
);

CREATE TABLE Countries (
  country_name VARCHAR(50) NOT NULL PRIMARY KEY,
  continent VARCHAR(30) NOT NULL REFERENCES continents(continent_name)
);

CREATE TABLE Destinations (
destination_name VARCHAR(50) NOT NULL PRIMARY KEY,
country VARCHAR(50) NOT NULL REFERENCES countries(country_name),
destination_description VARCHAR(255)
);

CREATE TABLE Stretch (
  id SERIAL PRIMARY KEY,
  start_location VARCHAR(50) NOT NULL REFERENCES destinations(destination_name),
  end_location VARCHAR(50) NOT NULL REFERENCES destinations(destination_name),
  duration TIME,
  distance NUMERIC
);

CREATE TABLE Itinerary_Stretches (
  id SERIAL PRIMARY KEY,
  itinerary_id INT NOT NULL REFERENCES Itinerary(id),
  stretch_id INT NOT NULL REFERENCES Stretch(id),
  stretch_order INT NOT NULL
);

CREATE TABLE advertiser (
  email VARCHAR(50) NOT NULL PRIMARY KEY,
  company_name VARCHAR(50) NOT NULL
);

CREATE TABLE ads (
  id SERIAL PRIMARY KEY,
  advertiser_email VARCHAR(50) NOT NULL REFERENCES advertiser(email),
  ad_image BYTEA,
  ad_start_date DATE,
  link VARCHAR(255) NOT NULL
);