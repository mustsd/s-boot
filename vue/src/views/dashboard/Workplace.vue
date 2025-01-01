<template>
  <a-card :bordered="false">
    <a-row :gutter="24">
      <h1>{{ timeFix }} {{ nickname }} ,{{ welcome }}</h1>
    </a-row>
    <a-row :gutter="24" style="margin-top: 20px">
      <h1></h1>
    </a-row>
  </a-card>
</template>

<script>
import { timeFix } from '@/utils/util'
import { mapState } from 'vuex'

export default {
  name: 'Workplace',
  components: {},
  data() {
    return {
      timeFix: timeFix(),
      avatar: '',
      user: {},
    }
  },
  computed: {
    ...mapState({
      nickname: (state) => state.user.name,
      welcome: (state) => state.user.welcome,
    }),
    userInfo() {
      return this.$store.getters.userInfo
    },
  },
  created() {
    this.user = this.userInfo
    this.avatar = this.userInfo.avatar
  },
  mounted() {},
  methods: {},
}
</script>

<style lang="less" scoped>
@import '~@assets/less/common.less';
</style>
