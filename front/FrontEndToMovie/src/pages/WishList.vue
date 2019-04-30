<template>
    <div>
        <Header></Header>
        <div class="content-container">
            <div class="content">
                <div v-if="!(films.length === 0)">
                    <h4>Избранные фильмы:</h4>
                    <div v-for="film in paginatedData" :key="film.id">
                        <film :next-film = film></film>
                    </div>
                    <div class="pagination-container">
                        <base-pagination :page-count="Math.ceil(films.length / pagination.size)" v-model="pagination.pageNumber" align="center"></base-pagination>
                    </div>
                </div>
                <div v-else>
                    <h4>Список пуст</h4>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'
    import Film from '../components/item_card/FilmCard'
    export default {
        name: "WishList",
        components: {
            Film
        },

        data() {
            return {
                films: [],
                pagination: {
                    pageNumber: 1,
                    size: 9
                }
            }
        },

        mounted() {
            document.title = "Избранное";
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };

            this.getWishList();
        },

        methods: {
            delWish(id) {
                axios.delete(this.url + "/api/account/wishlist", {
                    params: {
                        filmId: id
                    }
                }).then(() => {
                    this.getWishList();
                })
            },

            getWishList() {
                axios.get(this.url + "/api/account/wishlist").then((response) => {
                    this.films = response.data;
                    this.films.forEach(function (film) {
                        film.year = film.year.substring(0, 4);
                        film.release = film.release.substring(0, 10);
                    });
                });
            },

            nextPage() {
                this.pagination.pageNumber++;
            },

            prevPage() {
                this.pagination.pageNumber--;
            }
        },

        computed: {
            paginatedData(){
                const start = (this.pagination.pageNumber - 1) * this.pagination.size,
                    end = start + this.pagination.size;
                return this.films.slice(start, end);
            }
        }
    }
</script>

<style scoped>
</style>