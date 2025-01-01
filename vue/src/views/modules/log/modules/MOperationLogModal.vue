<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="用户id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['userId', validatorRules.userId]" placeholder="请输入用户id"></a-input>
        </a-form-item>
        <a-form-item label="行为" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['action']" placeholder="请输入行为"></a-input>
        </a-form-item>
        <a-form-item label="调用方法" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['method']" placeholder="请输入调用方法"></a-input>
        </a-form-item>
        <a-form-item label="IP" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['ip']" placeholder="请输入IP"></a-input>
        </a-form-item>
        <a-form-item label="请求参数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['param']" rows="4" placeholder="请输入请求参数" />
        </a-form-item>
        <a-form-item label="操作耗时" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['cost']" placeholder="请输入操作耗时" style="width: 100%" />
        </a-form-item>
        <a-form-item label="操作失败" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['failure']" placeholder="请输入操作失败" style="width: 100%" />
        </a-form-item>
        <a-form-item label="失败原因" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['error']" rows="4" placeholder="请输入失败原因" />
        </a-form-item>
        <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['createBy']" placeholder="请输入创建人"></a-input>
        </a-form-item>
        <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date
            placeholder="请选择创建时间"
            v-decorator="['createTime']"
            :trigger-change="true"
            style="width: 100%"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
import { httpAction } from '@/api/manage'
import pick from 'lodash.pick'
import { validateDuplicateValue } from '@/utils/util'

export default {
  name: 'MOperationLogModal',
  components: {},
  data() {
    return {
      form: this.$form.createForm(this),
      title: '操作',
      width: 800,
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
        userId: {
          rules: [{ required: true, message: '请输入用户id!' }],
        },
      },
      url: {
        add: '/sys/operationLog/add',
        edit: '/sys/operationLog/edit',
      },
    }
  },
  created() {},
  methods: {
    add() {
      this.edit({})
    },
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(
            this.model,
            'userId',
            'action',
            'method',
            'ip',
            'param',
            'cost',
            'failure',
            'error',
            'createBy',
            'createTime'
          )
        )
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
      this.form.setFieldsValue(
        pick(row, 'userId', 'action', 'method', 'ip', 'param', 'cost', 'failure', 'error', 'createBy', 'createTime')
      )
    },
  },
}
</script>