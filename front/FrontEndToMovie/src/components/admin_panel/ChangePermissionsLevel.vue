<template>
    <div>
        <div>
            <div class="username-input">
                <div>
                    <form @submit.prevent="searchUser">
                        <span class="t"><base-input v-model="username" placeholder="Username пользователя"></base-input></span>
                        <span class="t s"><base-button nativeType="submit">Найти</base-button></span><br>
                    </form>
                </div>
            </div>
            <div class="response">
                <span><h5>Результат поиска:</h5></span>
                <div v-if="info.length !== 0">{{info}}</div>
                <div v-else>
                    <card class="film-card">
                    <img class="user-icon" src="../../assets/icons/common/ikonka.png">
                    <span class="username">{{user.name}}</span>
                    </card>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "ChangePermissionsLevel",
        data() {
            return {
                user: '',
                username: '',
                info: ''
            }
        },
        methods: {
            searchUser() {
                this.info = '';
                axios.defaults.headers = {
                    'Content-Type': 'application/json',
                    Authorization: this.authorization
                };

                axios.get(this.url + "/api/account/" + this.username).then((response) => {
                    if (response.status === 204) {
                        this.info = "Пользователь " + this.username + " не найден";
                    } else {
                        this.user = response.data;
                        this.username = '';
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .username-input {
    }

    .t {
        position: relative;
        float: left;
        margin-right: 5px;
        width: 300px;
    }

    .s {
        margin-top: 1px;
    }

    .response {
        position: fixed;
        float: bottom;
        margin-top: 50px;
    }
    .user-icon {
        width: 50px;
        border-radius: 20px;
        float: left;
    }
    .username {
        float: left;
        margin-top: 10px;
        margin-left: 5px;
    }
</style>