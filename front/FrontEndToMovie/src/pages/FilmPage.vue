<template>
    <div>
        <Header></Header>
        <div class="content-container">
            <div class="content" v-if="film !== ''">
                <div></div>
                <div class="big-item-poster-container">
                    <img class="big-poster" :src=film.poster>
                </div>
                <div class="big-item-content">
                    <div>
                        <div>
                            <h3>{{film.title}}</h3>
                        </div>
                        <div class="wish-button" v-if="inWishList">
                            <base-button size="sm" type="warning" icon="ni ni-favourite-28" @click="deleteWish">Удалить из
                                избранного&nbsp;&nbsp;&nbsp;
                            </base-button>
                        </div>
                        <div class="wish-button" v-else>
                            <base-button size="sm" type="success" icon="ni ni-favourite-28"
                                         @click="addWish">В избранное
                            </base-button>
                        </div>

                        <div v-show="userPermissionsLevel === 'ADMIN'" class="edit-film-button">
                            <base-button size="sm" type="danger"
                                         @click="deleteFilm">Удалить
                            </base-button>
                        </div>
                        <div v-show="userPermissionsLevel === 'ADMIN' || userPermissionsLevel === 'EDITOR'"
                             class="edit-film-button">
                            <base-button size="sm" type="info"
                                         @click="showFilmFormToEdit = true">Редактировать
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
                        <div><b>Дата выхода: </b> {{changeDateFormat}}</div>
                        <div><b>Описание: </b>{{film.description}}</div>
                    </div>
                </div>
                <modal :show.sync="showFilmFormToEdit" body-classes="p-0" modal-classes="modal-dialog modal-sm"
                       class="film-form-background">
                    <card type="secondary" class="border-0 film-form-content">
                        <template>
                            <h4>Изменение фильма</h4>
                            <film-form :film="film" type="update" @film-edited = "checkFilmToEdited"></film-form>
                        </template>
                    </card>
                </modal>
            </div>
            <div v-else class="content">
                <h5>Фильм не найден</h5>
            </div>
        </div>
        <div class="notification">
           <notification :show-notification="showNotification"> {{notificationMessage}}</notification>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'
    import Modal from '../components/base_components/Modal'
    import FilmForm from '../components/item_card/FormToAddAndUpdateFilm'
    import Notification from '../components/notification/Notification'

    export default {
        name: "Film",
        components: {
            Modal,
            FilmForm,
            Notification
        },
        data() {
            return {
                film: {
                    id: '',
                    title: '',
                    country: '',
                    description: '',
                    poster: '',
                    genre: '',
                    year: '',
                    release: '',
                    actors: '',
                    directors: []
                },
                inWishList: false,
                userPermissionsLevel: '',
                showFilmFormToEdit: false,
                showNotification: false,
                notificationMessage: ''
            }
        },
        mounted() {
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };

            axios.get(this.url + "/api/account/").then((response) => {
                this.userPermissionsLevel = response.data.permissionsLevel;
            });

            axios.get(this.url + "/api/films/film/" + this.$route.params.id).then((response) => {
                this.film = response.data;
                document.title = this.film.title;
                this.film.year = this.film.year.substring(0, 4);
                this.film.release = this.film.release.substring(0, 10);

                //В дальнейшем замеить запросом на бэкенд
                let list = JSON.parse(localStorage.getItem("wishlist"));
                let checkInWishlist = false;
                list.forEach(function (film) {
                    if (film.id === response.data.id) {
                        checkInWishlist = true;
                    }
                });
                this.inWishList = checkInWishlist;
                //******************************************************
            });
        },

        computed: {
            changeDateFormat() {
                let tmp = this.film.release.split("-");
                return tmp[2] + "." + tmp[1] + "." + tmp[0];
            }
        },

        methods: {
            deleteWish() {
                axios.delete(this.url + "/api/account/wishlist", {
                    params: {
                        filmId: this.film.id
                    }
                }).then(() => {
                    this.inWishList = false;
                });
            },

            addWish() {
                axios.put(this.url + "/api/account/wishlist?filmId=" + this.film.id).then(() => {
                    this.inWishList = true;
                });
            },

            deleteFilm() {
                axios.delete(this.url + "/api/films/film/" + this.film.id).then(() => {
                    this.film = '';
                    this.pushNotification("Фильм удален");
                });
            },

            pushNotification(message) {
                this.notificationMessage = message;
                this.showNotification = true;
                    setTimeout(() => {
                        this.showNotification = false;
                    }, 5000);
            },

            checkFilmToEdited(data) {
                if (data === true) {
                    this.showFilmFormToEdit = false;
                    if(this.userPermissionsLevel === 'ADMIN') {
                        this.pushNotification("Фильм изменен");
                    } else if (this.userPermissionsLevel === 'EDITOR') {
                        this.pushNotification("Запрос на изменение фильма отправлен администратору")
                    }
                }
            }
        }
    }
</script>
<style>
    .edit-film-button {
        float: right;
        margin-left: 5px;
    }
</style>