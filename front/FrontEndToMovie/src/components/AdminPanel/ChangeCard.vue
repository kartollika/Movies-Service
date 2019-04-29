<template>
    <div>
        <div>
            <div class="requests-poster-container">
                <img class="requests-poster" src="../../../public/img/posters/Марсианин.jpg">
            </div>
            <div class="requests-content">

            </div>

        </div>
        <card class="film-card">
            <div class="modal card-changes">
                <h6 id="modal-title-default" class="modal-title">Запрос на изменение</h6>
                <button type="button" data-dismiss="modal" aria-label="Close" class="close">
                    <span>×</span>
                </button>
            </div>
            <div class="modal-body card-changes">
                <h5>Как приручить дракона 3</h5>
                <img src="../../../public/img/posters/семь.jpg" alt="Постер фильма" align="left">
                <div class="requests"><p>Тут будут запросы от Editor-ов.</p></div>
            </div>
            <div class="modal-footer card-changes">
                <button type="button" class="btn btn-primary">Save changes</button>
                <button type="button" class="btn btn-primary ml-auto">Close</button>
            </div>
        </card>
    </div>
</template>

<script>
    import Card from "../Base/Card.vue";

    export default {
        components: {
            Card
        }
    };
</script>

<style>
    .modal {
        background-color: rgba(0, 0, 0, 0.3);
    }

    img {
        width: 100px;
    }

    .requests-poster-container {
        float: left;
    }

    .requests-poster {
        width: 150px;
        border-radius: 5px;
    }

    .requests-content {
        width: 900px;
        height: 300px;
        background-color: white;
        float: left;
    }

    .requests {
        padding-left: 10px;
        margin-left: 10px;
    }
</style>