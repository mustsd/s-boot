<template>
  <div class="user-wrapper">
    <div class="content-box">
      <span>机构：{{ userInfo.orgName }}</span>
      <span class="action" style="margin-right: 10px">
        <a-badge :count="unread">
          <router-link :to="{ path: '/sys/myNotice' }">
            <a-icon style="font-size: 16px; padding: 4px" type="bell" />
          </router-link>
        </a-badge>
      </span>
      <a-dropdown>
        <span class="action ant-dropdown-link user-dropdown-menu">
          <a-avatar class="avatar" shape="square" size="small" :src="avatar" />
          <span>{{ nickname }}</span>
        </span>
        <a-menu slot="overlay" class="user-dropdown-menu-wrapper">
          <a-menu-item key="0">
            <router-link :to="{ path: '/account/usercenter' }">
              <a-icon type="user" />
              <span>个人中心</span>
            </router-link>
          </a-menu-item>
          <a-menu-divider />
          <a-menu-item key="1">
            <a href="javascript:;" @click="handleLogout">
              <a-icon type="logout" />
              <span style="color: red">退出登录</span>
            </a>
          </a-menu-item>
        </a-menu>
      </a-dropdown>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'UserMenu',
  components: {},
  computed: {
    ...mapGetters(['nickname', 'avatar', 'unread', 'userInfo']),
  },
  data() {
    return {}
  },
  methods: {
    ...mapActions(['Logout']),
    handleLogout() {
      this.$confirm({
        title: '提示',
        content: '确定要注销登录吗 ?',
        onOk: () => {
          return this.Logout({})
            .then(() => {
              setTimeout(() => {
                window.location.reload()
              }, 16)
            })
            .catch((err) => {
              this.$message.error({
                title: '错误',
                description: err.message,
              })
            })
        },
        onCancel() {},
      })
    },
  },
}
</script>
