<template>
    <el-container style="min-height: 100vh; ">
      <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); overflow-x: hidden; box-shadow:rgba(0,21,41,0.35) 2px 0 6px; ">
        <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden;"
                 background-color="rgb(48,65,86)"
                 text-color="#fff"
                 :collapse-transition="false"
                 :collapse="isCollapse"
        >
          <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/rainbow.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px; ">
            <b style="color: aliceblue" v-show="logoTextShow">后台管理系统</b>
          </div>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-message"></i>
              <span slot="title">导航一</span>
            </template>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </template>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title"><i class="el-icon-setting"></i>
              <span slot="title">导航三</span>
            </template>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>

        <el-header style=" font-size: 12px; border: 1px solid #ccc; line-height: 60px; display: flex">
          <div style="flex: 1; font-size: 20px">
            <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
          </div>
          <el-dropdown style="width: 70px; cursor: pointer">
            <span>God</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item>退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>

        <el-main>

          <div style="margin-bottom: 30px;">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{path: '/'}">首页</el-breadcrumb-item>
              <el-breadcrumb-item>用户管理</el-breadcrumb-item>
            </el-breadcrumb>
            </div>

          <div style="padding: 10px 0">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="username">
            </el-input>

            <el-input style="width: 200px;margin-left: 30px" placeholder="请输入电话" suffix-icon="el-icon-search" v-model="phone">
            </el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
          </div>
          <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确定'
                cancel-button-text='取消'
                icon="el-icon-info"
                icon-color="red"
                title="您确定要删除吗？"
                @confirm="delBatch"
            >
            <el-button type="primary" slot="reference" >批量删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
            <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i> </el-button>
            <el-button type="primary" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
          </div>
          <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="username" label="姓名" width="140" align="center"></el-table-column>
            <el-table-column prop="nickname" label="昵称" width="120" align="center"></el-table-column>
            <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
            <el-table-column prop="phone" label="电话" align="center"></el-table-column>
            <el-table-column prop="address" label="地址" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button type="success" @click="updateUser(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                <el-popconfirm

                    confirm-button-text='确定'
                    cancel-button-text='取消'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您确定要删除吗？"
                    @confirm="delUser(scope.row.id)"
                >
                <el-button class="ml-5" type="danger" slot="reference">删除 <i class="el-icon-delete"></i></el-button>
                </el-popconfirm
                    >
              </template>

            </el-table-column>
          </el-table>
          <div class="block" style="padding: 10px 0">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.sync="pageNum"
                :page-sizes="[5, 10, 15, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
          </div>

          <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px" size="small">
              <el-form-item label="用户名">
                <el-input v-model="form.username" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="昵称">
                <el-input v-model="form.nickname" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="form.email" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="电话">
                <el-input v-model="form.phone" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="地址">
                <el-input v-model="form.address" autocomplete="off"></el-input>
              </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="saveUser">确 定</el-button>
            </div>
          </el-dialog>
        </el-main>
      </el-container>
    </el-container>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import request from "@/utils/request";

export default {
  name: 'HomeView',
  data() {
    return {
      tableData: [],
      total:0,
      username:"",
      phone:"",
      nickname:"",
      pageNum:1,
      pageSize:10,
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg',
      dialogFormVisible: false,
      form: {},
      multipleSelection: []
    }
  },
  created() {
    //请求分页查询数据
    this.load()
  },
  methods: {
    collapse() { //点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if(this.isCollapse) { //收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },

    reset() {
      this.username = ""
      this.phone = ""
      this.load()
    },

    load() {
      request.get("/user/page", {
        params : {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          phone: this.phone
        }
      }).then(res => {
            this.tableData = res.records
            this.total = res.total
          })
    },
    saveUser() {
      request.post("/user/save",this.form).then(res => {
        if(res) {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    updateUser(scope) {
      this.form = scope
      this.dialogFormVisible = true
    },
    delUser(id) {
      request.delete("/user/del/" + id).then(res =>{
        if(res) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val;
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)
      request.post("/user/delBatch/",ids).then(res =>{
        if(res) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },

    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    }
  }
}
</script>


<style>
.headerBg {
  background: #dedede !important;
}
</style>
