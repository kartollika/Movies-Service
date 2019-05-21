DELETE from "movies-service".public.films_directors;
DELETE from "movies-service".public.wishlist;
DELETE from "movies-service".public.history;
DELETE from "movies-service".public.accounts;
DELETE from "movies-service".public.directors;
DELETE from "movies-service".public.films;

INSERT INTO "movies-service".public.accounts(id, name, username, password, permissions_level)
VALUES (1, 'admin', 'admin', '$2a$04$tLO7a4X9vW51sGbm/27ciOFhDrL8K/9zGfuZqOrVrUOlwsL1Q2uPO', 'ADMIN');

INSERT INTO "movies-service".public.films(id, title, genre, year, release, country, poster, description, actors, creation_date)
VALUES (2, 'Green Book', 'Comedy', '2018-09-11 00:00:00.000000', '2019-02-25 00:00:00.000000', 'USA', 'poster', 'description', 'Mahershala Ali', '2019-05-15 00:00:00.000000');

INSERT INTO "movies-service".public.directors(id, name, country)
VALUES (1, 'Peter Farrelly', 'USA');

INSERT INTO "movies-service".public.films_directors(film_id, director_id)
VALUES (2, 1);
