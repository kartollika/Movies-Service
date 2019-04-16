<template>
    <div>
    <div style="position: relative">
        <base-nav type="default" effect="dark" expand>
            <a class="logo navbar-brand" href="/">Movies Service</a>
            <div class="nav-menu">
                <ul class="navbar-nav ml-lg-auto">
                    <li class="nav-item">
                        <a class="nav-link nav-link-icon" href="/">Главая</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-icon" href="#">Случайный фильм</a>
                    </li>
                    <!--<li class="nav-item">-->
                        <!--<a class="nav-link nav-link-icon" href="#">Новинки</a>-->
                    <!--</li>-->
                    <li class="nav-item">
                        <genre-dropdown />
                    </li>
                </ul>
            </div>
            <div class="search-nav">
                <form @submit="search">
                    <base-input placeholder="Поиск" v-model="searchQuery"
                                addon-right-icon="ni ni-zoom-split-in"></base-input>
                </form>
            </div>
            <div v-if="authorization === null"><a class="nav-link nav-link-icon" href="#">
                <login-modal></login-modal>
            </a></div>
            <div class="user-profile" v-else>
                <user-profile></user-profile>
            </div>
        </base-nav>
    </div>
    </div>
</template>

<script>
    import BaseNav from "../Base/BaseNav";
    import LoginModal from "./LoginModal";
    import UserProfile from "./UserProfile";
    import GenreDropdown from "./GenreDropdown.vue";

    export default {
        name: "Header",
        components: {
            BaseNav,
            LoginModal,
            UserProfile,
            GenreDropdown
        },
        data() {
            return {
                authorization: '',
                searchQuery: ''
            }
        },
        mounted() {
            this.authorization = localStorage.getItem("Authorization");
        },
        methods: {
            search() {
                if (this.searchQuery !== null) {
                    this.$router.push("/search/" + this.searchQuery);
                }
            }
        }
    };
</script>

<style>
    .logo.navbar-brand {
        float: left;
        margin-left: -150px;
    }

    .nav-menu {
        margin-left: 50px;
    }

    .search-nav {
        height: 10px;
        width: 300px;
        padding-bottom: 50px;
        margin-left: 75px;
    }

    .user-profile {
        position: absolute;
        right: 50px;
        width: 400px;
        padding: 5px;
    }
</style>
