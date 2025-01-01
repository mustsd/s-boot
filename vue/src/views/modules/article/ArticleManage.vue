<template>
  <a-row type="flex" :gutter="16">
    <a-col :md="5" :sm="24">
      <a-card :bordered="false" style="height: 100%; overflow: auto">
        <a-spin :spinning="loading">
          <a-tree
            :show-line="true"
            v-if="treeData.length > 0"
            :defaultExpandAll="true"
            :treeData="treeData"
            @select="onSelect"
          />
        </a-spin>
      </a-card>
    </a-col>
    <a-col :md="24 - 5" :sm="24">
      <!-- 操作按钮区域 -->
      <div class="table-operator">
        <a-button type="primary" icon="plus" @click="handleAdd">新增</a-button>
        <a-button type="primary" icon="edit" @click="handleEdit">修改</a-button>
        <a-button type="primary" icon="delete" @click="handleDel">删除</a-button>
      </div>
      <div class="padding-editor">
        <md-editor ref="mdEditor" :mdText="mdText" @save="saveArticle" />
        <div>
          <a-back-top />
        </div>
      </div>
    </a-col>
    <!-- 表单区域 -->
    <article-modal ref="modalForm" @ok="modalFormOk" :selectedNode="selectedNode"></article-modal>
  </a-row>
</template>

<script>
import { getAction, postAction, putAction, deleteAction } from '@/api/manage'
import ArticleModal from './modules/ArticleModal'
import MdEditor from '@/components/Editor/MdEditor'

export default {
  name: 'ArticleManage',
  components: {
    ArticleModal,
    MdEditor
  },
  data() {
    return {
      mdText: '',
      loading: false,
      treeData: [],
      selectedNode: null,
      url: {
        loadTree: '/sys/article/tree',
        del: '/sys/article/delete',
        queryById: '/sys/article/queryById',
        editArticle: '/sys/article/edit'
      }
    }
  },
  created() {
    this.loadTree()
  },
  computed: {},
  methods: {
    saveArticle(obj) {
      if (!(this.selectedNode && this.selectedNode.key)) {
        return
      }
      let article = new Object()
      article.id = this.selectedNode.key
      article.content = obj.text
      article.render = obj.html
      putAction(this.url.editArticle, article).then(res => {
        if (res.success) {
          this.$message.success('保存成功')
        } else {
          this.$message.error('保存失败')
        }
      })
    },
    loadArticle(id) {
      getAction(this.url.queryById, { id: id }).then(res => {
        if (res.success) {
          if (res.result.content) {
            this.mdText = res.result.content
          } else {
            this.value = ''
          }
        }
      })
    },
    onSelect(selectedKeys, info) {
      if (info.selected) {
        let node = new Object()
        node.key = info.node.dataRef.key
        node.title = info.node.dataRef.title
        node.description = info.node.dataRef.description
        node.personal = info.node.dataRef.personal
        node.hasChildren = info.node.dataRef.children.length > 0 ? true : false
        this.selectedNode = node
        this.loadArticle(node.key)
      } else {
        this.selectedNode = null
      }
    },
    loadTree() {
      this.loading = true
      postAction(this.url.loadTree, { personal: false }).then(res => {
        this.loading = false
        this.treeData = res.result
      })
    },
    handleAdd: function() {
      this.$refs.modalForm.add()
      this.$refs.modalForm.title = '新增'
      this.$refs.modalForm.disableSubmit = false
    },
    handleEdit() {
      let record = new Object()
      record.title = this.selectedNode.title
      record.id = this.selectedNode.key
      record.description = this.selectedNode.description
      record.personal = this.selectedNode.personal
      this.$refs.modalForm.edit(record)
      this.$refs.modalForm.title = '修改'
      this.$refs.modalForm.disableSubmit = false
    },
    handleDel() {
      if (!this.selectedNode) {
        this.$message.error('请选中节点')
        return
      }
      if (this.selectedNode.hasChildren) {
        this.$message.error('请先删除子节点')
        return
      }
      let that = this
      this.$confirm({
        title: '删除确认',
        zIndex: 1501,
        content: `确定删除 ${this.selectedNode.title} 么?`,
        okText: '确定',
        okType: 'danger',
        cancelText: '取消',
        onOk() {
          that.articleDel()
        },
        onCancel() {
          console.log('Cancel')
        }
      })
    },
    articleDel() {
      this.loading = true
      deleteAction(this.url.del, { id: this.selectedNode.key }).then(res => {
        this.loading = false
        this.loadTree()
        this.selectedNode = null
      })
    },
    modalFormOk() {
      // 新增/修改 成功时，重载列表
      this.loadTree()
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
.padding-editor {
  padding-top: 10px;
}
</style>
