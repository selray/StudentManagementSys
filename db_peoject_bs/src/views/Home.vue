<template>
  <div>
  <h1>欢迎进入学生选课管理系统</h1>
  <br>
    <el-card class="box-card">
      <div >
        <div class="text item "><i class="el-icon-s-home" style="margin:10px"></i>项目名：学生管理系统</div>
        <div class="text item"><i class="el-icon-user-solid" style="margin:10px"></i>开发人员：李洪辰，易津锐，张有临</div>
        <div class="text item"><i class="el-icon-location" style="margin:10px"></i>上海大学</div>
        <div class="text item"><i class="el-icon-error" style="margin:10px"></i>仅供学习，禁止抄袭</div>
      </div>
    </el-card>
  </div>
</template>

<script>
import * as echarts from 'echarts'
export default {
  name: "Home",
  data(){
    return{

    }
  },
  mounted() {//页面元素渲染之后触发
    //折线图
    var option = {
      xAxis: {
        type: 'category',
        data: []
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };

    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    //请求后端数据
    this.request.get("/echarts/example").then(res => {
      //填写数据
      //这里有两个series表
      option.xAxis.data = res.data.x
      option.series[0].data = res.data.y
      option.series[1].data = res.data.y
      //数据准备完毕之后set
      myChart.setOption(option)
    })

    //饼图
    var pieoption = {
      title: {
        text: 'Referer of a Website',
        subtext: 'Fake Data',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 1048, name: 'Search Engine' },
            { value: 735, name: 'Direct' },
            { value: 580, name: 'Email' },
            { value: 484, name: 'Union Ads' },
            { value: 300, name: 'Video Ads' }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };

    var pieDom = document.getElementById('pie');
    var pieChar = echarts.init(pieDom);
    pieChar.setOption(pieoption);
  }
}
</script>

<style scoped>
.box-card {
  width: 480px;
}
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}
</style>