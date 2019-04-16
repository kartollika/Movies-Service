<template>
    <div class="account-info">
        <div class="username">{{user.name}}</div>
        <base-dropdown position="right">
            <a slot="title">
                <img class="user-logo" src="../../../public/img/xno-user.jpg.pagespeed.ic.bwzYx3O8_n.jpg">
            </a>
            <a class="dropdown-item" href="/history/">История просмотров</a>
            <a class="dropdown-item" href="/wishlist/">Список желаний</a><hr>
            <div v-show="user.permissionsLevel === 'ADMIN'"><a class="dropdown-item" href="/admin/">Панель администратора</a><hr></div>
            <a class="dropdown-item" href="#" @click="logout">Выйти</a>
        </base-dropdown>
    </div>
</template>

<script>
    import BaseDropdown from '../Base/BaseDropdown'
    import axios from 'axios'

    export default {
        name: "UserProfile",
        components: {
            BaseDropdown,
        },
        data() {
            return {
                user: '',
                authorization: localStorage.getItem("Authorization"),
            }
        },
        mounted() {
            if (this.authorization !== null) {
                axios.defaults.headers = {
                    'Content-Type': 'application/json',
                    Authorization: this.authorization
                };

                axios.get("http://localhost:8080/api/account").then((response) => {
                    this.user = response.data;
                });

                axios.get("http://localhost:8080/api/account/wishlist").then((response) => {
                    let serialObj = JSON.stringify(response.data)
                    localStorage.setItem("wishlist", serialObj);
                });
            }
        },
        methods: {
            logout() {
                localStorage.removeItem("Authorization");
                this.$router.push("../");
            }

        }
    }
</script>

<style scoped>
    .account-info {
        position: relative;
        float: right;
        margin-top: 5px;
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
        color: #f6f9fc;
    }
</style>