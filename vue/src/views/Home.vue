<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 100px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div >用户总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">100</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #40ff46">
          <div >销售总量</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">100</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #ff9340">
          <div >受益总额</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">100</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #e240ff">
          <div >门店总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">100</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 400px;height: 650px"></div>

      </el-col>
      <el-col :span="12">
        <div id="pi" style="width: 800px;height: 600px"></div>

      </el-col>
    </el-row>
  </div>

</template>


<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data() {
    return{
    }
  },
  created() {
  },
  methods: {

    f1() {
      //饼状图
      var ipChartDom = document.getElementById('pi');
      var ipMyChart = echarts.init(ipChartDom);
      var ipOption;
      ipOption = {
        title: {
          text: '各季度会员统计',
          subtext: '           比例图',
          left: '200px'
        },
        tooltip: {
          trigger: 'item',
        },
        legend: {
          left: 'left',
          top: 'bottom',
          data: [
            'rose1',
            'rose2',
            'rose3',
            'rose4'
          ]
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: '书亦烧仙草',
            type: 'pie',
            radius: '50%',
            center: ['30%', '70%'],
            roseType: 'radius',
            itemStyle: {
              borderRadius: 5
            },
            label: {
              show: true,
            },
            emphasis: {
              label: {
                show: true
              }
            },
            data: [],

          },
          {
            name: '蜜雪冰城',
            type: 'pie',
            center: ['70%','30%'],
            radius: '50%',
            roseType: 'area',
            itemStyle: {
              borderRadius: 5
            },
            data: []
          }

        ]
      };

      //柱状图
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      var option;
      option = {
        title: {
          text: '各季度会员统计',
          subtext: '趋势图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        xAxis: {
          type: 'category',
          data: ['第一季度', '第二季度', '第三季度', '第四季度']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '书亦烧仙草',
            data: [],
            type: 'line'
          },
          {
            name: '书亦烧仙草',
            data: [],

            type: 'bar'
          },
          {
            name: '蜜雪冰城',
            data: [],
            type: 'line'
          },
          {
            name: '蜜雪冰城',
            data: [],
            type: 'bar'
          }
        ]
      };
      //请求数据
      this.request.post("/echarts/members").then(res => {
        //柱状图
        option.series[0].data = res.data
        option.series[1].data = res.data

        option.series[2].data = [1,2,3,4]
        option.series[3].data = [1,2,3,4]
        option && myChart.setOption(option);
        //饼状图
        ipOption.series[0].data = [
          {name: "第一季度", value: res.data[0]},
          {name: "第二季度", value: res.data[1]},
          {name: "第三季度", value: res.data[2]},
          {name: "第四季度", value: res.data[3]},
        ]
        ipOption.series[1].data = [
          {name: "第一季度", value: 2},
          {name: "第二季度", value: 5},
          {name: "第三季度", value: 4},
          {name: "第四季度", value: 8},
        ]
        ipOption && ipMyChart.setOption(ipOption);
      })

    },

  },
  mounted() {
    this.f1()
  }
}
</script>

<style scoped>

</style>