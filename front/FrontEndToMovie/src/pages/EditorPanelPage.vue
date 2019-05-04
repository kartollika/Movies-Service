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
                <div class="requests-container">
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
                                   request.genre, request.year, request.release, request.actors)">Изменить
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
                                <film-form :film=film type="update"></film-form>
                            </template>
                        </card>
                    </modal>
                    <modal :show.sync="showFilmFormToAdd" body-classes="p-0" modal-classes="modal-dialog modal-sm"
                           class="film-form-background">
                        <card type="secondary" class="border-0 film-form-content">
                            <template>
                                <h4>Добавление фильма</h4>
                                <film-form></film-form>
                            </template>
                        </card>
                    </modal>
                </div>
            </div>
            <div v-else class="content">
                <h4>У вас нет прав для доступа к этой странице</h4>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
    import Modal from '../components/base_components/Modal'
    import FilmForm from '../components/item_card/FormToAddAndUpdateFilm'

    export default {
        name: "EditorPage",
        components: {
            FilmForm,
            Modal
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
                showFilmFormToAdd: false
            }
        },
        mounted() {
            document.title = "Панель модератора";
            axios.get(this.url + "/api/account").then((response) => {
                this.user = response.data;
                this.getRequests(this.user);
            });
        },

        methods: {
            getRequests(user) {
                axios.get(this.url + "/api/films/requests").then((response) => {
                    let tmp = [];
                    let url = this.url;
                    response.data.forEach(function (request) {
                        axios.get(url + "/api/account/id/" + request.state.ownerId).then((response) => {
                            if (response.data.username === user.username) {
                                request.year = request.year.substring(0, 4);
                                request.release = request.release.substring(0, 10);
                                tmp.push(request);
                            }
                        });
                    });
                    this.requests = tmp;
                });
            },

            clearFields() {
                this.film.poster = '';
                this.film.title = '';
                this.film.country = '';
                this.film.genre = '';
                this.film.release = '';
                this.film.year = '';
                this.film.description = '';
            },

            publishFilm(id) {
                axios.put(this.url + "/api/films/publish/" + id,).then(() => {
                    this.clearFields();
                    window.location.reload();
                })
            },

            editFilm(id, title, country, description, poster, genre, year, release, actors) {
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