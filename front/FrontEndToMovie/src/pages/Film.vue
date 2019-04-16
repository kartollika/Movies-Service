<template>
    <div>
        <Header></Header>
        <div class="content">
            <div>
                <card class="film-card">
                <div>
                <span>
                    <h3>{{film.title}}</h3>
                </span>
                    <span class="wish-button" v-if="wishList">
                        <base-button size="sm" type="danger" icon="ni ni-favourite-28" @click="delWish">Удалить&nbsp;&nbsp;&nbsp;</base-button>
                    </span>
                    <span class="wish-button" v-else>
                        <base-button size="sm" type="success" icon="ni ni-favourite-28" @click="addWish">В избранное</base-button>
                    </span>
                </div>
              <img class="poster-lg" src="../../public/img/posters/Марсианин.jpg">
                <div class="description">
                    <div class="description-item"><b>Год: </b> {{film.year}}</div>
                    <hr>
                    <div class="description-item"><b>Страна: </b> {{film.country}}</div>
                    <hr>
                    <div class="description-item"><b>Жанр: </b> {{film.genre}}</div>
                    <hr>
                    <div>
                        <div class="description-item"><b>Режиссер: </b>
                            <span class="film-directors" v-for="(director, index) in film.directors" :key="director.id">
                                <span v-if="index !== film.directors.length - 1"><a :href="/director/ + director.id">{{director.name}}</a>, </span>
                                <span v-else><a :href="/director/ + director.id">{{director.name}}</a></span>
                            </span>
                        </div>
                    </div>
                    <hr>
                    <div class="description-item"><b>Дата выхода: </b> {{film.release}}</div>
                    <hr>
                    <div class="description-item"><b>Бюджет: </b> {{film.budget}}</div>
                    <hr>
                </div>
                </card>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "Film",
        data() {
            return {
                film: '',
                authorization: localStorage.getItem("Authorization"),
                wishList: ''
            }
        },
        mounted() {

            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };
            axios.get("http://localhost:8080/api/films/film/" + this.$route.params.id).then((response) => {
                this.film = response.data;
                this.film.year = this.film.year.substring(0, 4);
                this.film.release = this.film.release.substring(0, 10);

                let list = JSON.parse(localStorage.getItem("wishlist"));
                let check = false;
                list.forEach(function (film) {
                    if (film.id === response.data.id) {
                        check = true;
                    }
                });

                this.wishList = check;
            });
        },
        methods: {
            delWish() {
                axios.delete("http://localhost:8080/api/account/wishlist", {
                    params: {
                        filmId: this.film.id
                    }
                }).then(() => {
                    this.wishList = false;
                });
            },

            addWish() {
                axios.put("http://localhost:8080/api/account/wishlist?filmId=" + this.film.id).then(() => {
                    this.wishList = true;
                });
            }
        }
    }
</script>

<style>
    .description {
        margin: 20px 10px;
        font-size: 15px;
    }

    .wish-button {
        float: right;
        margin-top: -35px;
    }

    .film-directors a {
        text-decoration: underline;
        color: #32325d;
    }

</style>