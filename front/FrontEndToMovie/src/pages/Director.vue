<template>
    <div>
        <Header></Header>
        <div class="content">
            <card class="film-card">
                <div class="director-info">
                <h3>{{director.name}}</h3>
                <img class="poster-lg" src="../../public/img/posters/Марсианин.jpg">
                <div class="description">
                    <div class="description-item"><b>Карьера:</b> Режиссёр</div><hr>
                    <div class="description-item"><b>Страна:</b> {{director.country}}</div><hr>
                    <div class="description-item"><b>Фильмы: </b></div><hr>
                </div>
            </div>
            </card>
        </div>
    </div>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "Director",
        data() {
            return {
                director: '',
                authorization: localStorage.getItem("Authorization")
            }
        },
        mounted() {
            axios.defaults.headers = {
                'Content-Type': 'application/json',
                Authorization: this.authorization
            };
            axios.get(this.url + "/api/directors/director/" + this.$route.params.id).then((response) => {
                this.director = response.data;
            })
        }
    }
</script>

<style scoped>
    .director-info .description {
        margin: 20px;
        font-size: 15px;
    }
</style>