<template>
    <div>
        <Header></Header>
        <div class="content-container">
            <div class="content">
                <div v-if="!(films.length === 0)">
                    <h4>Фильмы жанра <b>{{genre}}</b>:</h4>
                    <h5>Найдено фильмов: <b>{{films.length}}</b></h5>
                    <div v-for="film in paginatedData" :key="film.id">
                       <film :next-film = film></film>
                    </div>
                    <div class="pagination-container">
                        <base-pagination :page-count="Math.ceil(films.length / pagination.size)"
                                         v-model="pagination.pageNumber" align="center"></base-pagination>
                    </div>
                </div>
                <div v-else>
                    <h4>Не найдено ни одного фильма жанра: <b>{{genre}}</b></h4>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'
    import Film from '../components/item_card/FilmCard'
    export default {
        name: "Genre",
        components: {
            Film
        },
        data() {
            return {
                films: [],
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
                this.films.forEach(function (film) {
                    film.year = film.year.substring(0, 4);
                    film.release = film.release.substring(0, 10);
                });
            });

            document.title = this.genre;
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