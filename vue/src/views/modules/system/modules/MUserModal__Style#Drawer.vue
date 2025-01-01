<template>
  <a-drawer :title="title" :width="width" placement="right" :closable="false" @close="close" :visible="visible">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="账号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['account', validatorRules.account]" placeholder="请输入账号"></a-input>
        </a-form-item>
        <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入姓名"></a-input>
        </a-form-item>
        <a-form-item v-if="shwoPwd" label="密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-password v-decorator="['pwd', validatorRules.pwd]" placeholder="请输入密码"></a-input-password>
        </a-form-item>
        <a-form-item label="机构" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag
            v-decorator="['orgId', validatorRules.orgId]"
            :triggerChange="true"
            placeholder="机构"
            dictCode="sys_org,name,id"
          />
        </a-form-item>
        <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag v-decorator="['gender']" :triggerChange="true" placeholder="性别" dictCode="gender" />
        </a-form-item>
        <a-form-item label="头像" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-image-upload class="avatar-uploader" text="上传" v-decorator="['avatar']"></j-image-upload>
        </a-form-item>
        <a-form-item label="电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['phone']" placeholder="请输入电话"></a-input>
        </a-form-item>
        <a-form-item label="邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['email']" placeholder="请输入邮箱"></a-input>
        </a-form-item>
        <a-form-item label="角色" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-multi-select-tag v-decorator="['roleIds', validatorRules.roleIds]" placeholder="请选择角色" dictCode="sys_role,name,id" />
        </a-form-item>
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import pick from 'lodash.pick'
import { validateDuplicateValue } from '@/utils/util'
import { config } from 'vue-clipboard2'

export default {
  name: 'MUserModal',
  components: {},
  data() {
    return {
      form: this.$form.createForm(this),
      title: '操作',
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
        account: {
          rules: [{ required: true, message: '请输入账号!' }],
        },
        name: {
          rules: [{ required: true, message: '请输入姓名!' }],
        },
        pwd: {
          rules: [{ required: true, message: '请输入密码!' }],
        },
        orgId: {
          rules: [{ required: true, message: '请输入机构!' }],
        },
        roleIds: {
          rules: [{ required: true, message: '请选择角色标签!' }],
        },
      },
      url: {
        add: '/sys/user/add',
        edit: '/sys/user/edit',
        listRoleIds: '/sys/user/listRoleIds',
      },
      roleIds: undefined,
      shwoPwd: true,
    }
  },
  created() {},
  methods: {
    async loadRoles(record) {
      let arr = await getAction(this.url.listRoleIds, { id: record.id }).then((res) => {
        if (res.success) {
          return res.result.join(',')
        } else {
          return []
        }
      })
      return arr
    },
    add() {
      this.edit({})
    },
    async edit(record) {
      let fields = ['account', 'name', 'orgId', 'gender', 'avatar', 'phone', 'email', 'roleIds']
      if (record.id) {
        this.shwoPwd = false
      } else {
        this.shwoPwd = true
        fields = [...fields, 'pwd']
      }
      await this.loadRoles(record).then((res) => (this.roleIds = res))
      record.roleIds = this.roleIds
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, ...fields))
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleOk() {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          let formData = Object.assign(this.model, values)
          console.log('表单提交数据', formData)
          httpAction(httpurl, formData, method)
            .then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok')
              } else {
                that.$message.warning(res.message)
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
    popupCallback(row) {
      this.form.setFieldsValue(pick(row, 'account', 'name', 'pwd', 'gender', 'avatar', 'phone', 'email', 'roleIds'))
    },
  },
}
</script>

<style lang="less" scoped>
/** Button按钮间距 */
.ant-btn {
  margin-left: 30px;
  margin-bottom: 30px;
  float: right;
}
.avatar-uploader > .ant-upload {
  width: 104px;
  height: 104px;
}
.ant-upload-select-picture-card i {
  font-size: 49px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>