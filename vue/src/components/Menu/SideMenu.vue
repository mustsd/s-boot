<template>
  <a-layout-sider
    :class="['sider', isDesktop() ? null : 'shadow', theme, fixSiderbar ? 'ant-fixed-sidemenu' : null]"
    width="256px"
    :collapsible="collapsible"
    v-model="collapsed"
    :trigger="null"
  >
    <logo :title="appName" />
    <s-menu
      :collapsed="collapsed"
      :menu="menus"
      :theme="theme"
      :mode="mode"
      @select="onSelect"
      style="padding: 16px 0px"
    ></s-menu>
    <!-- 广告代码 真实项目中请移除 -->
    <!-- <ads v-if="!collapsed" /> -->
  </a-layout-sider>
</template>

<script>
import Logo from '@/components/tools/Logo'
import SMenu from './index'
import { mixin, mixinDevice } from '@/utils/mixin'
import Ads from '@/components/Other/CarbonAds'

export default {
  name: 'SideMenu',
  components: { Logo, SMenu, Ads },
  mixins: [mixin, mixinDevice],
  props: {
    appName: {
      type: String,
      required: false,
      default: 'Manage',
    },
    mode: {
      type: String,
      required: false,
      default: 'inline',
    },
    theme: {
      type: String,
      required: false,
      default: 'dark',
    },
    collapsible: {
      type: Boolean,
      required: false,
      default: false,
    },
    collapsed: {
      type: Boolean,
      required: false,
      default: false,
    },
    menus: {
      type: Array,
      required: true,
    },
  },
  methods: {
    onSelect(obj) {
      this.$emit('menuSelect', obj)
    },
  },
}
</script>
