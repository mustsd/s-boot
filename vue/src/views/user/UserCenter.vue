<template>
  <a-card :bordered="false" type="inner" class="info-container">
    <a-row :gutter="24">
      <a-col :md="8" :sm="8">
        <a-row> <a-divider>个人信息</a-divider></a-row>
        <a-row> <img class="info-avatar" :src="userInfo.avatar" /> </a-row>
        <a-row
          ><a-icon type="user" /><span class="info-key">姓名</span
          ><span class="info-val">{{ userInfo.name }}</span></a-row
        >
        <a-row
          ><a-icon type="man" /><span class="info-key">性别</span>
          <span v-if="userInfo.gender === 1" class="info-val">男</span>
          <span v-else-if="userInfo.gender === 2" class="info-val">女</span>
          <span v-else class="info-val">未指定</span>
        </a-row>
        <a-row
          ><a-icon type="phone" /><span class="info-key">电话</span
          ><span class="info-val">{{ userInfo.phone }}</span></a-row
        >
        <a-row
          ><a-icon type="mail" /><span class="info-key">邮箱</span
          ><span class="info-val">{{ userInfo.email }}</span></a-row
        >
        <a-row
          ><a-icon type="key" /><span class="info-key">密码</span
          ><a-button class="pwd-mod" type="link" @click="handlePwd">修改</a-button></a-row
        >
      </a-col>
    </a-row>
    <pwd-modal ref="pwdModal"></pwd-modal>
  </a-card>
</template>
<script>
import { mapGetters } from 'vuex'
import PwdModal from './PwdModal'

export default {
  name: 'UserCenter',
  components: {
    PwdModal
  },
  data() {
    return {
      description: '用户中心'
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  created() {
    console.log(this.userInfo)
  },
  methods: {
    handlePwd() {
      console.log(this.userInfo)
      this.$refs.pwdModal.visible = true
    }
  }
}
</script>
<style scoped>
.info-container {
  height: 99%;
}
.info-key {
  margin-left: 20px;
  color: rgb(91, 191, 238);
}
.info-val {
  float: right;
  margin-right: 20px;
  font-style: italic;
}
.pwd-mod {
  float: right;
  margin-right: 5px;
}
.info-avatar {
  float: right;
  margin-right: 20px;
  margin-bottom: 25px;
  height: 80px;
  width: 80px;
}
</style>
