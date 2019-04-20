<template>
    <div class="page">
        <Header></Header>
        <div class="content">
            <h3>Результаты поиска по запросу: <b>{{$route.params.query}}</b></h3>
            <div v-show="filmsEmpty && directorsEmpty">По запросу <b>{{$route.params.query}}</b> ничего не найдено</div>
            <div class="search-result">
                <div v-show="!this.filmsEmpty">
                    <h4>Фильмы:</h4>
                    <div>
                        <div v-for="(film, index) in films" :key="film.id">
                            <div v-if="index < size">
                                <card class="film-card">
                                <div>
                                    <div>
                                        <img class="poster-sm"
                                             src="../../public/img/posters/Марсианин.jpg">
                                    </div>
                                    <div class="search-item-title">
                                        <a :href="/film/ + film.id"><b>{{film.title}}</b></a>
                                    </div>
                                    <div class="search-description">
                                        <div class="description-item"><b>Год:</b> {{film.year}}</div>
                                        <div class="description-item"><b>Страна:</b> {{film.country}}</div>
                                        <div class="description-item"><b>Жанр:</b> {{film.genre}}</div>
                                        <div class="description-item"><b>Режиссер:</b></div>
                                    </div>
                                </div>
                                </card>
                            </div>
                        </div>
                    </div>
                    <div v-show="films.length > size">
                        <base-button style="float: right" size="sm" outline type="primary" @click="size=films.length">
                            Показать все
                        </base-button>
                    </div>
                </div>
                <div v-show="!this.directorsEmpty">
                    <h4>Режиссеры:</h4>
                    <div v-for="director in directors" :key="director.id">
                        <card class="film-card">
                        <div>
                            <div>
                                <img class="poster-sm" src="../../public/img/posters/Марсианин.jpg">
                            </div>
                            <div class="search-item-title">
                                <a :href="/director/ + director.id"><b>{{director.name}}</b></a>
                            </div>
                            <div class="search-description">
                                <div class="description-item"><b>Страна:</b> {{director.country}}</div>
                                <div class="description-item"><b>Фильмы:</b></div>
                            </div>
                        </div>
                        </card>
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
                size: 2
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
        }
    }
</script>

<style scoped>
</style>