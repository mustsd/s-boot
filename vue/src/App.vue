<template>
  <a-config-provider :locale="locale">
    <div id="app">
      <transition name="page-transition" mode="out-in">
        <router-view />
      </transition>
    </div>
  </a-config-provider>
</template>

<script>
import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'
import { AppDeviceEnquire } from '@/utils/mixin'

export default {
  mixins: [AppDeviceEnquire],
  data() {
    return {
      locale: zhCN,
    }
  },
  mounted() {},
}
</script>
<style lang="less" scoped>
@import '~@assets/less/transition.less';
</style>
