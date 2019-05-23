<template>
    <div class="account-info">
        <div class="username">{{user.name}}</div>
        <base-dropdown position="right">
            <a slot="title">
                <img class="user-logo" src="../../assets/icons/common/ikonka.png">
            </a>
            <a class="dropdown-item" href="/history/">История просмотров</a>
            <a class="dropdown-item" href="/wishlist/">Список желаний</a><hr>
            <div v-show="user.permissionsLevel === 'ADMIN'"><a class="dropdown-item" href="/admin/requests">Панель администратора</a><hr></div>
            <div v-show="user.permissionsLevel === 'EDITOR'"><a class="dropdown-item" href="/editor/requests">Панель модератора</a><hr></div>
            <a class="dropdown-item" href="#" @click="logout">Выйти</a>
        </base-dropdown>
    </div>
</template>

<script>
    import BaseDropdown from '../base_components/BaseDropdown'
    import axios from 'axios'

    export default {
        name: "UserProfile",
        components: {
            BaseDropdown,
        },
        data() {
            return {
                user: ''
            }
        },
        mounted() {
            if (this.authorization !== null) {
                axios.defaults.headers = {
                    'Content-Type': 'application/json',
                    Authorization: this.authorization
                };

                axios.get(this.url + "/api/account").then((response) => {
                    this.user = response.data;
                });
            }
        },

        methods: {
            logout() {
                localStorage.removeItem("Authorization");
                window.location.reload();
            }
        }
    }
</script>

<style scoped>
    .account-info {
        position: relative;
        float: right;
        margin-top: 6px;
    }

    .user-logo {
        width: 50px;
        height: 50px;
        border-radius: 20px;
        float: right;
        cursor: pointer;
    }

    .username {
        padding-top: 10px;
        margin-right: 10px;
        float: left;
        color: #0E0E0E;
    }
    .account-info hr {
        margin-top: 5px;
        margin-bottom: 5px;
    }

</style>