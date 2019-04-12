<template>
    <div>
        <Header></Header>
        <div class="content">
            <div v-if="!this.filmsEmpty">
                <h4>Фильмы жанра <b>{{genre}}</b>:</h4>
                <div class="search-result">
                    <div v-for="film in films">
                        <img class="poster-sm" src="../../public/img/posters/Марсианин.jpg">
                        <div class="search-item-title"><a :href="/film/ + film.id"><b>{{film.title}}</b></a></div>
                        <div class="search-description">
                            <div class="description-item"><b>Год:</b> {{film.year}}</div>
                            <div class="description-item"><b>Страна:</b> {{film.country}}</div>
                            <div class="description-item"><b>Жанр:</b> {{film.genre}}</div>
                            <div class="description-item"><b>Режиссер:</b></div>
                        </div>
                        <hr>
                    </div>
                </div>
            </div>
            <div v-else>
                <h4>Не найдено ни одного фильма жанра <b>{{genre}}</b></h4>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "Genre",
        data() {
            return {
                films: [],
                authorization: localStorage.getItem("Authorization"),
                filmsEmpty: true,
                genre: ''
            }
        },
        mounted() {
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };

            this.genre = this.$route.params.name[0].toUpperCase() + this.$route.params.name.slice(1);
            axios.get("http://localhost:8080/api/films", {
                params: {
                    genre: this.$route.params.name,
                }
            }).then((response) => {
                this.films = response.data;
                if (this.films.length !== 0) {
                    this.filmsEmpty = false;
                }
                this.films.forEach(function (film) {
                    film.year = film.year.substring(0, 4);
                    film.release = film.release.substring(0, 10);
                });
            });
        }
    }
</script>

<style>
</style>