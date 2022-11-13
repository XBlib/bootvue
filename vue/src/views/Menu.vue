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
      <el-button type="primary" @click="handleAdd('')">新增 <i class="el-icon-circle-plus-outline"></i></el-button>

        <el-input class="ml-5" style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="menuName">
        </el-input>

        <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
        <el-button class="mr-5" type="warning" @click="reset">重置</el-button>
    </div>
    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
              row-key="id" default-expand-all @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="menuName" label="名称" align="center"></el-table-column>
      <el-table-column prop="path" label="路径"  align="center"></el-table-column>
      <el-table-column prop="icon" label="图标"  align="center"></el-table-column>
      <el-table-column prop="description" label="描述"  align="center"></el-table-column>
      <el-table-column label="操作" align="center" >
        <template slot-scope="scope">
          <el-button type="primary" @click="handleAdd(scope.row.id)" v-if="!scope.row.pid && !scope.row.path" >新增子菜单 <i class="el-icon-plus"></i></el-button>
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

    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.menuName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="form.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveMenu">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: "Menu",

  data() {
    return {
      tableData: [],
      total:0,
      menuName:"",
      pageNum:1,
      pageSize:10,
      dialogFormVisible: false,
      headerBg: 'headerBg',
      form: {},
      multipleSelection: []
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
      this.menuName = ""
      this.load()
    },

    load() {
      this.request.get("/menu", {
        params : {
          menuName: this.menuName,
        }
      }).then(res => {
        if(res.data){
          this.tableData = res.data
        }

      })
    },
    saveMenu() {
      console.log(this.form)
      this.request.post("/menu/save",this.form).then(res => {

        if(res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    handleAdd(pid) {
      this.dialogFormVisible = true
      this.form = {}
      if(pid) {
        this.form.pid = pid
      }
    },
    updateUser(scope) {
      this.form = scope
      this.dialogFormVisible = true
    },
    delUser(id) {
      this.request.delete("/menu/del/" + id).then(res =>{
        if(res.code === '200') {
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
      this.request.post("/menu/delBatch/",ids).then(res =>{
        if(res.code === '200') {
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

<style scoped>
.headerBg {
  background: #dedede !important;
}
</style>