<template>
  <a-layout :class="['layout', device]">
    <!-- SideMenu -->
    <a-drawer
      v-if="isMobile()"
      placement="left"
      :wrapClassName="`drawer-sider ${navTheme}`"
      :closable="false"
      :visible="collapsed"
      @close="drawerClose"
    >
      <side-menu
        mode="inline"
        :menus="menus"
        :theme="navTheme"
        :collapsed="false"
        :collapsible="true"
        @menuSelect="menuSelect"
      ></side-menu>
    </a-drawer>

    <side-menu
      v-else-if="isSideMenu()"
      mode="inline"
      :menus="menus"
      :theme="navTheme"
      :appName="appName"
      :collapsed="collapsed"
      :collapsible="true"
    ></side-menu>

    <a-layout
      :class="[layoutMode, `content-width-${contentWidth}`]"
      :style="{ paddingLeft: contentPaddingLeft, minHeight: '100vh' }"
    >
      <!-- layout header -->
      <global-header
        :mode="layoutMode"
        :menus="menus"
        :theme="navTheme"
        :collapsed="collapsed"
        :device="device"
        @toggle="toggle"
      />

      <!-- layout content -->
      <a-layout-content :style="{ height: '100%', margin: '24px 24px 0', paddingTop: fixedHeader ? '64px' : '0' }">
        <multi-tab v-if="multiTab"></multi-tab>
        <transition name="page-transition" mode="out-in">
          <route-view v-if="routerActive" />
        </transition>
      </a-layout-content>

      <!-- layout footer -->
      <a-layout-footer>
        <global-footer />
      </a-layout-footer>

      <!-- Setting Drawer (show in development mode) -->
      <!-- <setting-drawer v-if="!production"></setting-drawer> -->
      <setting-drawer></setting-drawer>
    </a-layout>
  </a-layout>
</template>

<script>
import { triggerWindowResizeEvent } from '@/utils/util'
import { mapState, mapActions } from 'vuex'
import { mixin, mixinDevice } from '@/utils/mixin'
import config from '@/config/defaultSettings'

import RouteView from './RouteView'
import SideMenu from '@/components/Menu/SideMenu'
import GlobalHeader from '@/components/GlobalHeader'
import GlobalFooter from '@/components/GlobalFooter'
import SettingDrawer from '@/components/SettingDrawer'
import { convertRoutes } from '@/utils/routeConvert'

export default {
  name: 'BasicLayout',
  mixins: [mixin, mixinDevice],
  components: {
    RouteView,
    SideMenu,
    GlobalHeader,
    GlobalFooter,
    SettingDrawer,
  },
  data() {
    return {
      production: config.production,
      collapsed: false,
      menus: [],
      appName: '',
      routerActive: true,
    }
  },
  computed: {
    ...mapState({
      // 动态主路由
      mainMenu: (state) => state.permission.routers,
    }),
    contentPaddingLeft() {
      if (!this.fixSidebar || this.isMobile()) {
        return '0'
      }
      if (this.sidebarOpened) {
        return '256px'
      }
      return '80px'
    },
  },
  watch: {
    sidebarOpened(val) {
      this.collapsed = !val
    },
  },
  created() {
    const routes = convertRoutes(this.mainMenu.find((item) => item.path === '/'))
    this.menus = (routes && routes.children) || []
    this.collapsed = !this.sidebarOpened
    this.appName = this.$store.getters.serverNickName
    this.$socket.connect()
  },
  destroyed() {
    this.$socket.disconnect()
  },
  sockets: {
    unread(res) {
      this.$store.commit('SET_UNREAD', res.unread)
      // this.showNotify(res.title, 10)
    },
    notify(res) {
      this.showNotify(res, 60)
    },
    logout(res) {
      this.Logout({})
        .then(() => {
          this.$confirm({
            title: '提示',
            cancelText: '',
            content: res,
            onOk() {
              return (window.location.reload())
            },
            onCancel() {
              return (window.location.reload())
            },
          })
        })
        .catch((err) => {
          console.log(err)
          this.$message.error({
            title: '错误',
            description: err,
          })
        })
    },
  },
  mounted() {
    const userAgent = navigator.userAgent
    if (userAgent.indexOf('Edge') > -1) {
      this.$nextTick(() => {
        this.collapsed = !this.collapsed
        setTimeout(() => {
          this.collapsed = !this.collapsed
        }, 16)
      })
    }
  },
  provide() {
    return {
      reload: this.reload,
    }
  },
  methods: {
    ...mapActions(['setSidebar', 'Logout']),
    reload() {
      this.routerActive = false
      this.$nextTick(() => {
        this.routerActive = true
      })
    },
    showNotify(msg, duration) {
      this.$notification.open({
        message: '系统通知',
        description: msg,
        duration: duration || 30,
      })
    },
    toggle() {
      this.collapsed = !this.collapsed
      this.setSidebar(!this.collapsed)
      triggerWindowResizeEvent()
    },
    paddingCalc() {
      let left = ''
      if (this.sidebarOpened) {
        left = this.isDesktop() ? '256px' : '80px'
      } else {
        left = (this.isMobile() && '0') || (this.fixSidebar && '80px') || '0'
      }
      return left
    },
    menuSelect() {},
    drawerClose() {
      this.collapsed = false
    },
  },
}
</script>

<style lang="less">
@import '~@assets/less/transition.less';
</style>
