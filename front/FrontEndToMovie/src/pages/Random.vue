<template>
    <div>
        <Header></Header>
        <div class="content-container">
            <div class="content" v-if="authorization === null">
                <un-authorized-error></un-authorized-error>
            </div>
        </div>
    </div>
</template>
<script>
    import axios from "axios";

    export default {
        name: "Random",
        data() {
            return {
                film: []
            };
        },
        mounted() {
            axios.defaults.headers = {
                "Content-Type": "application/json",
                Authorization: this.authorization
            };
            if (this.authorization !== null) {
                axios.get(this.url + "/api/films/random").then(response => {
                    this.film = response.data;
                    this.$router.push("/film/" + this.film.id);
                });
            }
        }
    };
</script>

<style scoped>
</style>