<template>
    <div>
        <div class="item-poster-container">
            <a :href="/film/ + nextFilm.id"><img class="item-poster" :src=nextFilm.poster></a>
        </div>
        <div class="item-content">
            <div class="wishlist-icon">
                <i class="fa fa-star-o" aria-hidden="true" v-if="!inWishList" @click="addWish"></i>
                <i class="fa fa-star" aria-hidden="true" v-else @click="deleteWish"></i>
            </div>
            <div class="item-title">
                <a :href="/film/ + nextFilm.id"><b>{{nextFilm.title}}</b></a>
            </div>
            <br><br>
            <div class="item-description">
                <div><b>Год:</b> {{nextFilm.year}}</div>
                <div><b>Страна:</b> {{nextFilm.country}}</div>
                <div><b>Жанр:</b> {{nextFilm.genre}}</div>
                <div><b>Режиссер: </b>
                    <span class="film-directors" v-for="(director, index) in nextFilm.directors" :key="director.id">
                        <span v-if="index !== nextFilm.directors.length - 1">
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
</template>

<script>
    import axios from 'axios'

    export default {
        name: "FilmContainer",
        props: ['nextFilm'],
        data() {
            return {
                inWishList: false
            }
        },

        mounted() {
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };
            axios.get(this.url + "/api/account/wishlist/contains" , {
                params: {
                    filmId: this.nextFilm.id
                }
            }).then((response) => {
                this.inWishList = response.data
            });
        },

        methods: {
            deleteWish() {
                axios.delete(this.url + "/api/account/wishlist", {
                    params: {
                        filmId: this.nextFilm.id
                    }
                }).then(() => {
                    this.inWishList = false;
                    this.$emit('updateWishlist', true);
                });
            },

            addWish() {
                axios.put(this.url + "/api/account/wishlist?filmId=" + this.nextFilm.id).then(() => {
                    this.inWishList = true;
                });
            },
        }
    }
</script>

<style scoped>
    .wishlist-icon {
        float: right;
        font-size: 18px;
        color: #E378CA;
    }
    .wishlist-icon:hover {
        cursor: pointer;
    }
</style>