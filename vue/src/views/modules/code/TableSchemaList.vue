<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="数据库">
              <a-input placeholder="请输入数据库名称" v-model="queryParam.tableSchema"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="库表">
              <a-input placeholder="请输入表名称" v-model="queryParam.tableName"></a-input>
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
        rowKey="tableName"
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
          <a @click="showColumn(record)">字段</a>
          <a-divider type="vertical" />
          <a @click="showPreview(record)">预览</a>
          <a-divider type="vertical" />
          <a @click="showZip(record)">下载</a>
        </span>
      </a-table>
    </div>
    <!-- 字段预览 -->
    <tableColumnList-modal ref="columnListModal" @ok="modalFormOk"></tableColumnList-modal>
    <!-- 代码生成 -->
    <generate-modal ref="generateModal" @zip="downZip" @preview="preview"></generate-modal>
    <!-- 代码预览 -->
    <code-preview-modal ref="codePreviewModal"></code-preview-modal>
  </a-card>
</template>
<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { BaseQueryMixin } from '@/mixins/BaseQueryMixin'
import TableColumnListModal from './modules/TableColumnListModal'
import GenerateModal from './modules/GenerateModal'
import CodePreviewModal from './modules/CodePreviewModal'
import { downloadFile } from '@/api/manage'

export default {
  name: 'TableSchemaList',
  mixins: [BaseQueryMixin, mixinDevice],
  components: {
    TableColumnListModal,
    GenerateModal,
    CodePreviewModal,
  },
  data() {
    return {
      description: '代码生成管理页面',
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
          title: '数据库名称',
          align: 'center',
          dataIndex: 'tableSchema',
        },
        {
          title: '表名',
          align: 'center',
          dataIndex: 'tableName',
        },
        {
          title: '描述',
          align: 'center',
          dataIndex: 'tableComment',
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
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/code/list',
        genZip: '/code/genZip',
      },
      dictOptions: {},
      disableMixinCreated: true,
    }
  },
  methods: {
    downZip(record) {
      downloadFile(this.url.genZip, record)
    },
    preview(record) {
      this.$refs.codePreviewModal.visible = true
      this.$refs.codePreviewModal.loadData(record)
    },
    showPreview(record) {
      this.$refs.generateModal.title = `预览 · <${record.tableName}>`
      this.$refs.generateModal.visible = true
      this.$refs.generateModal.edit(record, 'preview')
    },
    showZip(record) {
      this.$refs.generateModal.title = `下载 · <${record.tableName}>`
      this.$refs.generateModal.visible = true
      this.$refs.generateModal.edit(record, 'zip')
    },
    showColumn(record) {
      this.$refs.columnListModal.visible = true
      this.$refs.columnListModal.title = `${record.tableSchema} · ${record.tableName}`
      this.$refs.columnListModal.loadData(record)
    },
    searchReset() {
      this.queryParam = {}
      this.queryParam.tableSchema = 'ai-stock'
      this.loadData(1)
    },
    initDictConfig() {},
  },
  created() {
    //默认查询manage库
    this.queryParam.tableSchema = 'ai-stock'
    this.loadData()
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>
