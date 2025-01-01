<template>
  <iframe :id="id" :key="id" :src="url" frameborder="0" width="100%" height="800px" scrolling="auto"></iframe>
</template>

<script>
import Vue from 'vue'

export default {
  name: 'IframeView',
  data() {
    return {
      url: '',
      id: '',
    }
  },
  created() {
    this.goUrl()
  },
  updated() {
    this.goUrl()
  },
  watch: {
    $route(to, from) {
      this.goUrl()
    },
  },
  methods: {
    goUrl() {
      console.log(this.$route)
      this.url = this.$route.meta.target
      this.id = this.$route.path
    },
  },
}
</script>

<style>
</style>