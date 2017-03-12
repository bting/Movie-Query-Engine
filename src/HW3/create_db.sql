-- CREATE TABLE movies
CREATE TABLE movies (
  Mid                     NUMBER NOT NULL,
  Title                   VARCHAR(300),
  imdbID                  VARCHAR(10),
  snapishTitle            VARCHAR(128),
  imdbPictureURL          VARCHAR(2083),
  mYear                   INTEGER,
  rtID                    VARCHAR(128),
  AllCriticrating         NUMBER,
  AllCriticNoReview       INTEGER,
  rtAllCriticsNumFresh    NUMBER,
  rtAllCriticsNumRotten   NUMBER,
  rtAllCriticsScore       NUMBER,
  TopCriticrating         NUMBER,
  TopCriticNoReview       INTEGER,
  rtTopCriticsNumFresh    NUMBER,
  rtTopCriticsNumRotten   NUMBER,
  rtTopCriticsScore       NUMBER,
  Audiencerating          NUMBER,
  AudienceNorating        INTEGER,
  rtAudienceScore         NUMBER,
  rtPictureURL            VARCHAR(2083),
  PRIMARY KEY (Mid)
);
-- CREATE TABLE movie_genres
CREATE TABLE movie_genres (
  Mid    NUMBER,
  Genres VARCHAR(100),
  FOREIGN KEY (Mid) REFERENCES movies (Mid)
);

-- CREATE TABLE movie_directors
CREATE TABLE movie_directors (
  Mid           NUMBER       NOT NULL,
  DirectorId    VARCHAR(100) NOT NULL,
  DirectorName  VARCHAR(100),
  PRIMARY KEY (Mid, DirectorId),
  FOREIGN KEY (Mid) REFERENCES movies (Mid)
);

-- CREATE TABLE movie_actors
CREATE TABLE movie_actors (
  Mid        NUMBER NOT NULL,
  ActorId    VARCHAR(100),
  ActorName  VARCHAR(100),
  PRIMARY KEY (Mid, ActorId),
  FOREIGN KEY (Mid) REFERENCES movies (Mid)
);

-- CREATE TABLE movie_countries
CREATE TABLE movie_countries(
  Mid     NUMBER,
  Country VARCHAR(30),
  PRIMARY KEY (Mid),
  FOREIGN KEY (Mid) REFERENCES movies (Mid)
);

-- CREATE TABLE tags
CREATE TABLE tags (
  Tid     NUMBER,
  TagText VARCHAR(100),
  PRIMARY KEY (Tid)
);

-- CREATE TABLE movie_tags
CREATE TABLE movie_tags (
  Mid NUMBER,
  Tid NUMBER,
  tagWeight NUMBER,
  FOREIGN KEY (Mid) REFERENCES movies (Mid),
  FOREIGN KEY (Tid) REFERENCES tags (Tid)
);


-- CREATE TABLE ratedMovies
CREATE TABLE ratedMovies (
  userId       NUMBER,
  Mid       NUMBER,
  Rating    NUMBER,
  rateTime TIMESTAMP,
  PRIMARY KEY (userId, Mid, Rating, rateTime),
  FOREIGN KEY (Mid) REFERENCES movies (Mid)
);

CREATE TABLE movie_locations (
  Mid      NUMBER,
  location1   VARCHAR(100),
  location2   VARCHAR(300),
  location3   VARCHAR(300),
  location4   VARCHAR(300),
  FOREIGN KEY (Mid) REFERENCES movies(Mid)
);

CREATE TABLE taggedMovies (
  userId  NUMBER,
  Mid     NUMBER,
  Tid     NUMBER,
  tagTime TIMESTAMP,
  PRIMARY KEY(userId, Mid, Tid)
);

-- CREATE TABLE INDEXES
CREATE INDEX actor_index ON movie_actors (ActorName);

CREATE INDEX country_index ON movie_countries (Country);

CREATE INDEX director_index ON movie_directors (DirectorName);

CREATE INDEX genre_index ON movie_genres (Genres);

CREATE INDEX mYear_index ON movies (mYear);

CREATE INDEX rating_index ON ratedMovies (userId, Rating, rateTime);

CREATE INDEX movieTag_index ON movie_tags (Mid, Tid);

CREATE INDEX tag_index ON tags (Tid);

CREATE INDEX movie_location_index ON movie_locations (Location1);


CREATE INDEX taggedMovies_index ON taggedMovies (userId, Mid, Tid);