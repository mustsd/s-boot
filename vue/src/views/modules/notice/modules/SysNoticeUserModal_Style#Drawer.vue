<template>
  <a-drawer :title="title" :width="width" placement="right" :closable="false" @close="close" :visible="visible">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="通知id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['noticeId',{}]"
            placeholder="请输入通知id"
          ></a-input>
        </a-form-item>
        <a-form-item label="用户id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['userId',{}]"
            placeholder="请输入用户id"
          ></a-input>
        </a-form-item>
        <a-form-item label="阅读状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['readFlag',{}]"
            placeholder="请输入阅读状态"
          ></a-input>
        </a-form-item>
        <a-form-item label="阅读时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['readTime',{}]"
            placeholder="请输入阅读时间"
          ></a-input>
        </a-form-item>
        <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['createBy',{}]"
            placeholder="请输入创建人"
          ></a-input>
        </a-form-item>
        <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['createTime',{}]"
            placeholder="请输入创建时间"
          ></a-input>
        </a-form-item>
        <a-form-item label="更新人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['updateBy',{}]"
            placeholder="请输入更新人"
          ></a-input>
        </a-form-item>
        <a-form-item label="更新时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            v-decorator="['updateTime',{}]"
            placeholder="请输入更新时间"
          ></a-input>
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
        name: 'SysNoticeUserModal',
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
        },
            url: {
                add: '/notice/sysNoticeUser/add',
                    edit: '/notice/sysNoticeUser/edit',
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
                        'noticeId',
                        'userId',
                        'readFlag',
                        'readTime',
                        'createBy',
                        'createTime',
                        'updateBy',
                        'updateTime',
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
                        'noticeId',
                        'userId',
                        'readFlag',
                        'readTime',
                        'createBy',
                        'createTime',
                        'updateBy',
                        'updateTime',
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
