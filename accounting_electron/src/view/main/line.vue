<template>
  <div id="main" :style="{ width: Width + 'px', height: Hight + 'px' }">

  </div>
  {{ Width }}
</template>
  
<script setup lang="ts">
import * as echarts from 'echarts/core';
import { onMounted, toRefs, onUnmounted, watch, } from 'vue';
import {
  TitleComponent,
  TitleComponentOption,
  ToolboxComponent,
  ToolboxComponentOption,
  TooltipComponent,
  TooltipComponentOption,
  GridComponent,
  GridComponentOption,
  LegendComponent,
  LegendComponentOption
} from 'echarts/components';
import { LineChart, LineSeriesOption } from 'echarts/charts';
import { UniversalTransition } from 'echarts/features';
import { CanvasRenderer } from 'echarts/renderers';

echarts.use([
  TitleComponent,
  ToolboxComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent,
  LineChart,
  CanvasRenderer,
  UniversalTransition
]);

type EChartsOption = echarts.ComposeOption<
  | TitleComponentOption
  | ToolboxComponentOption
  | TooltipComponentOption
  | GridComponentOption
  | LegendComponentOption
  | LineSeriesOption
>;

const props = defineProps({
  Hight: Number,
  Width: Number
})

let { Hight, Width } = toRefs(props);

const initChart = () => {
  var chartDom = document.querySelector('#main') as HTMLElement;
  var myChart = echarts.init(chartDom);
  var option: EChartsOption;
  option = {
    title: {
      text: '总支出',
      top: "7%"
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['Email', 'Union Ads', 'Video Ads', 'Direct', 'Search Engine']
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: "20%",
      containLabel: true
    },
    toolbox: {
      feature: {
        saveAsImage: {}
      }
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: 'Email',
        type: 'line',
        stack: 'Total',
        data: [120, 132, 101, 134, 90, 230, 210]
      },
      {
        name: 'Union Ads',
        type: 'line',
        stack: 'Total',
        data: [220, 182, 191, 234, 290, 330, 310]
      },
      {
        name: 'Video Ads',
        type: 'line',
        stack: 'Total',
        data: [150, 232, 201, 154, 190, 330, 410]
      },
      {
        name: 'Direct',
        type: 'line',
        stack: 'Total',
        data: [320, 332, 301, 334, 390, 330, 320]
      },
      {
        name: 'Search Engine',
        type: 'line',
        stack: 'Total',
        data: [820, 932, 901, 934, 1290, 1330, 1320]
      }
    ]
  };

  option && myChart.setOption(option);
}
// 监听图表大小变化
watch([Hight, Width], ([newHigh, newWidth], [oldHight, oldWidth]) => {
  var chartDom = document.querySelector('#main') as HTMLElement
  var myChart = echarts.init(chartDom);
  // 重新设置大小
  myChart.resize();
});

onMounted(() => {
  //初始化图表
  initChart();
});
//销毁图表
onUnmounted(() => {
  var chartDom = document.querySelector('#main') as HTMLElement
  var myChart = echarts.init(chartDom);
  myChart.dispose();
});
</script>
  
<style lang="scss">
#main {
  width: 100%;
  height: 400px;
}
</style>