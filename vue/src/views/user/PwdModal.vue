<template>
  <j-modal :title="title" :width="width" :visible="visible" @ok="handleOk" @cancel="handleCancel" cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="原密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-password
            v-decorator="['originalPwd', validatorRules.originalPwd]"
            placeholder="请输入原密码"
          ></a-input-password>
        </a-form-item>
        <a-form-item label="新密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-password
            v-decorator="['newPwd', validatorRules.newPwd]"
            placeholder="请输入新密码"
          ></a-input-password>
        </a-form-item>
        <a-form-item label="确认密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-password
            v-decorator="['repeatPwd', validatorRules.repeatPwd]"
            placeholder="请再次输入新密码"
          ></a-input-password>
        </a-form-item>
      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
import { postAction } from '@/api/manage'
import { mapActions } from 'vuex'

export default {
  name: 'PwdModal',
  components: {},
  data() {
    return {
      form: this.$form.createForm(this),
      title: '密码修改',
      width: 400,
      visible: false,
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      confirmLoading: false,
      validatorRules: {
        originalPwd: {
          rules: [{ required: true, message: '请输入原密码!' }],
        },
        newPwd: {
          rules: [{ required: true, message: '请输入新密码!' }],
        },
        repeatPwd: {
          rules: [{ required: true, message: '请再次输入新密码!' }],
        },
      },
      url: {
        modifyPwd: '/sys/user/modifyPwd',
      },
    }
  },
  created() {},
  methods: {
    ...mapActions(['RefrashToken']),
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleOk() {
      const that = this
      this.form.validateFields((err, values) => {
        if (!err) {
          let formData = Object.assign(this.model, values)
          if (formData.newPwd != formData.repeatPwd) {
            that.$message.error('两次密码输入不一致')
            return
          }
          that.confirmLoading = true
          console.log('表单提交数据', formData)
          postAction(that.url.modifyPwd, formData)
            .then((res) => {
              if (res.success) {
                console.log(res)
                that.RefrashToken(res.result)
                that.$message.success(res.message)
              } else {
                that.$message.error(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
              that.close()
            })
        }
      })
    },
    handleCancel() {
      this.close()
    },
  },
}
</script>