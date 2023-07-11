<template>
    <div style="height: 100%;display: flex;flex-direction: column;justify-content: center;">
        <div class="home_up">
            <div class="totalExpenditure">
                <div class="center" style="height: 100%; width: 92%;">
                    <p class="expenditure">总支出发布</p>
                    <div style="height: 87.5%;width: 100%;">
                        <div class="food td " v-for="item in data">
                            <p class="type">{{ item.type }}：</p>
                            <p class="percentage ">{{ item.percentage }}</p>
                            <p class="money">￥{{ item.money }}</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="totalIncome">
                <div class="center" style="height: 100%; width: 92%;">
                    <p class="expenditure">总收入发布</p>
                    <div style="height: 87.5%;width: 100%;">
                        <div class="food td td1 " v-for="item in data">
                            <p class="type">{{ item.type }}：</p>
                            <p class="percentage ">{{ item.percentage }}</p>
                            <p class="money">￥{{ item.money }}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="home_up">
            <div class="totalExpenditure">
                <div class="center" style="height: 100%; width: 92%;" v-if="LineHeight">
                    <Line :Hight="LineHeight" :Width="LineWidth" />
                </div>
            </div>
            <div class="totalIncome"></div>
        </div>
    </div>
</template>

<script setup lang='ts'>
import Line from "./line.vue"
import { ref, onMounted, onUnmounted } from 'vue'
let LineWidth = ref<number>(0);
let LineHeight = ref<number>(0);
const resetChartSize = () => {
    // 获取折线图宽高，做成自适应布局
    LineWidth.value = (window.innerWidth - 200) * 0.49 * 0.92
    LineHeight.value = (window.innerHeight - 60) * 0.50 * 0.97
}
//  模拟数据
const data = ref([
    {
        type: "饮食",
        money: "200",
        percentage: "40%"
    },
    {
        type: "洗脚",
        money: "200",
        percentage: "40%"
    },
    {
        type: "饮食",
        money: "200",
        percentage: "40%"
    },
    {
        type: "旅游",
        money: "200",
        percentage: "40%"
    },
    {
        type: "生活用品",
        money: "200",
        percentage: "40%"
    },
    {
        type: "电子产品",
        money: "200",
        percentage: "40%"
    },
    {
        type: "其他",
        money: "200",
        percentage: "40%"
    },
])

onMounted(() => {
    const td = document.querySelector(".td") as HTMLElement;
    td.style.borderTop = "1px solid #000 "
    const td1 = document.querySelector(".td1") as HTMLElement;
    td1.style.borderTop = "1px solid #000 "
    resetChartSize()
    // 监听浏览器窗口的变化重新设置宽高
    window.addEventListener("resize", resetChartSize);
});
onUnmounted(() => {
    // 组件销毁移出监听
    window.removeEventListener("resize", resetChartSize);
});


</script>

<style lang='scss' scoped>
.home_up {
    height: 50%;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-around;

    .totalExpenditure,
    .totalIncome {
        height: 97%;
        width: 49%;
        display: flex;
        justify-content: center;
        background-color: #eee;
    }

    .expenditure {
        height: 12.5%;
        font-size: 25px;
        display: flex;
        align-items: center;
    }

    .top {
        border-top: 1px solid #000;
    }

    .td {
        height: 13%;
        border-right: 1px solid #000;
        border-left: 1px solid #000;
        display: flex;
        font-size: 20px;
        align-items: center;
        background-color: #ccc;
        border-bottom: 1px solid #000;
        position: relative;

        .type {
            width: 70%;
            margin-left: 10px;
        }

        .percentage {
            width: 10%;
            font-size: 16px;
            color: #000;
            opacity: 37%;
        }

        .money {
            display: flex;
            flex: 1;
            justify-content: center;
        }
    }


}
</style>
