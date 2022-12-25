<template>

  <div>
    <div>
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-upload action="http://localhost:9090/file/upload " :headers="headerObject" :show-file-list="false"  :on-success="handleFileUploadSuccess" :on-error="handleFileUploadFail" style="display: inline-block">
        <el-button type="primary" class="ml-5">上传文件 <i class="el-icon-top"></i> </el-button>
      </el-upload>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='取消'
          icon="el-icon-info"
          icon-color="red"
          title="您确定要删除吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference" >批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>
    <el-table :data="tableData" style="align-content: center" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="name" label="文件名称" width="120" align="center"></el-table-column>
      <el-table-column prop="type" label="文件类型" align="center"></el-table-column>
      <el-table-column prop="size" label="文件大小(KB)" align="center"></el-table-column>
      <el-table-column label="下载" align="center">
        <template slot-scope="scope" >
          <el-button type="primary"  @click="downLoad(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="启用" align="center">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="操作" label="删除" width="200" align="center">
        <template slot-scope="scope">
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="您确定要删除吗？"
              @confirm="delFile(scope.row.id)"
          >
            <el-button class="ml-5" type="danger" slot="reference">删除 <i class="el-icon-delete"></i></el-button>
          </el-popconfirm>
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

  </div>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: "File",
  data() {
    return {
      tableData: [],
      name: '',
      multipleSelection: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      headerBg: 'headerBg',
      headerObject: {
        token: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).token : {}
      }
    }
  },
  components: {
    Aside,
    Header
  },
  created() {
    this.load()
  },
  methods: {
    downLoad(url) {
      window.open(url)
    },
    reset() {
      this.name = ""
      this.load()
    },
    load() {
      this.request.get("/file/page", {
        params : {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        if(res.code==='200'){
          this.tableData = res.data.records
          this.total = res.data.total
          console.log(this.tableData)
        }
      })
    },


    updateUser(scope) {
      this.form = scope
      this.dialogFormVisible = true
    },

    delFile(id) {
      this.request.delete("/file/del/" + id).then(res =>{
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
      this.request.post("/file/delBatch/",ids).then(res =>{
        if(res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleFileUploadSuccess() {
      this.$message.success("文件上传成功")
      this.load()
    },
    handleFileUploadFail() {
      this.$message.error("文件上传失败")
    },

    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },

    changeEnable(row) {
      this.request.post("/file/update",row).then(res => {
        if(res.code === '200') {
          this.$message.success("操作成功")
        }
      })
    }
  }
}
</script>

<style scoped>

.headerBg {
  background: #dedede !important;
}
</style>