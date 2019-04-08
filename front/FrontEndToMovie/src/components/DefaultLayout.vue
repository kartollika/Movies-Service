<template>
  <div>
    <slot name="header"></slot>
    <div class="row" id="body-row">
      <div>
      <slot name="sidebar"></slot>
      </div>
      <div class="col">
        <slot name="content"></slot>
      </div>
    </div>
  </div>
</template>

<script>
</script>

<style>
#body-row {
  margin-left: 0;
  margin-right: 0;
}
</style>
