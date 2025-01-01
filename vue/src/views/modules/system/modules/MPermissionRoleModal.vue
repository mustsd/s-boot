<template>
  <j-modal :title="title" :width="width" :visible="visible" @cancel="handleCancel" :footer="null">
    <a-table
      ref="table"
      size="small"
      bordered
      rowKey="id"
      :columns="columns"
      :dataSource="dataSource"
      :pagination="false"
      class="j-table-force-nowrap"
    ></a-table>
  </j-modal>
</template>

<script>
import { getAction } from '@/api/manage'

export default {
  name: 'MPermissionRoleModal',
  data() {
    return {
      title: '所属关联角色',
      width: 800,
      visible: false,
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        {
          title: '名称',
          align: 'center',
          dataIndex: 'name',
        },
        {
          title: '编码',
          align: 'center',
          dataIndex: 'code',
        },
        {
          title: '描述',
          align: 'center',
          dataIndex: 'description',
        },
        {
          title: '创建人',
          align: 'center',
          dataIndex: 'createBy',
        },
        {
          title: '创建时间',
          align: 'center',
          dataIndex: 'createTime',
        },
      ],
      url: {
        listRoleByPermission: '/sys/permission/listRoleByPermission',
      },
      dataSource: [],
    }
  },
  created() {},
  methods: {
    loadData(permId) {
      getAction(this.url.listRoleByPermission, { permissionId: permId }).then((res) => {
        if (res.success) {
          this.dataSource = res.result
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
