<template>
    <div>
        <div style="position: relative">
            <base-nav class="header-background" type="default" expand>
                <a slot="brand" class="logo navbar-brand" href="/"><img src="../../assets/images/logo.png"></a>
                <div class="nav-menu">
                    <ul class="navbar-nav ml-lg-auto">
                        <li class="nav-item">
                            <a class="nav-link nav-link-icon" href="/">Главная</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link nav-link-icon" href="/random/">Случайный фильм</a>
                        </li>
                        <li class="nav-item">
                            <genre-dropdown/>
                        </li>
                    </ul>
                </div>
                <div class="search-nav">
                    <form @submit="search">
                        <input placeholder="Поиск..." type="search" v-model="searchQuery">
                        <span class="search-icon">
                            <i class="fa fa-search" aria-hidden="true" @click="search"></i>
                        </span>
                    </form>
                </div>
                <div v-if="authorization === null">
                    <login-modal></login-modal>
                </div>
                <div class="user-profile" v-else>
                    <user-profile></user-profile>
                </div>
            </base-nav>
        </div>
    </div>
</template>

<script>
    import BaseNav from "../base_components/BaseNav";
    import LoginModal from "./LoginModal";
    import UserProfile from "./UserProfile";
    import GenreDropdown from "./GenreDropdown";

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
                searchQuery: '',
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
    .navbar-brand img {
	height: 50px;
    }

    .logo.navbar-brand {
	position: absolute;
	left: 55px;
    }

    .nav-menu {
        margin-left: 50px;
    }

    input[type="search"]::placeholder {
        color: #E9E7E8;
    }

    input[type="search"] {
        width: 350px;
        border-radius: 10px;
        border: 0px;
        padding: 5px 10px;
        background-color: #0E0E0E;
        margin-top: 8px;
        color: #E9E7E8;

    }

    .search-nav {
	position: relative;
        height: 10px;
        width: 300px;
        padding-bottom: 50px;
        margin-left: 75px;
    }

    .search-icon {
        color: #E9E7E8;
	position: absolute;
	top: 12px;
	right: -40px;
    }

    .user-profile {
        position: absolute;
        right: 50px;
        width: 400px;
        padding: 5px;
    }

    .header-background {
        background-image: url(../../assets/images/shapka.png);
        background-size: cover;

    }
    .fa.fa-search:hover {
        cursor: pointer;
    }
</style>
