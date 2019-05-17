<template>
    <div>
        <Header></Header>
        <div class="content-container">
            <div class="content" v-if="user.permissionsLevel === 'EDITOR'">
                <div>
                    <span class="admin-menu"><a href="/editor/requests">Мои запросы</a></span>
                    <span class="admin-menu"><a href="#" @click="showFilmFormToAdd = true">Добавление фильма</a></span>
                </div>
                <div></div>
                <br><br>
                <div class="requests-container" :style="{height : requests.length * 300 + 'px'}">
                    <div v-show="requests.length === 0"><h5>Список пуст</h5></div>
                    <div v-for="request in requests" :key="request.id">
                        <div class="request-poster-container">
                            <img class="request-poster" :src=request.poster>
                        </div>
                        <div class="request-content">
                            <div v-show="request.state.status === 'REJECTED'" class="editors-buttons">
                                <base-button class="resend-film-button" type="success" size="sm"
                                             @click="publishFilm(request.id)">Отправить повторно
                                </base-button>
                                <base-button class="change-film-button" size="sm" type="success"
                                             @click="editFilm(request.id, request.title, request.country, request.description, request.poster,
                                   request.genre, request.year, request.release, request.actors, request.directors)">Изменить
                                </base-button>
                            </div>
                            <div class="request-status">
                                <div v-if="request.state.status === 'REJECTED'">Отклонен</div>
                                <div v-else-if="request.state.status === 'NEWLY' || request.state.status === 'MODIFIED'">
                                    Ожидает рассмотрения
                                </div>
                            </div>
                            <div><b>Название </b> {{request.title}}</div>
                            <div>
                                <b>Год: </b> {{request.year}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <b>Страна: </b> {{request.country}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <b>Жанр: </b> {{request.genre}}
                            </div>
                            <div><b>Дата выхода: </b> {{request.release}}</div>
                            <div>
                                <div><b>Режиссер: </b>
                                    <span class="film-directors" v-for="(director, index) in request.directors"
                                          :key="director.id">
                                <span v-if="index !== request.directors.length - 1"><a :href="/director/ + director.id">{{director.name}}</a>,
                                </span>
                                <span v-else><a :href="/director/ + director.id">{{director.name}}</a></span>
                            </span>
                                </div>
                            </div>
                            <div><b>Актеры: </b> {{request.actors}}</div>
                            <div><b>Описание: </b>{{request.description}}</div>
                            <div v-show="request.state.status === 'REJECTED'">
                                <hr>
                                <b>Комментарий: </b>
                                {{request.state.comment}}
                            </div>
                        </div>
                    </div>
                    <modal :show.sync="showFilmFormToEdit" body-classes="p-0" modal-classes="modal-dialog modal-sm"
                           class="film-form-background">
                        <card type="secondary" class="border-0 film-form-content">
                            <template>
                                <h4>Редактирование фильма</h4>
                                <film-form :film=film type="update" :permissions-level="'EDITOR'"
                                           @film-edited="checkRequestToEdit"></film-form>
                            </template>
                        </card>
                    </modal>
                    <modal :show.sync="showFilmFormToAdd" body-classes="p-0" modal-classes="modal-dialog modal-sm"
                           class="film-form-background">
                        <card type="secondary" class="border-0 film-form-content">
                            <template>
                                <h4>Добавление фильма</h4>
                                <film-form :permissions-level="'EDITOR'"></film-form>
                            </template>
                        </card>
                    </modal>
                </div>
            </div>
            <div v-else class="content">
                <h4>У вас нет прав для доступа к этой странице</h4>
            </div>
        </div>
        <div class="notification">
            <notification :show-notification="showNotification"> {{notificationMessage}}</notification>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import Modal from '../components/base_components/Modal'
    import FilmForm from '../components/item_card/FormToAddAndUpdateFilm'
    import Notification from '../components/notification/Notification'

    export default {
        name: "EditorPage",
        components: {
            FilmForm,
            Modal,
            Notification
        },
        data() {
            return {
                user: '',
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
                },
                requests: [],
                showFilmFormToEdit: false,
                showFilmFormToAdd: false,
                showNotification: false,
                notificationMessage: ''
            }
        },
        mounted() {
            document.title = "Панель модератора";
            axios.get(this.url + "/api/account").then((response) => {
                this.user = response.data;
                this.getRequests();
            });
        },

        methods: {
            getRequests() {
                axios.get(this.url + "/api/account/requests").then((response) => {
                    let tmp = [];
                    response.data.forEach(function (request) {
                        request.year = request.year.substring(0, 4);
                        request.release = request.release.substring(0, 10);
                        tmp.push(request);
                    });
                    this.requests = tmp;
                });
            },

            publishFilm(id) {
                axios.put(this.url + "/api/films/publish/" + id).then(() => {
                    this.getRequests();
                    this.pushNotification("Запрос повторно отправлен администратору");
                })
            },

            editFilm(id, title, country, description, poster, genre, year, release, actors, directors) {
                this.showFilmFormToEdit = true;
                this.film.id = id;
                this.film.poster = poster;
                this.film.title = title;
                this.film.country = country;
                this.film.genre = genre;
                this.film.release = release;
                this.film.year = year;
                this.film.description = description;
                this.film.actors = actors;
                this.film.directors = directors;
            },

            checkRequestToEdit(data) {
                if (data === true) {
                    this.showFilmFormToEdit = false;
                    this.getRequests();
                    this.pushNotification("Запрос изменен и повторно отправлен администратору");
                }
            },

            pushNotification(message) {
                this.notificationMessage = message;
                this.showNotification = true;
                setTimeout(() => {
                    this.showNotification = false;
                }, 5000);
            }
        }

    }
</script>

<style scoped>
    .editors-buttons {
        position: absolute;
        right: -3px;
        margin-top: 225px;
    }

    hr {
        margin-top: 5px;
        margin-bottom: 5px;
    }

    .resend-film-button {
        float: right;
        margin-top: 12px;
        margin-left: 5px;
    }

    .change-film-button {
        float: right;
        margin-top: 12px;
    }
</style>