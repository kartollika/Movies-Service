<template>
    <div>
        <Header></Header>
        <div class="content-container">
            <div class="content">
                <div class="big-item-poster-container">
                    <img class="big-poster" :src=film.poster>
                </div>
                <div class="big-item-content">
                    <div>
                        <div>
                            <h3>{{film.title}}</h3>
                        </div>
                        <div class="wish-button" v-if="wishList">
                            <base-button size="sm" type="danger" icon="ni ni-favourite-28" @click="delWish">Удалить&nbsp;&nbsp;&nbsp;</base-button>
                        </div>
                        <div class="edit-button" v-if="wishList">
                            <base-button size="sm" type="danger" icon="ni ni-favourite-28" @click="delWish">Удалить&nbsp;&nbsp;&nbsp;</base-button>
                        </div>
                        <div class="wish-button" v-else>
                            <base-button size="sm" type="success" icon="ni ni-favourite-28"
                                         @click="addWish">В избранное
                            </base-button>
                        </div>
                    </div>

                    <div class="big-item-description">
                        <div><b>Год: </b> {{film.year}}</div>
                        <div><b>Страна: </b> {{film.country}}</div>
                        <div><b>Жанр: </b> {{film.genre}}</div>
                        <div>
                            <div><b>Режиссер: </b>
                                <span class="film-directors" v-for="(director, index) in film.directors"
                                      :key="director.id">
                                <span v-if="index !== film.directors.length - 1"><a :href="/director/ + director.id">{{director.name}}</a>,
                                </span>
                                <span v-else><a :href="/director/ + director.id">{{director.name}}</a></span>
                            </span>
                            </div>
                        </div>
                        <div><b>Актеры: </b> {{film.actors}}</div>
                        <div><b>Дата выхода: </b> {{film.release}}</div>
                        <div><b>Описание: </b>{{film.description}}</div>
                    </div>
                </div>
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
                wishList: ''
            }
        },
        mounted() {
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };
            axios.get(this.url + "/api/films/film/" + this.$route.params.id).then((response) => {
                this.film = response.data;
                document.title = this.film.title;
                this.film.year = this.film.year.substring(0, 4);
                this.film.release = this.film.release.substring(0, 10);
                let tmp = this.film.release.split("-");
                this.film.release = tmp[2] + "." + tmp[1] + "." + tmp[0];

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
                axios.delete(this.url + "/api/account/wishlist", {
                    params: {
                        filmId: this.film.id
                    }
                }).then(() => {
                    this.wishList = false;
                });
            },

            addWish() {
                axios.put(this.url + "/api/account/wishlist?filmId=" + this.film.id).then(() => {
                    this.wishList = true;
                });
            }
        }
    }
</script>
<style>
</style>