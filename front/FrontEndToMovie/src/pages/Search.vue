<template>
    <div>
        <Header></Header>
        <div class="content-container">
            <div class="content">
                <h3>Результаты поиска по запросу: <b>{{$route.params.query}}</b></h3>
                <div v-show="filmsEmpty && directorsEmpty">По запросу <b>{{$route.params.query}}</b> ничего не найдено
                </div>
                <div v-show="!filmsEmpty" :class=filmSearchClass>
                    <h4>Фильмы: <b>(Результатов: {{films.length}})</b></h4>
                    <div v-for="(film, index) in filmsPaginatedData" :key="film.id">
                        <div v-if="index < filmsPagination.size">
                            <div class="item-poster-container">
                                <a :href="/film/ + film.id">
                                    <img class="item-poster" :src=film.poster>
                                </a>
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
                                        <span class="film-directors" v-for="(director, index) in film.directors" :key="director.id">
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
                    <div v-if="films.length > filmsPagination.size && filmsPagination.size !== 9" class="films-show-all-button">
                        <base-button size="sm" type="primary" @click="showAllFilms">
                            Показать все
                        </base-button>
                    </div>
                    <div class="search pagination-container" v-show="filmsPagination.size > 3">
                        <base-pagination :page-count="Math.ceil(films.length / filmsPagination.size)"
                                         v-model="filmsPagination.pageNumber" align="center"></base-pagination>
                        <div class="show-less-button">
                            <base-button size="sm" type="primary" @click="showLessFilms">
                                Показать меньше
                            </base-button>
                        </div>
                    </div>
                </div>

                <div v-show="!this.directorsEmpty" :class=directorSearchClass>
                    <h4>Режиссеры: <b>(Результатов: {{directors.length}})</b></h4>
                    <div v-for="(director, index) in directorsPaginatedData" :key="director.id">
                        <div v-if="index < directorsPagination.size">
                            <div class="item-poster-container">
                                <a :href="/director/ + director.id">
                                    <img class="item-poster" src="../../public/img/posters/Марсианин.jpg">
                                </a>
                            </div>
                            <div class="item-content">
                                <div class="item-title">
                                    <a :href="/director/ + director.id"><b>{{director.name}}</b></a>
                                </div>
                                <br><br>
                                <div class="item-description">
                                    <div><b>Страна:</b> {{director.country}}</div>
                                    <div><b>Фильмы:</b></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div v-if="directors.length > directorsPagination.size && directorsPagination.size !== 9" class="films-show-all-button">
                        <base-button size="sm" type="primary" @click="showAllDirectors">
                            Показать все
                        </base-button>
                    </div>
                    <div class="search pagination-container" v-show="directorsPagination.size > 3">
                        <base-pagination :page-count="Math.ceil(directors.length / directorsPagination.size)"
                                         v-model="directorsPagination.pageNumber" align="center"></base-pagination>
                        <div class="show-less-button">
                            <base-button size="sm" type="primary" @click="showLessDirectors">
                                Показать меньше
                            </base-button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "SearchPage",
        data() {
            return {
                films: [],
                directors: [],
                authorization: localStorage.getItem("Authorization"),
                filmsEmpty: true,
                directorsEmpty: true,
                filmsPagination: {
                    pageNumber: 1,
                    size: 3,
                },
                directorsPagination: {
                    pageNumber: 1,
                    size: 3,
                },
                filmSearchClass: 'film-search-less',
                directorSearchClass: 'director-search-less'
            }
        },
        mounted() {
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };

            axios.get(this.url + "/api/films", {
                params: {
                    title: this.$route.params.query,
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

            axios.get(this.url + "/api/directors", {
                params: {
                    name: this.$route.params.query,
                }
            }).then((response) => {
                this.directors = response.data;
                if (this.directors.length !== 0) {
                    this.directorsEmpty = false;
                }
            });
        },
        computed: {
            filmsPaginatedData() {
                const start = (this.filmsPagination.pageNumber - 1) * this.filmsPagination.size,
                    end = start + this.filmsPagination.size;
                return this.films.slice(start, end);
            },

            directorsPaginatedData() {
                const start = (this.directorsPagination.pageNumber - 1) * this.directorsPagination.size,
                    end = start + this.directorsPagination.size;
                return this.directors.slice(start, end);
            }
        },
        methods: {
            showAllFilms() {
                this.filmsPagination.size = 9;
                this.filmSearchClass = "film-search-all";
            },
            showLessFilms() {
                this.filmsPagination.size = 3;
                this.filmsPagination.pageNumber = 1;
                this.filmSearchClass = "film-search-less";
            },

            showAllDirectors() {
                this.directorsPagination.size = 9;
                this.directorSearchClass = "director-search-all";
            },
            showLessDirectors() {
                this.directorsPagination.size = 3;
                this.directorsPagination.pageNumber = 1;
                this.directorSearchClass = "director-search-less";
            }
        }
    }
</script>

<style scoped>
    .film-search-all {
        height: 680px;
    }
    .film-search-less {
        height: 270px;
    }
    .director-search-all{
        height: 750px;
    }
    .director-search-less{
        height: 350px;
    }

    .films-show-all-button {
        margin-top: 10px;
        margin-right: 10px;
        float: right;
    }
    .show-less-button {
        float: right;
        margin-top: -45px;
        margin-right: 10px;
    }
    h4 b {
        font-size: 18px;
    }
</style>