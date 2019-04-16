<template>
    <div>
        <Header></Header>
        <div class="content">
            <div v-if="!wishListEmpty">
                <h4>Избранные фильмы:</h4>
                <div class="search-result">
                    <div v-for="film in films" :key="film.id">
                        <card class="film-card">
                            <div>
                            <div>
                                <img class="poster-sm" src="../../public/img/posters/Марсианин.jpg">
                            </div>
                            <div class="search-item-title">
                                <a :href="/film/ + film.id"><b>{{film.title}}</b></a>
                                <span class="delete-wish">
                                    <base-button size="sm" type="danger" icon="ni ni-favourite-28" @click="delWish(film.id)">Удалить&nbsp;&nbsp;&nbsp;</base-button>
                                </span>
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
            <div v-else>
                <h4>Список пуст</h4>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'
    export default {
        name: "WishList",
        data() {
            return {
                wishListEmpty: true,
                authorization: localStorage.getItem("Authorization"),
                films: [],
                pagination: {
                    default: 1
                }
            }
        },
        mounted() {
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };

            this.getWishList();
        },
        methods: {
            delWish(id) {
                axios.delete("http://localhost:8080/api/account/wishlist", {
                    params: {
                        filmId: id
                    }
                }).then(() => {
                    this.getWishList();
                })
            },

            getWishList() {
                axios.get("http://localhost:8080/api/account/wishlist").then((response) => {
                    this.films = response.data;
                    if (this.films.length !== 0) {
                        this.wishListEmpty = false;
                    }
                    this.films.forEach(function (film) {
                        film.year = film.year.substring(0, 4);
                        film.release = film.release.substring(0, 10);
                    });
                });
            }
        }
    }
</script>

<style scoped>
    .delete-wish {
        float: right;
    }
</style>