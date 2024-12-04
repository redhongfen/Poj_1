<template>
    <div class="">
        <!-- 查询部分 -->
        <div>
            <el-input style="width: 200px" placeholder="查询课程代码" v-model="course_code"></el-input>
            <el-input style="width: 200px; margin: 0 5px" placeholder="查询学生学号" v-model="student_id"></el-input>
            <el-button type="primary" @click="load(1)">查询</el-button>

                        <!-- 添加排序和筛选按钮 -->
                        <el-button 
                :type="activeSort === 'desc' ? 'success' : 'primary'" 
                @click="toggleSort('desc')">
                按分数降序排序
            </el-button>
            <el-button 
                :type="activeSort === 'asc' ? 'success' : 'primary'" 
                @click="toggleSort('asc')">
                按分数升序排序
            </el-button>
            <el-button 
                :type="showLowScores ? 'warning' : 'primary'" 
                @click="toggleFilterLowScores">
                显示分数低于 60 的学生
            </el-button>
            <el-button type="info" @click="reset">重置</el-button>

        </div>

        <div style="margin: 10px 0">

        </div>

        <!-- 数据表 -->
        <el-table :data="filteredTableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }">
            <el-table-column label="课程代码" prop="course_code" align="center"></el-table-column>
            <el-table-column label="分数" prop="score" align="center"></el-table-column>
            <el-table-column label="学生学号" prop="student_id" align="center"></el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[5, 10, 20, 50]"
                :page-size="pageSize"
                layout="total, prev, pager, next"
                :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    name: "SummaryView",
    data() {
        return {
            tableData: [], // 原始数据
            filteredTableData: [], // 筛选或排序后的数据
            pageNum: 1,   // 当前页码
            pageSize: 5,  // 每页显示的个数
            course_code: '', // 查询条件：课程代码
            student_id: '', // 查询条件：学生学号
            total: 0, // 总数据条数
            activeSort: null, // 当前激活的排序类型：'desc' 或 'asc'
            showLowScores: false // 是否只显示分数低于 60
        };
    },
    mounted() {
        this.load();
    },
    methods: {
        // 加载数据
        load(pageNum) {
            if (pageNum) this.pageNum = pageNum;
            this.$request.get('/grades/selectByPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    course_code: this.course_code,
                    student_id: this.student_id,
                }
            }).then(res => {
                this.tableData = res.data.list;
                this.filteredTableData = [...this.tableData]; // 初始化筛选数据
                this.total = res.data.total;
            });
        },

        // 重置查询条件
        reset() {
            this.course_code = '';
            this.student_id = '';
            this.load();
            this.filteredTableData = [...this.tableData];
            this.activeSort = null; // 清除排序状态
            this.showLowScores = false; // 清除筛选状态
        },

        // 分页切换
        handleCurrentChange(pageNum) {
            this.load(pageNum);
        },

        // 切换排序状态
        toggleSort(type) {
            if (this.activeSort === type) {
                // 如果已经激活，取消排序
                this.activeSort = null;
                this.resetFilter();
            } else {
                // 激活排序
                this.activeSort = type;
                this.sortScores(type);
            }
        },

        // 排序逻辑
        sortScores(type) {
            if (type === 'desc') {
                this.filteredTableData.sort((a, b) => b.score - a.score);
            } else if (type === 'asc') {
                this.filteredTableData.sort((a, b) => a.score - b.score);
            }
        },

        // 切换分数低于 60 的筛选状态
        toggleFilterLowScores() {
            if (this.showLowScores) {
                // 如果已经激活，取消筛选
                this.showLowScores = false;
                this.resetFilter();
            } else {
                // 激活筛选
                this.showLowScores = true;
                this.filteredTableData = this.tableData.filter(item => item.score < 60);
            }
        },

        // 重置筛选和排序
        resetFilter() {
            this.filteredTableData = [...this.tableData];
            this.activeSort = null; // 清除排序状态
            this.showLowScores = false; // 清除筛选状态
        }
    }
};
</script>

<style scoped>
</style>
