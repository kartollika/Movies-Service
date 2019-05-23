<template>
    <div>
        <div class="item-poster-container">
            <a :href="/director/ + nextDirector.id">
                <img class="item-poster" src="../../assets/images/iphone360_22260.jpg">
            </a>
        </div>
        <div class="item-content">
            <div class="item-title">
                <a :href="/director/ + nextDirector.id"><b>{{nextDirector.name}}</b></a>
            </div>
            <br><br>
            <div class="item-description">
                <div><b>Страна:</b> {{nextDirector.country}}</div>
                <div><b>Фильмы:</b></div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "DirectorCard",
        props: ['nextDirector']
    }
</script>

<style scoped>

</style>