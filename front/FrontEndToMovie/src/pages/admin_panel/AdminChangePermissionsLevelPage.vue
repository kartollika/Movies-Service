<template>
    <div>
        <Header></Header>
        <div class="content-container">
            <div class="content">
                <div>
                    <span class="admin-menu"><a href="/admin/requests">Запросы на изменения</a></span>
                    <span class="admin-menu"><a href="/admin/change">Измение роли пользователя</a></span>
                </div>
                <br><br>
                <h4>Изменение роли пользователя</h4>
                <form @submit.prevent="searchUser">
                    <span><label>Поиск пользователя: &nbsp;&nbsp;</label><input type='text' v-model="searchUsername"
                                                                                style="width: 300px; margin-top: 2px"></span>
                    <span><base-button nativeType="submit" type="info" size="sm">Найти</base-button></span><br>
                </form>
                <div class="response" v-show="showSearchResult">
                    <span><h5>Результат поиска:</h5></span>
                    <div v-if = !isFound>Пользователь <b> {{foundUsername}} </b> не найден</div>
                    <div v-show="user !== ''">
                        <div class="found-user-content">
                            <div>Пользователь <b>{{user.username}}</b></div>
                            <img class="found-user-icon" src="../../assets/icons/common/ikonka.png">
                            <div class="found-user-info">
                                <div><b>Имя:</b> {{user.name}} <b>&nbsp;&nbsp;&nbsp;Роль: </b> {{user.permissionsLevel}}</div>
                            </div>
                            <div class="change-permission-button">
                                <base-button type="success" size="sm">Изменение роли</base-button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "change-permissions-level",
        data() {
            return {
                user: '',
                searchUsername: '',
                foundUsername: '',
                isFound: false,
                showSearchResult: false
            }
        },
        mounted() {
            document.title = "Изменение роли пользователя";
        },

        methods: {
            searchUser() {
                this.info = '';
                this.user = '';
                axios.defaults.headers = {
                    'Content-Type': 'application/json',
                    Authorization: this.authorization
                };

                axios.get(this.url + "/api/account/" + this.searchUsername).then((response) => {
                    this.showSearchResult = true;
                    this.foundUsername = this.searchUsername;
                    if (response.status === 204) {
                        this.isFound = false;
                    } else {
                        this.isFound = true;
                        this.user = response.data;
                        this.searchUsername = '';
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .response {
        position: fixed;
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
</style>