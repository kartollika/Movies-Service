<template>
    <div>
        <img class="user-logo" src="../../public/img/xno-user.jpg.pagespeed.ic.bwzYx3O8_n.jpg">
        <div class="username">{{user.name}}</div>
    </div>
</template>

<script>
    import BaseDropdown from './Base/BaseDropdown'
    import Modal from '../components/Modal'
    import axios from 'axios'
    export default {
        name: "UserProfile",
        components: {
            BaseDropdown,
            Modal,
        },
        data() {
            return {
                showMenu: false,
                user: '',
                authorization: localStorage.getItem("Authorization")
            }
        },
        mounted() {
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };

            axios.get("http://localhost:8080/api/account").then((response) => {
                this.user = response.data;
            });
        }
    }
</script>

<style scoped>
    .user-logo {
        width: 50px;
        height: 50px;
        border-radius: 15px;
        float: right;
        cursor: pointer;
    }

    .username {
        padding-top: 10px;
        margin-right: 10px;
        float: right;
        color: #f6f9fc;
    }
</style>