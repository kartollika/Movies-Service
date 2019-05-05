<template>
    <div>
        <div>
            <span class="admin-menu"><a href="/admin/requests">Запросы на изменения</a></span>
            <span class="admin-menu"><a href="/admin/change">Измение роли пользователя</a></span>
            <span class="admin-menu"><a @click="showFilmFormToAdd = true" href="#">Добавление фильма</a></span>
        </div>
        <modal :show.sync="showFilmFormToAdd" body-classes="p-0" modal-classes="modal-dialog modal-sm"
               class="film-form-background">
            <card type="secondary" class="border-0 film-form-content">
                <template>
                    <h4>Добавление фильма</h4>
                    <film-form :permissions-level="'ADMIN'"></film-form>
                </template>
            </card>
        </modal>
    </div>
</template>

<script>
    import FilmForm from '../item_card/FormToAddAndUpdateFilm'
    import Modal from '../base_components/Modal'
    export default {
        name: "AdminMenu",
        components: {
            FilmForm,
            Modal
        },
        data() {
            return {
                showFilmFormToAdd: false
            }
        }
    }
</script>

<style scoped>
</style>