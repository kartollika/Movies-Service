<template>
    <div v-show="userRole === 'ADMIN'">
        <div>
            <Header/>
            <div class="content">
                <div>
                    <span class="admin-menu"><a href="#" @click="currentPage = 'requests'">Запросы на изменения</a></span>
                    <span class="admin-menu"><a href="#" @click="currentPage = 'changeRole'">Измение роли пользователя</a></span>
                </div>
                <br><br>
                <div v-if="currentPage === 'requests'">
                    <ChangeCard class="ChangeCard"></ChangeCard>
                    <ChangeCard class="ChangeCard"></ChangeCard>
                </div>
                <div v-else-if="currentPage === 'changeRole'">
                    <change-permissions-level></change-permissions-level>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import ChangeCard from "../components/AdminPanel/ChangeCard.vue";
    import ChangePermissionsLevel from "../components/AdminPanel/ChangePermissionsLevel"
    import axios from "axios"

    export default {
        components: {
            ChangeCard,
            ChangePermissionsLevel
        },
        data() {
            return {
                userRole: '',
                currentPage: "requests"
            }
        },
        mounted() {
            axios.get("http://localhost:8080/api/account").then((response) => {
                this.userRole = response.data.permissionsLevel;
            });
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
        color: #32325d;
    }
</style>