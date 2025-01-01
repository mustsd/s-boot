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
        <a-form-item label="标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['title', validatorRules.title]" placeholder="请输入标题"></a-input>
        </a-form-item>
        <a-form-item label="内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!-- <a-textarea v-decorator="['content', validatorRules.content]" placeholder="请输入内容"></a-textarea> -->
          <wang-editor ref="weditor" v-decorator="['content', validatorRules.content]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>
import { httpAction } from '@/api/manage'
import pick from 'lodash.pick'
import { validateDuplicateValue } from '@/utils/util'
import WangEditor from '@/components/Editor/WangEditor'

export default {
  name: 'SysNoticeModal',
  components: { WangEditor },
  data() {
    return {
      form: this.$form.createForm(this),
      title: '操作',
      width: '60%',
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
        title: {
          rules: [{ required: true, message: '请输入标题!' }],
        },
        content: {
          rules: [{ required: true, message: '请输入内容!' }],
        },
      },
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
      this.$refs.weditor && this.$refs.weditor.clear()
    },
    edit(record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'title', 'content'))
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
