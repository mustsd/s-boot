<script>
import events from './events'

export default {
  name: 'MultiTab',
  inject: ['reload'],
  data() {
    return {
      fullPathList: [],
      pages: [],
      activeKey: '',
      newTabIndex: 0,
      reloading: false,
    }
  },
  created() {
    // bind event
    events
      .$on('open', (val) => {
        if (!val) {
          throw new Error(`multi-tab: open tab ${val} err`)
        }
        this.activeKey = val
      })
      .$on('close', (val) => {
        if (!val) {
          this.closeTab(this.activeKey)
          return
        }
        this.closeTab(val)
      })
      .$on('rename', ({ key, name }) => {
        console.log('rename', key, name)
        try {
          const item = this.pages.find((item) => item.path === key)
          item.meta.customTitle = name
          this.$forceUpdate()
        } catch (e) {}
      })

    this.pages.push(this.$route)
    this.fullPathList.push(this.$route.fullPath)
    this.selectedLastPath()
  },
  methods: {
    onEdit(targetKey, action) {
      this[action](targetKey)
    },
    remove(targetKey) {
      this.pages = this.pages.filter((page) => page.fullPath !== targetKey)
      this.fullPathList = this.fullPathList.filter((path) => path !== targetKey)
      // 判断当前标签是否关闭，若关闭则跳转到最后一个还存在的标签页
      if (!this.fullPathList.includes(this.activeKey)) {
        this.selectedLastPath()
      }
    },
    selectedLastPath() {
      this.activeKey = this.fullPathList[this.fullPathList.length - 1]
    },

    // content menu
    closeTab(e) {
      // 判断是否为最后一个标签页，如果是最后一个，则无法被关闭
      if (this.fullPathList.length > 1) {
        this.remove(e)
      } else {
        this.$message.info('这是最后一个标签了, 无法被关闭')
      }
    },
    closeLeft(e) {
      const currentIndex = this.fullPathList.indexOf(e)
      if (currentIndex > 0) {
        this.fullPathList.forEach((item, index) => {
          if (index < currentIndex) {
            this.remove(item)
          }
        })
      } else {
        this.$message.info('左侧没有标签')
      }
    },
    closeRight(e) {
      const currentIndex = this.fullPathList.indexOf(e)
      if (currentIndex < this.fullPathList.length - 1) {
        this.fullPathList.forEach((item, index) => {
          if (index > currentIndex) {
            this.remove(item)
          }
        })
      } else {
        this.$message.info('右侧没有标签')
      }
    },
    closeAll(e) {
      const currentIndex = this.fullPathList.indexOf(e)
      this.fullPathList.forEach((item, index) => {
        if (index !== currentIndex) {
          this.remove(item)
        }
      })
    },
    refrashTab(e) {
      this.reloading = true
      this.reload()
      setTimeout(() => (this.reloading = false), 500)
    },
    closeMenuClick(key, route) {
      this[key](route)
    },
    renderTabPaneMenu(e) {
      return (
        <a-menu
          {...{
            on: {
              click: ({ key, item, domEvent }) => {
                this.closeMenuClick(key, e)
              },
            },
          }}
        >
          <a-menu-item key="closeTab">关闭当前</a-menu-item>
          <a-menu-item key="closeRight">关闭右侧</a-menu-item>
          <a-menu-item key="closeLeft">关闭左侧</a-menu-item>
          <a-menu-item key="closeAll">关闭全部</a-menu-item>
          <a-menu-item key="refrashTab">刷新页面</a-menu-item>
        </a-menu>
      )
    },
    // render
    renderTabPane(title, keyPath) {
      const menu = this.renderTabPaneMenu(keyPath)
      return (
        <a-dropdown overlay={menu} trigger={['contextmenu']}>
          <span style={{ userSelect: 'none' }}>
            <a-icon
              type={this.reloading ? 'loading' : 'reload'}
              on={{ click: this.refrashTab }}
              class={['icon-reload', { hide: keyPath != this.activeKey }]}
            />
            <font style="font-weight: bold;">{title}</font>
          </span>
        </a-dropdown>
      )
    },
  },
  watch: {
    $route: function (newVal) {
      this.activeKey = newVal.fullPath
      if (this.fullPathList.indexOf(newVal.fullPath) < 0) {
        this.fullPathList.push(newVal.fullPath)
        this.pages.push(newVal)
      }
    },
    activeKey: function (newPathKey) {
      this.$router.push({ path: newPathKey })
    },
  },
  render() {
    const {
      onEdit,
      $data: { pages },
    } = this
    const panes = pages.map((page) => {
      return (
        <a-tab-pane
          style={{ height: 0 }}
          tab={this.renderTabPane(page.meta.customTitle || page.meta.title, page.fullPath)}
          key={page.fullPath}
          closable={pages.length > 1}
        />
      )
    })

    return (
      <div class="ant-pro-multi-tab">
        <div class="ant-pro-multi-tab-wrapper">
          <a-tabs
            hideAdd
            type={'editable-card'}
            v-model={this.activeKey}
            tabBarStyle={{ background: '#FFF', margin: 0, paddingLeft: '16px', paddingTop: '1px' }}
            {...{ on: { edit: onEdit } }}
          >
            {panes}
          </a-tabs>
        </div>
      </div>
    )
  },
}
</script> 
<style lang="less" scoped>
.icon-reload {
  margin-left: -4px;
  color: rgb(4, 52, 92);
  transition: all 0.3s ease-in-out;
  &:hover {
    color: rgb(41, 240, 51);
    font-size: 16px;
  }
  font-size: 14px;
  &.hide {
    font-size: 0;
  }
}
</style>
