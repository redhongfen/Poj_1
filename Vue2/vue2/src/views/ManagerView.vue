<template>
  <div>
    <el-container>
      <el-aside :width="asideWidth">
        <div style="height: 60px; line-height: 60px; font-size: 20px; display: flex; align-items: center; justify-content: center">
          <img src="../assets/logo.png" style="width: 30px;" alt="">
          <span class="logo-title" v-show="!isCollapse">Honey2024</span>
        </div>
        <el-menu router :collapse="isCollapse" :collapse-transition="false" background-color="#001529"
                 active-text-color="#fff" text-color="rgba(255, 255, 255, 0.65)" :default-active="$route.path"
                 style="border: none">
          <el-menu-item index="/home">
            <i class="el-icon-s-home"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-submenu index="info" v-if="user.role==='管理员'">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/user">用户信息</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="height: 60px; line-height: 60px; display: flex; align-items: center; box-shadow: 2px 0 6px rgba(0, 21, 41, .35);">
          <i :class="collapseIcon" @click="handleCollapse" style="font-size: 26px"></i>
          <el-breadcrumb separator="/" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: $route.path }">{{$route.meta.name}}</el-breadcrumb-item>
          </el-breadcrumb>

          <div style="flex: 1; display: flex; justify-content: flex-end; align-items: center">
            <i class="el-icon-quanping" @click="handleFullScreen" style="font-size: 25px"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: pointer">
                <img :src="user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'" class="avatar" style="width: 40px; height: 40px; margin: 0 5px;border-radius: 50%;" />
                <span>{{user.name}}</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="$router.push('/preson')">个人信息</el-dropdown-item>
                <el-dropdown-item @click.native="$router.push('/password')">修改密码</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>  
            </el-dropdown>
          </div>
        </el-header>

        <el-main>
          <router-view @update:user="updateUser"/>
          <!-- <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
            早安，骚年，祝你开心每一天！
          </div>
          <div style="display: flex;">
          <el-card style="width: 30%;margin-right: 10px;">
              <div slot="header" class="clearfix">
                <span>青哥哥带你做毕设2024</span>
              </div>
              <div>
                2024毕设正式开始了！青哥哥带你手把手敲出来！
                <div style="margin-top: 20px">
                  <div style="margin: 10px 0"><strong>主题色</strong></div>
                  <el-button type="primary">按钮</el-button>
                  <el-button type="success">按钮</el-button>
                  <el-button type="warning">按钮</el-button>
                  <el-button type="danger">按钮</el-button>
                  <el-button type="info">按钮</el-button>
                </div>
              </div>
            </el-card>
            <el-card style="width: 70%">
              <div slot="header" class="clearfix">
                <span>用户数据</span>
              </div>
              <div class="">
                <el-table :data="users">
                  <el-table-column label="ID" prop="id"></el-table-column>
                  <el-table-column label="用户名" prop="username"></el-table-column>
                  <el-table-column label="姓名" prop="name"></el-table-column>
                  <el-table-column label="地址" prop="address"></el-table-column>
                  <el-table-column label="文件上传">
                    <template v-slot="scope">
                      <el-upload
                        action="http://localhost:8081/file/upload"
                        :headers="{token:user.token}"
                        :show-flie-list="false"
                        :on-success="(row,file,fileList)=>handleTableFileUpload(scope.row,file,fileList)"
                        >
                        <el-button size="mini" type="primary">点击上传</el-button>
                      </el-upload>
                    </template>
                  </el-table-column>
                  <el-table-column table="文件上传">
                    <template v-slot="scope">
                      <el-image v-if="scope.row.avatar" :src="scope.row.avatar" style="width: 50px;height: 50px;"></el-image>
                      <div><el-button @click="preview(scope.row.avatar)">预览</el-button></div>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
            </el-card>
          </div>
          <div style="display: flex;margin: 10px 0;">
            <el-card style="width: 70%;margin-right: 10px;">
              <div slot="header" class="clearfix">
                <span>文件上传下载</span>
              </div>
              <div style="margin: 10px 0;">
                <el-upload
                    action="http://localhost:8081/file/upload"
                    :headers="{token: user.token}"
                    :on-success="handleFileUpload"
                  >
                  <el-button size="mini" type="success">单文件上传</el-button>
                </el-upload>
              </div>
              <div style="margin: 10px 0;">
                <el-upload
                    action="http://localhost:8081/file/upload"
                    :headers="{token: user.token}"
                    :on-success="handleMultipleFileUpload"
                    multiple
                  >
                  <el-button size="mini" type="success">多文件上传</el-button>
                </el-upload>
                <el-button type="primary" style="margin:  10px 0;" @click="showUrls">显示上传文件</el-button>
              </div>
            </el-card>
          </div> -->
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>

