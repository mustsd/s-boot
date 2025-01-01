<template>
  <a-drawer
    :title="record.name"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form>
      <a-form-item :label="lable">
        <a-tree
          show-icon
          checkable
          show-line
          v-if="treeData.length > 0"
          v-model="checkedKeys"
          :defaultExpandAll="true"
          :tree-data="treeData"
          :checkStrictly="checkStrictly"
          @select="showPermRoles"
          @check="onCheck"
        >
          <a-icon slot="menu" type="profile" theme="twoTone" />
          <a-icon slot="action" type="tag" theme="twoTone" />
        </a-tree>
      </a-form-item>
    </a-form>

    <div class="drawer-bootom-button">
      <a-dropdown style="float: left" :trigger="['click']" placement="topCenter">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="checkALL">全部勾选</a-menu-item>
          <a-menu-item key="2" @click="cancelCheckALL">取消全选</a-menu-item>
        </a-menu>
        <a-button> 树操作 <a-icon type="up" /> </a-button>
      </a-dropdown>
      <a-button type="primary" @click="handleCancel">取消</a-button>
      <a-button type="primary" @click="handleOk">确定</a-button>
    </div>
    <m-permission-role-modal ref="permRoleModal"></m-permission-role-modal>
  </a-drawer>
</template>

<script>
import { getAction, postAction } from '@/api/manage'
import MPermissionRoleModal from './MPermissionRoleModal'

export default {
  name: 'MRolePermissionModal',
  components: { MPermissionRoleModal },
  data() {
    return {
      width: 400,
      visible: false,
      url: {
        add: '/sys/rolePermission/add',
        grantPerm: '/sys/rolePermission/grantPerm',
        listTree: '/sys/permission/listRolePermissionTree',
      },
      record: {},
      treeData: [],
      checkedKeys: [],
      allPermKey: [],
      checkStrictly: true,
    }
  },
  computed: {
    lable() {
      return `角色权限 - (${this.checkedKeys.length}/${this.allPermKey.length})`
    },
  },
  created() {},
  methods: {
    showPermRoles([permId]) {
      if (!permId) {
        return
      }
      this.$refs.permRoleModal.visible = true
      this.$refs.permRoleModal.loadData(permId)
    },
    onCheck(checkedKeys) {
      console.log('onCheck', checkedKeys)
      this.checkedKeys = checkedKeys.checked
    },
    checkALL() {
      this.checkedKeys = this.allPermKey
    },
    cancelCheckALL() {
      this.checkedKeys = []
    },
    loadTree() {
      getAction(this.url.listTree, { roleId: this.record.id }).then((res) => {
        if (res.success) {
          this.treeData = res.result.treeData
          this.checkedKeys = res.result.checkedKey
          this.allPermKey = res.result.allPermKey
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    handleOk() {
      console.log(this.checkedKeys)
      postAction(this.url.grantPerm, { roleId: this.record.id, permIds: this.checkedKeys }).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.close()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleCancel() {
      this.close()
    },
  },
}
</script>

<style lang="less" scoped>
.drawer-bootom-button {
  position: absolute;
  bottom: 0;
  width: 100%;
  border-top: 1px solid #e8e8e8;
  padding: 10px 16px;
  text-align: right;
  left: 0;
  background: #fff;
  border-radius: 0 0 2px 2px;
}
.ant-btn {
  margin-right: 5px;
}
</style>
