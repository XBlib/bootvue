<template>
  <div>

    <div style="margin: 10px 0">
      <el-popconfirm
          class="mr-5"
          confirm-button-text='确定'
          cancel-button-text='取消'
          icon="el-icon-info"
          icon-color="red"
          title="您确定要删除吗？"
          @confirm="delBatch"
      >
        <el-button type="primary" slot="reference" >批量删除 <i class="el-icon-remove-outline"></i></el-button>

      </el-popconfirm>
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>

        <el-input class="ml-5" style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="roleName">
        </el-input>

        <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
        <el-button class="mr-5" type="warning" @click="reset">重置</el-button>
    </div>
    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="roleName" label="姓名" align="center"></el-table-column>
      <el-table-column prop="description" label="描述"  align="center"></el-table-column>
      <el-table-column label="操作" align="center" >
        <template slot-scope="scope">
          <el-button @click="selectMenu(scope.row.id)" class="ml-5" type="info" slot="reference">分配菜单 <i class="el-icon-menu"></i></el-button>
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
          :page-sizes="[5, 10, 15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.roleName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveUser">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="菜单分配" :visible.sync="menuDialogVisible" width="30%">
      <el-tree
          :props="props"
          :data="menuData"
          ref="tree"
          show-checkbox
          node-key="id"
          :default-checked-keys="checks"
          :default-expanded-keys="expends"


      >
        <span class="custom-tree-node" slot-scope="{node, data }">
          <span><i :class="data.icon"></i>> {{data.menuName}}</span>
        </span>

      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveMenu">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: "Role",

  data() {
    return {
      tableData: [],
      total:0,
      roleName:"",
      pageNum:1,
      pageSize:10,
      dialogFormVisible: false,
      headerBg: 'headerBg',
      form: {},
      menuDialogVisible: false,
      multipleSelection: [],
      menuData:[],
      expends:[],
      checks:[],
      roleId:0,
      props: {
        label: 'menuName'
      }
    }
  },
  created() {
    //请求分页查询数据
    this.load()
  },
  components: {
    Aside,
    Header
  },

  methods: {
    reset() {
      this.roleName = ""
      this.description = ""
      this.load()
    },

    load() {
      this.request.get("/role/page", {
        params : {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          roleName: this.roleName,
        }
      }).then(res => {
        if(res.data){

          this.tableData = res.data.records
          this.total = res.data.total
        }

      })




    },
    saveUser() {
      this.request.post("/role/save",this.form).then(res => {
        if(res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    async saveMenu() {

      await this.request.post("/role/roleMenu/" + this.roleId,this.$refs.tree.getCheckedKeys())
          .then(res => {
          if(res.code === '200') {
            this.$message.success('绑定成功')
            this.menuDialogVisible = false;
          } else {
            this.$message.error('绑定失败')
          }
      })
      this.request.get("/role/roleMenu/"+ this.roleId).then(res => {
        if(res.data){
          this.checks = res.data;
          console.log(this.checks)

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
      this.request.delete("/role/del/" + id).then(res =>{
        if(res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {

      this.multipleSelection = val;
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)
      this.request.post("/role/delBatch/",ids).then(res =>{
        if(res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    selectMenu(roleId) {
      this.menuDialogVisible = true
      this.roleId = roleId
      this.checks = []

      this.request.get("/menu").then(res => {
        if(res.data){
          this.menuData = res.data;
          this.expends = this.menuData.map(v => v.id)
        }
      })

      this.request.get("/role/roleMenu/"+ this.roleId).then(res => {
        if(res.data){
          this.checks = res.data;
          console.log(this.checks)

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
    },


  }
}
</script>

<style scoped>
.headerBg {
  background: #dedede !important;
}
</style>