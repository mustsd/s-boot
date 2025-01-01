<template>
  <a-drawer :title="title" :width="width" placement="right" :closable="false" @close="close" :visible="visible">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="任务名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入任务名"></a-input>
        </a-form-item>
        <a-form-item label="任务类" :labelCol="labelCol" :wrapperCol="wrapperCol" hasFeedback>
          <a-input v-decorator="['jobClass', validatorRules.jobClass]" placeholder="请输入任务类"></a-input>
        </a-form-item>
        <a-form-item label="cron表达式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-cron v-decorator="['cronExpression', { initialValue: '* * * * * ? *' }]" @change="cronChange"></j-cron>
        </a-form-item>
        <a-form-item label="参数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['parameter', {}]" placeholder="请输入参数"></a-input>
        </a-form-item>
        <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['description', {}]" placeholder="请输入描述"></a-input>
        </a-form-item>
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
import { httpAction } from '@/api/manage'
import pick from 'lodash.pick'
import { validateDuplicateValue } from '@/utils/util'

export default {
  name: 'QuartzJobModal',
  components: {},
  data() {
    return {
      form: this.$form.createForm(this),
      title: '操作',
      width: 600,
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
        name: {
          rules: [{ required: true, message: '请输入任务名!' }],
        },
        jobClass: {
          rules: [{ required: true, message: '请输入任务类!' }],
        },
        cronExpression: {
          rules: [{ required: true, message: '请输入cron表达式!' }],
        },
      },
      url: {
        add: '/quartz/sysQuartzJob/add',
        edit: '/quartz/sysQuartzJob/edit',
      },
      cronValidateStatus: '',
    }
  },
  created() {},
  methods: {
    cronChange(data) {
      this.$nextTick(() => {
        this.model.cronExpression = data
      })

      // console.log(Object.keys(data).length==0);
      if (Object.keys(data).length == 0) {
        this.$message.warning('请输入cron表达式!')
      }
    },
    add() {
      this.edit({})
    },
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'name', 'jobClass', 'cronExpression', 'parameter', 'description'))
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
      this.form.setFieldsValue(pick(row, 'name', 'jobClass', 'cronExpression', 'parameter', 'description'))
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
</style>
