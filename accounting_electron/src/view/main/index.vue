<template>
    <div style="height: 100%;">
        <div class="home_up">
            <div class="totalExpenditure">
                <p class="expenditure">总支出发布</p>
                <el-table :data="tableData" style="width: 100%">
                    <el-table-column prop="date" label="类型" width="180" />
                    <el-table-column prop="expenditure" label="支出" width="180" />
                    <el-table-column prop="Percentage" label="所占比例" />
                </el-table>
            </div>
            <div class="totalIncome">
                <p class="expenditure">总收入发布</p>
                <el-table :data="tableData" style="width: 100%">
                    <el-table-column prop="date" label="类型" width="180" />
                    <el-table-column prop="expenditure" label="支出" width="180" />
                    <el-table-column prop="Percentage" label="所占比例" />
                </el-table>
            </div>
        </div>
        <div class="home_up">
            <div class="totalExpenditure">

            </div>
            <div class="totalIncome"></div>
        </div>
    </div>
</template>

<script setup lang='ts'>
const tableData = [
    {
        date: '饮食',
        expenditure: '200',
        Percentage: "10%",
    },
    {
        date: '洗脚',
        expenditure: '300',
        Percentage: '20%',
    },
    {
        date: '旅游',
        expenditure: '400',
        Percentage: '30%',
    },
    {
        date: '生活用品',
        expenditure: '500',
        Percentage: '40%',
    },
]
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
        width: 46%;
        // background-color: aquamarine;
    }

    .expenditure {
        font-size: 20px;
        margin-bottom: 10px;
    }

}
</style>
