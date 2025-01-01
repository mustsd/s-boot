<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="字典名称">
              <a-input placeholder="请输入字典名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="字典编码">
              <a-input placeholder="请输入字典编码" v-model="queryParam.code"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'" />
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button @click="cacheRefresh" type="primary" icon="refresh">缓存刷新</a-button>
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('字典')">导出</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete" />删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        class="j-table-force-nowrap"
        @change="handleTableChange"
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
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="showItem(record)">配置</a>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>

    <mDict-modal ref="modalForm" @ok="modalFormOk"></mDict-modal>
    <mDictItem-list-modal ref="mDictItemListModal" @showItemModal="handleItem"></mDictItem-list-modal>
    <mDictItem-modal ref="mDictItemModal" @ok="reloadItemList"></mDictItem-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { BaseQueryMixin } from '@/mixins/BaseQueryMixin'
import MDictModal from './modules/MDictModal__Style#Drawer'
import MDictItemModal from './modules/MDictItemModal'
import MDictItemListModal from './modules/MDictItemModal__Style#Drawer'
import { getAction } from '@/api/manage'

export default {
  name: 'MDictList',
  mixins: [BaseQueryMixin, mixinDevice],
  components: {
    MDictModal,
    MDictItemListModal,
    MDictItemModal,
  },
  data() {
    return {
      description: '字典管理页面',
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
          title: '字典名称',
          align: 'center',
          dataIndex: 'name',
        },
        {
          title: '字典编码',
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
          // fixed:"right",
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/sys/dict/list',
        delete: '/sys/dict/delete',
        deleteBatch: '/sys/dict/deleteBatch',
        exportXlsUrl: '/sys/dict/exportXls',
        importExcelUrl: '/sys/dict/importExcel',
        cacheRefresh: '/sys/dict/cacheRefresh',
      },
      dictOptions: {},
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
    cacheRefresh() {
      getAction(this.url.cacheRefresh, null).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    reloadItemList() {
      this.$refs.mDictItemListModal.loadData()
    },
    handleItem(dict) {
      this.$refs.mDictItemModal.title = '保存'
      this.$refs.mDictItemModal.edit(dict)
    },
    showItem(record) {
      this.$refs.mDictItemListModal.visible = true
      this.$refs.mDictItemListModal.dict = record
      this.$refs.mDictItemListModal.loadData()
    },
    initDictConfig() {},
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>