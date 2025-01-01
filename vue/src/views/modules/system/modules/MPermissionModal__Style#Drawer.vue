<template>
  <a-drawer :title="title" :width="width" placement="right" :closable="false" @close="close" :visible="visible">
    <div :style="{ width: '100%', border: '1px solid #e9e9e9', padding: '10px 16px', background: '#fff' }">
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-form-item label="菜单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-dict-select-tag
              v-decorator="['menuType', validatorRules.menuType]"
              :triggerChange="true"
              placeholder="菜单类型"
              dictCode="menuType"
              @change="menuTypeChange"
              :disabled="disabled"
            />
          </a-form-item>
          <a-form-item label="菜单名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入菜单名称"></a-input>
          </a-form-item>
          <a-form-item label="上级菜单" :labelCol="labelCol" :wrapperCol="wrapperCol"
            ><a-tree-select
              v-decorator="['parentId', validatorRules.parentId]"
              tree-data-simple-mode
              :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
              :tree-data="treeData"
              placeholder="Please select"
            ></a-tree-select>
          </a-form-item>
          <a-form-item label="菜单序号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number v-decorator="['sortNo']" placeholder="请输入菜单排序" />
          </a-form-item>
          <div v-if="menuType == '1'">
            <a-form-item label="菜单图标" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="点击选择图标" v-decorator="['icon']">
                <a-icon slot="addonAfter" type="setting" @click="selectIcons" />
              </a-input>
            </a-form-item>
            <a-form-item label="菜单路径" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['path']" placeholder="请输入菜单路径"></a-input>
            </a-form-item>
            <a-form-item label="菜单组件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['component']" placeholder="请输入组件"></a-input>
            </a-form-item>
            <a-form-item label="开启缓存" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-switch
                v-decorator="['keepAlive', { initialValue: true, valuePropName: 'checked' }]"
                checked-children="是"
                un-checked-children="否"
              />
            </a-form-item>
            <a-form-item label="隐藏菜单" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-switch
                v-decorator="['hidden', { initialValue: false, valuePropName: 'checked' }]"
                checked-children="是"
                un-checked-children="否"
              />
            </a-form-item>
            <a-form-item label="外部打开" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-switch
                v-decorator="['external', { initialValue: false, valuePropName: 'checked' }]"
                checked-children="是"
                un-checked-children="否"
              />
            </a-form-item>
          </div>
          <div v-else-if="menuType == '2'">
            <a-form-item label="授权标识" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['btnCode', validatorRules.btnCode]" placeholder="请输入授权标识"></a-input>
            </a-form-item>
            <a-form-item label="授权策略" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag
                v-decorator="['btnAction', validatorRules.btnAction]"
                :triggerChange="true"
                placeholder="请选择授权策略"
                dictCode="btnAction"
              />
            </a-form-item>
            <a-form-item label="按钮状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-switch
                v-decorator="['btnActive', { initialValue: true, valuePropName: 'checked' }]"
                checked-children="启用"
                un-checked-children="禁用"
              />
            </a-form-item>
          </div>
          <a-form-item label="功能描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="['description']" placeholder="请输入描述"></a-input>
          </a-form-item>
        </a-form>
      </a-spin>

      <!-- 选择图标 -->
      <icons @choose="handleIconChoose" @close="handleIconCancel" :iconChooseVisible="iconChooseVisible"></icons>
    </div>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>
import { httpAction, getAction } from '@/api/manage'
import pick from 'lodash.pick'
import { validateDuplicateValue } from '@/utils/util'
import Icons from '@/components/icon/Icons'

export default {
  name: 'MPermissionModal',
  components: {
    Icons,
  },
  data() {
    return {
      form: this.$form.createForm(this),
      title: '操作',
      width: 450,
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
        menuType: {
          rules: [{ required: true, message: '请选择菜单类型!' }],
        },
        parentId: {
          rules: [{ required: true, message: '请选择上级菜单!' }],
        },
        name: {
          rules: [{ required: true, message: '请输入菜单名称!' }],
        },
        btnCode: {
          rules: [{ required: true, message: '请输入授权标识!' }],
        },
        btnAction: {
          rules: [{ required: true, message: '请选择授权策略!' }],
        },
      },
      url: {
        add: '/sys/permission/add',
        edit: '/sys/permission/edit',
        listOptions: '/sys/permission/listOptions',
      },
      treeData: [],
      iconChooseVisible: false,
      menuType: '',
      disabled: false,
    }
  },
  created() {
    this.loadOptions()
  },
  methods: {
    menuTypeChange(key) {
      if (key) {
        this.menuType = key
      }
    },
    selectIcons() {
      this.iconChooseVisible = true
    },
    handleIconCancel() {
      this.iconChooseVisible = false
    },
    handleIconChoose(value) {
      // this.model.icon = value
      // this.form.icon = value
      this.$nextTick(() => {
        this.form.setFieldsValue({ icon: value })
      })
      this.iconChooseVisible = false
    },
    loadOptions() {
      getAction(this.url.listOptions, null).then((res) => {
        if (res.success) {
          this.treeData = res.result
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    add() {
      this.edit({})
      this.disabled = false
    },
    edit(record) {
      console.log(record)
      this.disabled = true
      this.menuType = record.menuType + ''
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(
          pick(
            this.model,
            'parentId',
            'name',
            'path',
            'component',
            'icon',
            'sortNo',
            'menuType',
            'btnCode',
            'btnAction',
            'btnActive',
            'keepAlive',
            'hidden',
            'external',
            'description'
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
          'parentId',
          'name',
          'path',
          'component',
          'icon',
          'sortNo',
          'menuType',
          'btnCode',
          'btnAction',
          'btnActive',
          'keepAlive',
          'hidden',
          'external',
          'description'
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