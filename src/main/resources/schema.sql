DROP TABLE IF EXISTS film, users, mpa, genres, film_genre, likes, friend, friend_status;

CREATE TABLE IF NOT EXISTS mpa (
  mpa_id integer PRIMARY KEY,
  name varchar
);

CREATE TABLE IF NOT EXISTS film (
  film_id integer GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  name varchar NOT NULL,
  description varchar NOT NULL,
  releaseDate date NOT NULL,
  duration integer NOT NULL,
  mpa_id integer NOT NULL REFERENCES mpa(mpa_id)
);

CREATE TABLE IF NOT EXISTS users(
  user_id integer GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
  email varchar NOT NULL,
  login varchar NOT NULL,
  name varchar,
  birthday date NOT NULL
);

CREATE TABLE IF NOT EXISTS genres (
  genre_id integer PRIMARY KEY,
  name varchar
);

CREATE TABLE IF NOT EXISTS film_genre (
  film_id integer REFERENCES film (film_id) ON DELETE CASCADE,
  genre_id integer REFERENCES genres (genre_id),
  PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS likes (
  film_id integer REFERENCES film (film_id) ON DELETE CASCADE,
  user_id integer REFERENCES users (user_id) ON DELETE CASCADE,
  PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS friend (
  user_id integer REFERENCES users (user_id) ON DELETE CASCADE,
  friend_id integer REFERENCES users (user_id) ON DELETE CASCADE,
  PRIMARY KEY (user_id, friend_id)
);

