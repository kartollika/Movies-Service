delete from "movies-service".public.films_directors;
delete from "movies-service".public.wishlist;
delete from "movies-service".public.history;
delete from "movies-service".public.accounts;
INSERT INTO "movies-service".public.accounts(id, name, username, password, permissions_level)
VALUES (1, 'admin', 'admin', '$2a$04$tLO7a4X9vW51sGbm/27ciOFhDrL8K/9zGfuZqOrVrUOlwsL1Q2uPO', 'ADMIN');
DELETE from "movies-service".public.films;

