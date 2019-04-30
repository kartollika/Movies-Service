<template>
    <div>
        <div>
            <div class="requests-poster-container">
                <img class="requests-poster" src="../../assets/icons/common/ikonka.png">
            </div>
            <div class="requests-content">
            </div>
        </div>
    </div>
</template>

<script>
    export default {
    };
</script>

<style>

    .requests-poster-container {
        float: left;
    }

    .requests-poster {
        width: 150px;
        border-radius: 5px;
        margin-top: 10px;
    }

    .requests-content {
        margin-top: 10px;
        width: 890px;
        height: 225px;
        background-color: white;
        float: left;
        border-radius: 5px;
        margin-left: 10px;
    }

    .requests {
        padding-left: 10px;
        margin-left: 10px;
    }
</style>