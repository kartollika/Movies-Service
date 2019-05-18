delete from "movies-service".public.films_directors;
delete from "movies-service".public.wishlist;
delete from "movies-service".public.history;
delete from "movies-service".public.accounts;
INSERT INTO "movies-service".public.accounts(id, name, username, password, permissions_level)
VALUES (1, 'admin', 'admin', '$2a$04$tLO7a4X9vW51sGbm/27ciOFhDrL8K/9zGfuZqOrVrUOlwsL1Q2uPO', 'ADMIN');
DELETE from "movies-service".public.films where id=2;
INSERT INTO "movies-service".public.films(id, title, genre, year, release, country, poster, description, actors, creation_date)
VALUES (2, 'Green Book', 'Comedy', '2018-09-11 00:00:00.000000', '2019-02-25 00:00:00.000000', 'USA', 'poster', 'description', 'Mahershala Ali', '2019-05-15 00:00:00.000000');
