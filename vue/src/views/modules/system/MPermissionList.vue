<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24"> </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" :disabled="false" v-auth="'addPermission'">新增</a-button>
      <a-button @click="loadData" type="primary" icon="reload">刷新</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :loading="loading"
        :pagination="false"
        @expand="handleExpand"
        :expandedRowKeys="expandedRowKeys"
        class="j-table-force-nowrap"
        :scroll="{ x: true }"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无图片</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt=""
            style="max-width: 80px; font-size: 12px; font-style: italic"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无文件</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a :disabled="false" v-auth="'editPermission'" @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm :title="`确定删除 ${record.name} 吗?`" @confirm="() => handleDelete(record.id)">
                  <a :disabled="false" v-auth="'delPermission'">删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>

    <mPermission-modal ref="modalForm" @ok="loadData"></mPermission-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import MPermissionModal from './modules/MPermissionModal__Style#Drawer'
import { getAction, deleteAction } from '@/api/manage'

export default {
  name: 'MPermissionList',
  mixins: [mixinDevice],
  components: {
    MPermissionModal,
  },
  data() {
    return {
      description: 'm_permission管理页面',
      // 表头
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
          title: '菜单名称',
          align: 'center',
          dataIndex: 'name',
        },
        {
          title: '菜单类型',
          align: 'center',
          dataIndex: 'menuType',
          customRender: function (text) {
            if (text == 1) {
              return <font style="color:blue"> 系统菜单</font>
            } else if (text == 2) {
              return <font style="color:red"> 按钮权限</font>
            } else {
              return text
            }
          },
        },
        {
          title: '图标',
          align: 'center',
          dataIndex: 'icon',
          customRender: function (text) {
            if (!text) {
              return null
            }
            let dynamicProps = { props: { type: text } }
            return <a-icon {...dynamicProps} />
          },
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
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '更新人',
          align: 'center',
          dataIndex: 'updateBy',
        },
        {
          title: '更新时间',
          align: 'center',
          dataIndex: 'updateTime',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/sys/permission/listTree',
        delete: '/sys/permission/delete',
        deleteBatch: '/sys/permission/deleteBatch',
        exportXlsUrl: '/sys/permission/exportXls',
        importExcelUrl: '/sys/permission/importExcel',
      },
      expandedRowKeys: [],
      loading: false,
      dataSource: [],
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    handleExpand(expanded, record) {
      if (expanded === true) {
        if (record.parentId === 'default') {
          this.expandedRowKeys = []
          this.expandedRowKeys.push(record.id)
        } else {
          this.expandedRowKeys.push(record.id)
        }
      } else {
        if (record.parentId === 'default') {
          this.expandedRowKeys = []
        } else {
          this.expandedRowKeys = this.expandedRowKeys.filter((key) => key != record.id)
        }
      }
    },
    loadData() {
      this.loading = true
      getAction(this.url.list, {}).then((res) => {
        if (res.success) {
          this.dataSource = res.result
        } else {
          this.$message.error(res.message)
        }
        this.loading = false
      })
    },
    handleAdd: function () {
      this.$refs.modalForm.add()
      this.$refs.modalForm.title = '新增'
    },
    handleEdit: function (record) {
      this.$refs.modalForm.edit(record)
      this.$refs.modalForm.title = '编辑'
    },
    handleDelete: function (id) {
      if (!this.url.delete) {
        this.$message.error('请设置url.delete属性!')
        return
      }
      var that = this
      deleteAction(that.url.delete, { id: id }).then((res) => {
        if (res.success) {
          that.$message.success(res.message)
          that.loadData()
        } else {
          that.$message.warning(res.message)
        }
      })
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>