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
      <div>
        <h2>{{ article.title }}</h2>
        <h4 style="float: right; font-style: italic">
          <font style="color: green"> by ： {{ article.createBy }}</font>
          <font style="color: grey; margin-left: 10px">{{ article.createTime }}</font>
        </h4>
      </div>
      <p>{{ article.description ? article.description : '...' }}</p>
      <div class="render" v-html="article.render" />
      <div>
        <a-back-top />
      </div>
    </a-col>
  </a-row>
</template>

<script>
import { getAction, postAction, deleteAction } from '@/api/manage'

export default {
  name: 'ArticleView',
  components: {},
  data() {
    return {
      article: {},
      loading: false,
      treeData: [],
      selectedNode: null,
      url: {
        loadTree: '/sys/article/tree',
        queryById: '/sys/article/queryById'
      }
    }
  },
  created() {
    this.loadTree()
  },
  computed: {},
  methods: {
    onSelect(selectedKeys, info) {
      if (info.selected) {
        let node = new Object()
        node.key = info.node.dataRef.key
        node.title = info.node.dataRef.title
        node.description = info.node.dataRef.description
        node.hasChildren = info.node.dataRef.children.length > 0 ? true : false
        this.selectedNode = node
        this.articleSelected(node.key)
      } else {
        this.selectedNode = null
      }
    },
    loadTree() {
      this.loading = true
      postAction(this.url.loadTree, { personal: false }).then(res => {
        this.loading = false
        this.treeData = res.result
        console.log(res)
      })
    },
    articleSelected(id) {
      getAction(this.url.queryById, { id: id }).then(res => {
        if (res.success) {
          if (res.result) {
            this.article = res.result
            console.log(this.article.render)
          } else {
            this.article = {}
          }
        }
      })
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
.render {
  padding: 30px;
  border-width: 1px;
  height: 78vh;
  border-color: blue;
  border-style: dashed;
  overflow: auto;
}
.render >>> p {
  text-indent: 30px;
}
.render >>> li p {
  text-indent: 0;
}
.render >>> p img {
  width: 95%;
}
.render >>> video {
  width: 95%;
}
</style>
