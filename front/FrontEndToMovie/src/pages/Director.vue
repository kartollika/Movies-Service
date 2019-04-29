<template>
    <div>
        <Header></Header>
        <div class="content-container">
            <div class="content">
                <div class="big-item-poster-container">
                    <img class="big-poster" src="../../public/img/posters/Марсианин.jpg">
                </div>
                <div class="big-item-content">
                    <div>
                        <h3>{{director.name}}</h3>
                    </div>
                    <div class="big-item-description">
                        <div><b>Карьера:</b> Режиссёр</div>
                        <div><b>Страна:</b> {{director.country}}</div>
                        <div><b>Фильмы: </b></div>
                    </div>
                </div>
            </div>
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
</style>