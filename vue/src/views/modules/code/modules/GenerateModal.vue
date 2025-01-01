<template>
  <a-drawer :title="title" :width="width" placement="right" :closable="false" @close="close" :visible="visible">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="数据库" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['tableSchema', validatorRules.tableSchema]"
            :disabled="true"
            placeholder="请输入库名"
          ></a-input>
        </a-form-item>
        <a-form-item label="表名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['tableName', validatorRules.tableName]"
            :disabled="true"
            placeholder="请输入表名"
          ></a-input>
        </a-form-item>
        <a-form-item label="包名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['module', validatorRules.module]" placeholder="请输入包名"></a-input>
        </a-form-item>
        <a-form-item label="实体名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['entityName', validatorRules.entityName]" placeholder="请输入实体名"></a-input>
        </a-form-item>
        <a-form-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['desc', validatorRules.desc]" placeholder="请输入描述"></a-input>
        </a-form-item>
      </a-form>
    </a-spin>

    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
import { getAction, postAction } from '@/api/manage'
import pick from 'lodash.pick'
import { validateDuplicateValue } from '@/utils/util'
export default {
  name: 'GenerateModal',
  components: {},
  data() {
    return {
      form: this.$form.createForm(this),
      title: '操作',
      width: 400,
      visible: false,
      confirmLoading: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      model: {},
      validatorRules: {
        tableSchema: {
          rules: [{ required: true, message: '请输入库名!' }],
        },
        tableName: {
          rules: [{ required: true, message: '请输入表名!' }],
        },
        module: {
          rules: [{ required: true, message: '请输入模块名!' }],
        },
        entityName: {
          rules: [{ required: true, message: '请输入实体名!' }],
        },
        desc: {
          rules: [{ required: true, message: '请输入描述!' }],
        },
      },
      action: '',
    }
  },
  methods: {
    edit(record, action) {
      this.action = action
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'tableSchema', 'tableName', 'module', 'entityName', 'desc'))
      })
    },
    handleOk() {
      if (!this.action) {
        this.$message.warning('action不能为空')
        return
      }
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let formData = Object.assign(this.model, values)
          that.$emit(this.action, formData)
          this.close()
          that.confirmLoading = false
        }
      })
    },
    handleCancel() {
      this.close()
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
  },
}
</script>
<style lang="less" scoped>
/** Button按钮间距 */
.ant-btn {
  margin-left: 10px;
  margin-bottom: 30px;
  float: right;
}
</style>
