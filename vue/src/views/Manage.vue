<template>
    <el-container style="min-height: 100vh; ">
      <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); overflow-x: hidden; box-shadow:rgba(0,21,41,0.35) 2px 0 6px; ">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"></Aside>
      </el-aside>
      <el-container>

        <el-header style="border-bottom: 1px solid #ccc;">
          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"></Header>
        </el-header>

        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import Header from "@/components/Header";
import Aside from "@/components/Aside";
export default {
  name: 'HomeView',
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
    }
  },
  create: {

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
    }
  },
  components: {
    Aside,
    Header
  }

}
</script>


<style>
.headerBg {
  background: #dedede !important;
}
</style>
