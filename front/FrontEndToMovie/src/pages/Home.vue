<template>
    <div>
        <Header/>
        <div class="content-container">
            <div class="content" v-if="authorization !== null">
                <h3 class="home-h3">Новинки</h3>
                <Swiper v-bind:films="swiperFilms"></Swiper>
                <h3 class="home-h3">Популярное</h3>
                <div v-for="film in films" :key="film.id">
                   <film :next-film = film></film>
                </div>
            </div>
            <div v-else class="content">
                <un-authorized-error></un-authorized-error>
            </div>
        </div>
    </div>
</template>

<script>
    import Film from '../components/item_card/FilmCard'
    import Swiper from '../components/swiper/Swiper.vue'
    import axios from 'axios'

    export default {
        components: {
            Swiper,
            Film
        },
        data() {
            return {
                content: 'content',
                films: [],
                swiperFilms: []
            }
        },
        mounted() {
            document.title = "Главная";

            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };
            axios.get(this.url + "/api/films").then((response) => {
                this.films = response.data;
                this.films.forEach(function (film) {
                    film.year = film.year.substring(0, 4);
                    film.release = film.release.substring(0, 10);
                });

                this.swiperFilms = this.films.slice(0, 18);
                this.films = this.films.slice(18, 27);
            });
        }
    }
</script>

<style>
    .home-h3 {
        margin-left: 30px;
    }
</style>