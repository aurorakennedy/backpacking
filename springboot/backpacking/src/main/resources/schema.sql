--created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

CREATE TABLE User (
  email VARCHAR(50) PRIMARY KEY,
  password VARCHAR(20) NOT NULL,
  username VARCHAR(20) NOT NULL,
  CONSTRAINT password_length CHECK (LENGTH(password) >= 8),
  CONSTRAINT email_format CHECK (email LIKE '%@%.%')
)



CREATE TABLE Moderator (
  email VARCHAR(50) PRIMARY KEY,
  CONSTRAINT fk_moderator_user_email 
    FOREIGN KEY (email) REFERENCES User(email) 
    ON DELETE CASCADE
    ON UPDATE CASCADE

);


CREATE TABLE Itinerary (
  id INTEGER PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  writer_email VARCHAR(50) NOT NULL,
  written_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  estimated_time INTEGER DEFAULT null,
  itinerary_description VARCHAR(255),
  image VARCHAR(255)
  price DECIMAL(10,2) DEFAULT null,
  CONSTRAINT fk_itinerary_user_email 
    FOREIGN KEY (writer_email) REFERENCES User(email) 
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

CREATE TABLE Destinations (
destination_name VARCHAR(50) NOT NULL,
country VARCHAR(50) NOT NULL,
destination_description VARCHAR(255),
PRIMARY KEY (destination_name, country),
CONSTRAINT fk_destinations_countries_country_name 
    FOREIGN KEY (country) REFERENCES countries(country_name) 
    ON DELETE CASCADE
    ON UPDATE CASCADE

);

CREATE TABLE Rating (
  user_email VARCHAR(50) NOT NULL ,
  itinerary_id INT NOT NULL,
  rating INT NOT NULL,
  rating_comment VARCHAR(255) DEFAULT null,
  PRIMARY KEY (user_email, itinerary_id),
  CONSTRAINT fk_rating_user_email 
    FOREIGN KEY (user_email) REFERENCES User(email) 
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_rating_itinerary_id 
    FOREIGN KEY (itinerary_id) REFERENCES Itinerary(id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE
  
);

CREATE TABLE Liked_Itineraries (
  user_email VARCHAR(50) NOT NULL,
  itinerary_id INT NOT NULL,
  PRIMARY KEY (user_email, itinerary_id)
  CONSTRAINT fk_liked_itineraries_user_email 
    FOREIGN KEY (user_email) REFERENCES User(email) 
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_liked_itineraries_itinerary_id
    FOREIGN KEY (itinerary_id) REFERENCES Itinerary(id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE
  
);

CREATE TABLE Continents (
  continent_name VARCHAR(30) NOT NULL PRIMARY KEY
);

CREATE TABLE Countries (
  country_name VARCHAR(50) NOT NULL PRIMARY KEY,
  continent VARCHAR(30) NOT NULL
  CONSTRAINT fk_countries_continents_continent_name 
    FOREIGN KEY (continent) REFERENCES continents(continent_name) 
    ON DELETE CASCADE
    ON UPDATE CASCADE
);




CREATE TABLE Itinerary_Destination (
  itinerary_id INT NOT NULL,
  order_number INT NOT NULL,
  destination_name VARCHAR(50) NOT NULL,
  country VARCHAR(50) NOT NULL,
  PRIMARY KEY (itinerary_id, order_number),
  CONSTRAINT fk_itinerary_destination_itinerary_id 
    FOREIGN KEY (itinerary_id) REFERENCES Itinerary(id) 
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_itinerary_destination_destination_name
    FOREIGN KEY (destination_name) REFERENCES Destinations(destination_name) 
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_itinerary_destination_country
    FOREIGN KEY (country) REFERENCES Destinations(country) 
    ON DELETE CASCADE
    ON UPDATE CASCADE
  

);

CREATE TABLE advertiser (
  email VARCHAR(50) NOT NULL PRIMARY KEY,
  password VARCHAR(50) NOT NULL,
  company_name VARCHAR(50) NOT NULL
);

CREATE TABLE ads (
  id INTEGER PRIMARY KEY,
  advertiser_email VARCHAR(50) NOT NULL,
  ad_image VARCHAR(255) NOT NULL,
  link VARCHAR(255) NOT NULL,
  CONSTRAINT fk_ads_advertiser_email 
    FOREIGN KEY (advertiser_email) REFERENCES advertiser(email) 
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT email_format CHECK (advertiser_email LIKE '%@%.%')

);


INSERT INTO User (email, password, username)
VALUES ('mod1@backpacking.com', 'password', 'moderator1');

INSERT INTO Moderator (email)
VALUES ('mod1@backpacking.com');

