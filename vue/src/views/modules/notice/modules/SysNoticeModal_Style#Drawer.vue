<template>
  <a-drawer :title="title" :width="width" placement="right" :closable="false" @close="close" :visible="visible">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['title', {}]" placeholder="请输入标题"></a-input>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['status', {}]" placeholder="请输入状态"></a-input>
        </a-form-item>
        <a-form-item label="内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['content', {}]" placeholder="请输入内容"></a-input>
        </a-form-item>
        <a-form-item label="发布人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sendBy', {}]" placeholder="请输入发布人"></a-input>
        </a-form-item>
        <a-form-item label="发布时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['sendTime', {}]" placeholder="请输入发布时间"></a-input>
        </a-form-item>
        <a-form-item label="撤销人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['cancelBy', {}]" placeholder="请输入撤销人"></a-input>
        </a-form-item>
        <a-form-item label="撤销时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['cancelTime', {}]" placeholder="请输入撤销时间"></a-input>
        </a-form-item>
        <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['createBy', {}]" placeholder="请输入创建人"></a-input>
        </a-form-item>
        <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['createTime', {}]" placeholder="请输入创建时间"></a-input>
        </a-form-item>
        <a-form-item label="更新人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['updateBy', {}]" placeholder="请输入更新人"></a-input>
        </a-form-item>
        <a-form-item label="更新时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['updateTime', {}]" placeholder="请输入更新时间"></a-input>
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
  name: 'SysNoticeModal',
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
      validatorRules: {},
      url: {
        add: '/notice/sysNotice/add',
        edit: '/notice/sysNotice/edit',
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
            'title',
            'status',
            'content',
            'sendBy',
            'sendTime',
            'cancelBy',
            'cancelTime',
            'createBy',
            'createTime',
            'updateBy',
            'updateTime'
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
        pick(
          row,
          'title',
          'status',
          'content',
          'sendBy',
          'sendTime',
          'cancelBy',
          'cancelTime',
          'createBy',
          'createTime',
          'updateBy',
          'updateTime'
        )
      )
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
