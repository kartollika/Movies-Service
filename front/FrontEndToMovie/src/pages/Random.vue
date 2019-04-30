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

            axios.get(this.url + "/api/films/random").then(response => {
                this.film = response.data;
                this.$router.push("/film/" + this.film.id);
            });
        }
    };
</script>

<style scoped>
</style>