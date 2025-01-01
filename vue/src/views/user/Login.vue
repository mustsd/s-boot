<template>
  <div class="main">
    <a-form id="formLogin" class="user-layout-login" ref="formLogin" :form="form" @submit="handleSubmit">
      <a-form-item>
        <a-input size="large" type="text" placeholder="请输入账号..." v-decorator="['account', validatorRules.account]">
          <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }" />
        </a-input>
      </a-form-item>
      <a-form-item>
        <a-input
          size="large"
          type="password"
          :autocomplete="false"
          placeholder="请输入密码..."
          v-decorator="['password', validatorRules.password]"
        >
          <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }" />
        </a-input>
      </a-form-item>
      <a-row :gutter="16">
        <a-col class="gutter-row" :span="16">
          <a-form-item>
            <a-input size="large" type="text" placeholder="验证码" v-decorator="['captcha', validatorRules.captcha]">
              <a-icon slot="prefix" type="safety" :style="{ color: 'rgba(0,0,0,.25)' }" />
            </a-input>
          </a-form-item>
        </a-col>
        <a-col :span="8" style="text-align: right">
          <img v-if="captchaImage" style="margin-top: 2px" :src="captchaImage" @click="reloadCaptcha" />
          <img v-else style="margin-top: 2px" src="../../assets/captcha404.png" @click="reloadCaptcha" />
        </a-col>
      </a-row>
      <a-form-item style="margin-top: 24px">
        <a-button size="large" type="primary" htmlType="submit" :loading="logining" class="login-button"
          >Login</a-button
        >
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import md5 from 'md5'
import { mapActions } from 'vuex'
import { timeFix } from '@/utils/util'
import { getCaptcha } from '@/api/login'

export default {
  components: {},
  data() {
    return {
      form: this.$form.createForm(this),
      validatorRules: {
        account: {
          rules: [{ required: true, message: '请输入账号...' }]
        },
        password: {
          rules: [{ required: true, message: '请输入密码...' }]
        },
        captcha: {
          rules: [{ required: true, message: '请输入验证码...' }]
        }
      },
      logining: false,
      captchaImage: '',
      key: ''
    }
  },
  created() {
    this.reloadCaptcha()
  },
  methods: {
    ...mapActions(['Login', 'Logout']),
    reloadCaptcha() {
      let key = new Date().getTime()
      getCaptcha(key).then(res => {
        if (res.success) {
          this.key = res.result.key
          this.captchaImage = res.result.base64Image
        } else {
          this.captchaImage = ''
          this.$message.warning(res.message)
        }
      })
    },
    handleSubmit(e) {
      e.preventDefault()
      this.logining = true
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        values.key = that.key
        if (!err) {
          that
            .Login(values)
            .then(res => that.loginSuccess(res))
            .catch(err => that.requestFailed(err))
            .finally(() => {
              that.logining = false
            })
        } else {
          that.logining = false
        }
      })
    },
    loginSuccess(res) {
      this.$router.push({ path: '/' })
      setTimeout(() => {
        this.$notification.success({
          message: '登录成功',
          description: `${timeFix() + ' ' + this.$store.getters.nickname}，欢迎回来`
        })
      }, 1000)
    },
    requestFailed(err) {
      this.$notification['error']({
        message: '错误',
        description: err.message || '请求出现错误，请稍后再试',
        duration: 4
      })
    }
  }
}
</script>

<style lang="less" scoped>
/deep/ .ant-input {
  background-color: rgba(255, 255, 255, 0.2);
}
/deep/ .ant-input:hover {
  background-color: rgba(255, 255, 255, 0.4);
}
/deep/ .ant-input:focus {
  background-color: #fff;
  color: #333;
}
.user-layout-login {
  label {
    font-size: 14px;
  }
  button.login-button {
    background-color: rgba(255, 255, 255, 0.1);
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }
  button.login-button:hover {
    background-color: rgba(255, 255, 255, 0.3);
    padding: 0 15px;
    height: 40px;
    width: 100%;
    border: 2px solid #fcfcfc;
    transition: border background-color 1s;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}
</style>