<script>
// import router from '@/router';
import request from '../utils/request';


export default {
  name: 'HomeView',
  data() {
    return {
      isCollapse: false,
      asideWidth: '200px',
      collapseIcon: 'el-icon-s-fold',
      users:[],
      user:JSON.parse(localStorage.getItem('honey-user')||'{}'),
      url:'',
      urls:[]
    }
  },
  mounted() {
    if (!this.user.id) {   // 当前的浏览器没有用户信息
    this.$router.push('/login')
    }

    request.get('/user/selectAll').then(res =>{
      this.users=res.data
    })
  },
  methods: {
    updateUser(user){
      this.user=JSON.parse(JSON.stringify(user))
    },
    preview(url){
      window.open(url)
    },
    showUrls(){
      console.log(this.urls)
    },
    handleMultipleFileUpload(response,file,fileList){
      // console.log(response, file, fileList)
      this.urls = fileList.map(v => v.response?.data || '');
    },
    handleTableFileUpload(row, file, fileList) {
      console.log(row, file, fileList)
      row.avatar = file.response.data
      // this.$set(row, 'avatar', file.response.data)
      console.log(row)
      // 触发更新就可以了
      request.put('/user/update', row).then(res => {
        if (res.code === '200') {
          this.$message.success('上传成功')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleFileUpload(respons,file,fileList){
      this.fileList=fileList

    },
    handleFullScreen() {
      document.documentElement.requestFullscreen()
    },
    handleCollapse() {
      this.isCollapse = !this.isCollapse
      this.asideWidth = this.isCollapse ? '64px' : '200px'
      this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'
    },
    logout(){
      localStorage.removeItem('honey-user')
      this.$router.push('/login')
    }
  }
}
</script>

<style>
.el-menu--inline, .el-menu-item {
  background-color: #000c17 !important;
}

.el-submenu__title {
  height: 40px !important;
  line-height: 40px !important;
  padding: 0 25px !important;
  transition: color 0s;
}
.el-menu--collapse .el-submenu__title {
  padding: 0 20px !important;
}
.el-submenu__title>i:nth-child(1) {
  margin-top: 2px;
}
.el-submenu__title>i.el-submenu__icon-arrow {
  margin-top: -5px;
}
.el-menu-item {
  min-width: 0 !important;
  width: calc(100% - 10px);
  margin: 5px;
  height: 40px !important;
  line-height: 40px !important;
  border-radius: 5px;
}
.el-menu--inline>.el-menu-item {
  padding-left: 50px !important;
}
.el-menu-item.is-active {
  background-color: dodgerblue !important;
}

.el-menu-item:hover {
  color: #fff !important;
}

.el-submenu__title:hover *, .el-submenu__title:hover {
  color: #fff !important;
}

.el-aside {
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
  background-color: #001529;
  color: #fff;
  min-height: 100vh;
  transition: width .3s;
}
.el-menu--collapse .el-tooltip {
  padding: 0 15px !important;
}
.logo-title {
  margin-left: 5px;
  transition: all .3s;
}
.el-menu {
  transition: all .3s;
}
</style>