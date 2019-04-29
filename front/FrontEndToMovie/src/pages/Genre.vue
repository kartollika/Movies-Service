<template>
    <div>
        <Header></Header>
        <div class="content-container">
            <div class="content">
                <div v-if="!this.filmsEmpty">
                    <h4>Фильмы жанра <b>{{genre}}</b>:</h4>
                    <h5>Найдено фильмов: <b>{{films.length}}</b></h5>
                    <div v-for="film in paginatedData" :key="film.id">
                        <div>
                            <div class="item-poster-container">
                                <img class="item-poster" :src=film.poster>
                            </div>
                            <div class="item-content">
                                <div class="item-title">
                                    <a :href="/film/ + film.id"><b>{{film.title}}</b></a>
                                </div>
                                <br><br>
                                <div class="item-description">
                                    <div><b>Год:</b> {{film.year}}</div>
                                    <div><b>Страна:</b> {{film.country}}</div>
                                    <div><b>Жанр:</b> {{film.genre}}</div>
                                    <div><b>Режиссер: </b>
                                        <span class="film-directors" v-for="(director, index) in film.directors"
                                              :key="director.id">
                                            <span v-if="index !== film.directors.length - 1">
                                                <a :href="/director/ + director.id">{{director.name}}</a>,
                                            </span>
                                            <span v-else>
                                                <a :href="/director/ + director.id">{{director.name}}</a>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="pagination-container">
                        <base-pagination :page-count="Math.ceil(films.length / pagination.size)"
                                         v-model="pagination.pageNumber" align="center"></base-pagination>
                    </div>
                </div>
                <div v-else>
                    <h4>Не найдено ни одного фильма жанра <b>{{genre}}</b></h4>
                </div>
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
                genre: '',
                pagination: {
                    pageNumber: 1,
                    size: 9
                },
            }
        },
        mounted() {
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };

            this.genre = this.$route.params.name[0].toUpperCase() + this.$route.params.name.slice(1);
            axios.get(this.url + "/api/films", {
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
        },

        computed: {
            paginatedData() {
                const start = (this.pagination.pageNumber - 1) * this.pagination.size,
                    end = start + this.pagination.size;
                return this.films.slice(start, end);
            }
        },

        methods: {
            nextPage() {
                this.pagination.pageNumber++;
            },
            prevPage() {
                this.pagination.pageNumber--;
            }
        }
    }
</script>

<style>
</style>