<template>
  <keep-alive :include="cachedViews">
    <router-view />
  </keep-alive>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'RouteView',
  data() {
    return {}
  },
  computed: {
    ...mapGetters(['cachedViews']),
    key() {
      return this.$route.path
    }
  }
}
</script>
