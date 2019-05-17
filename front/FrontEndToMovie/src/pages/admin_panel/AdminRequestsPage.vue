<template>
    <div>
        <Header/>
        <div class="content-container">
            <div class="content" v-if="userRole === 'ADMIN'">
                <admin-menu></admin-menu>
                <br><br>
                <div class="requests-container" :style="{height : requests.length * 320 + 'px'}">
                    <div v-for="request in requests" :key="request.id">
                        <div class="request-poster-container">
                            <img class="request-poster" :src=request.poster>
                        </div>

                        <div class="request-content">
                            <div class="request-status">
                                <div v-if="request.state.status === 'NEWLY'">Новый</div>
                                <div v-else-if="request.state.status === 'MODIFIED'">Измененный</div>
                            </div>
                            <div class="admin-buttons" v-show="showAdminButtons">
                                <base-button class="reject-button" type="danger" size="sm"
                                             @click="showCommentField = request.id; showAdminButtons = false">Отклонить
                                </base-button>
                                <base-button class="publish-button" type="success" size="sm"
                                             @click="publishFilm(request.id)">Добавить
                                </base-button>
                            </div>
                            <div><b>Название </b> {{request.title}}</div>
                            <div><b>Год: </b> {{request.year}}</div>
                            <div><b>Страна: </b> {{request.country}}</div>
                            <div><b>Дата выхода: </b> {{request.release}}</div>
                            <div><b>Жанр: </b> {{request.genre}}</div>
                            <div>
                                <div><b>Режиссер: </b>
                                    <span class="film-directors"
                                          v-for="(director, index) in request.directors"
                                          :key="director.id"
                                    >
                                                <span v-if="index !== request.directors.length - 1"><a
                                                        :href="/director/ + director.id">{{director.name}}</a>,&nbsp;</span>
                                                <span v-else><a
                                                        :href="/director/ + director.id">{{director.name}}</a></span>
                                        </span>
                                </div>
                            </div>
                            <div><b>Актеры: </b> {{request.actors}}</div>
                            <div><b>Описание: </b>{{request.description}}</div>
                        </div>

                        <div class="comment-field" v-show="showCommentField === request.id">
                            <label>
                                Комментарий<br>
                                <textarea v-model="comment" style="width: 815px; height: 70px"></textarea>
                            </label>
                            <div class="comment-buttons">
                                <base-button class="send-button" type="success" size="sm"
                                             @click="rejectFilm(request.id)">Отправить
                                </base-button>
                                <base-button class="cancel-button" type="danger" size="sm" @click="cancelComment">
                                    Отменить
                                </base-button>
                            </div>
                        </div>
                    </div>
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
    import Notification from '../../components/notification/Notification'
    import axios from "axios"
    import AdminMenu from "../../components/admin_panel/AdminMenu";

    export default {
        components: {
            AdminMenu,
            Notification
        },
        data() {
            return {
                userRole: '',
                requests: [],
                showCommentField: '',
                showAdminButtons: true,
                comment: '',
                showFilmFormToAdd: false,
                showNotification: false,
                notificationMessage: ''
            }
        },
        mounted() {
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                'accept': '*/*',
                Authorization: this.authorization,
            };

            document.title = "Запросы";
            axios.get(this.url + "/api/account").then((response) => {
                this.userRole = response.data.permissionsLevel;
            });
            this.getRequests();
        },
        methods: {
            getRequests() {
                axios.get(this.url + "/api/account/admin/requests").then((response) => {
                    let tmp = [];
                    response.data.forEach(function (request) {
                        if (request.state.status !== "REJECTED") {
                            request.year = request.year.substring(0, 4);
                            request.release = request.release.substring(0, 10);
                            tmp.push(request);
                        }
                    });
                    this.requests = tmp;
                });
            },

            publishFilm(id) {
                console.log(id);
                axios.put(this.url + "/api/films/publish/" + id).then(() => {
                    this.getRequests();
                    this.pushNotification("Фильм добавлен");
                })
            },

            rejectFilm(id) {
                axios.put(this.url + "/api/films/reject/" + id + "?comment=" + this.comment).then(() => {
                    this.comment = '';
                    this.showAdminButtons = true;
                    this.getRequests();
                    this.pushNotification("Запрос отклонен");
                })
            },

            cancelComment() {
                this.showCommentField = '';
                this.comment = '';
                this.showAdminButtons = true;
            },

            pushNotification(message) {
                this.notificationMessage = message;
                this.showNotification = true;
                setTimeout(() => {
                    this.showNotification = false;
                }, 5000);
            }
        }
    };
</script>

<style>
    .admin-menu {
        margin-right: 10px;
        float: left;
    }

    .admin-menu a {
        font-size: 20px;
        text-decoration: underline;
        color: #0E0E0E;
    }

    .comment-field {
        position: relative;
        width: 854px;
        margin-left: 190px;
        margin-top: 10px;
        background-color: white;
        border-radius: 5px;
        height: 142px;
        float: left;
        padding-left: 20px;
        padding-top: 10px;
    }

    .cancel-button {
        float: right;
        background-color: red;
    }

    .send-button {
        float: right;
        border-bottom-right-radius: 5px;
        background-color: green;
        margin-left: 5px;
    }

    .publish-button {
        margin-left: -3px;
    }

    .comment-buttons {
        float: right;
        margin-right: -9px;
    }

    .admin-buttons {
        right: 5px;
        margin-top: 237px;
        position: absolute;
    }
</style>