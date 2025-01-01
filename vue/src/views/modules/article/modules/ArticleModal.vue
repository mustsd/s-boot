<template>
  <a-modal
    :title="title"
    :zIndex="99999"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上级目录">
          <div>{{ selectedNode ? selectedNode.title : '根节点' }}</div>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="文章标题">
          <a-input placeholder="请输入文章标题" v-decorator="['title', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="文章描述">
          <a-input placeholder="请输入文章描述" v-decorator="['description', {}]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="是否私有">
          <a-switch checkedChildren="是" unCheckedChildren="否" v-decorator="['personal', {}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { httpAction } from '@/api/manage'
import pick from 'lodash.pick'
import moment from 'moment'

export default {
  name: 'ArticleModal',
  props: {
    selectedNode: Object,
  },
  data() {
    return {
      title: '操作',
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
      form: this.$form.createForm(this),
      validatorRules: {},
      url: {
        add: '/sys/article/add',
        edit: '/sys/article/edit',
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
      // this.open = record.personal?false:true;
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'parentId', 'title', 'description', 'personal'))
        //时间格式化
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
          // that.model.personal = !that.open;
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          let formData = Object.assign(this.model, values)
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
            if (this.selectedNode) {
              formData.parentId = this.selectedNode.key
            } else {
              formData.parentId = null
            }
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          //时间格式化
          console.log(formData)
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
  },
}
</script>

<style lang="less" scoped></style>
