<template>
    <div>
        <Header></Header>
        <div class="content-container">
            <div class="content" v-if="userPermissionsLevel === 'ADMIN'">
                <admin-menu></admin-menu>
                <br><br>
                <h4>Изменение роли пользователя</h4>
                <form @submit.prevent="searchUser">
                    <span><label>Поиск пользователя: &nbsp;&nbsp;</label><input type='text' v-model="searchUsername"
                                                                                style="width: 300px; margin-top: 2px"></span>
                    <span><base-button nativeType="submit" type="info" size="sm">Найти</base-button></span><br>
                </form>
                <div class="response" v-if="showSearchResult">
                    <span><h5>Результат поиска:</h5></span>
                    <div v-if=!isFound>Пользователь <b> {{foundUsername}} </b> не найден</div>
                    <div v-show="foundUser !== ''">
                        <div class="found-user-content">
                            <div>Пользователь <b>{{foundUser.username}}</b></div>
                            <img class="found-user-icon" src="../../assets/icons/common/ikonka.png">
                            <div class="found-user-info">
                                <div><b>Имя:</b> {{foundUser.name}} <b>&nbsp;&nbsp;&nbsp;Роль: </b> {{foundUser.permissionsLevel}}
                                </div>
                            </div>
                            <div class="change-permission-button" v-if="!isChange">
                                <base-button type="success" size="sm" @click="isChange = true">Изменение роли
                                </base-button>
                            </div>
                            <div v-else class="change-permission-content">
                                <label>
                                    Выберети новую роль:
                                    <select v-model="newPermissionsLevel">
                                        <option>EDITOR</option>
                                        <option>ADMIN</option>
                                        <option>USER</option>
                                    </select>
                                </label>
                                <base-button class="change-button" type="success" size="sm"
                                             @click="changePermissionsLevel">Изменить
                                </base-button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="content" v-else>
                <h4>У вас нет прав для доступа к данной странице</h4>
            </div>
        </div>
        <div class="notification">
            <notification :show-notification="showNotification"> {{notificationMessage}}</notification>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'
    import Notification from '../../components/notification/Notification'
    import AdminMenu from '../../components/admin_panel/AdminMenu'

    export default {
        name: "change-permissions-level",
        components: {
            Notification,
            AdminMenu
        },
        data() {
            return {
                userPermissionsLevel: '',
                foundUser: '',
                searchUsername: '',
                foundUsername: '',
                isFound: false,
                showSearchResult: false,
                isChange: false,
                newPermissionsLevel: '',
                showNotification: false,
                notificationMessage: ''
            }
        },
        mounted() {
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };
            document.title = "Изменение роли пользователя";
            axios.get(this.url + "/api/account/").then((response) => {
                this.userPermissionsLevel = response.data.permissionsLevel;
            });
        },

        methods: {
            searchUser() {
                this.info = '';
                this.foundUser = '';

                axios.get(this.url + "/api/account/" + this.searchUsername).then((response) => {
                    this.showSearchResult = true;
                    this.foundUsername = this.searchUsername;
                    if (response.status === 204) {
                        this.isFound = false;
                    } else {
                        this.isFound = true;
                        this.foundUser = response.data;
                        this.searchUsername = '';
                    }
                });
            },

            changePermissionsLevel() {
                axios.put(this.url + "/api/account/role/" + this.foundUser.username + "?newRole=" + this.newPermissionsLevel).then(() => {
                    this.pushNotification("Роль изменена");
                    this.foundUser.permissionsLevel = this.newPermissionsLevel;
                });
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
    .response {
        position: absolute;
        float: bottom;
        width: 400px;
        margin-top: 50px;
    }

    .found-user-icon {
        width: 50px;
        border-radius: 20px;
        float: left;
    }

    .found-user-info {
        margin-left: 60px;
        margin-top: 10px;
    }

    .found-user-content {
        background-color: white;
        border-radius: 5px;
        height: 130px;
        padding: 15px;
    }

    .change-permission-button {
        float: right;
        margin-right: -15px;
        margin-top: 29px;
    }

    .change-permission-content {
        float: right;
        margin-right: -15px;
        margin-top: 7px;
    }

    .change-button {
        margin-left: 20px;
        margin-top: 6px;
    }
</style>