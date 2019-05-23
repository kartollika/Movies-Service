<template>
    <transition name="notification">
        <div v-show="showNotification">
            <base-alert type="success" v-if="type === 'success'">
                <strong>Success!</strong><slot></slot>
            </base-alert>
            <base-alert type="warning" v-if="type === 'warning'">
                <strong>Warning!</strong><slot></slot>
            </base-alert>
        </div>
    </transition>
</template>

<script>
    export default {
        name: "Notification",
        props: {
            showNotification: {
                type: Boolean,
                default: false
            },
            type: {
                type: String,
                default: 'success'
            }
        }
    }
</script>

<style scoped>
</style>