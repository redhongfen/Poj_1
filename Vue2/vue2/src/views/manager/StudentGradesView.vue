<template>
    <div>
        <!-- 查询部分 -->
        <div>
            <el-input 
                style="width: 200px" 
                placeholder="查询课程代码" 
                v-model="course_code">
            </el-input>
            <el-button 
                type="primary" 
                @click="loadGrades">
                查询
            </el-button>
            <el-button 
                type="info" 
                @click="reset">
                重置
            </el-button>
        </div>

        <!-- 成绩表格 -->
        <el-table 
            :data="tableData" 
            stripe 
            :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }">
            <el-table-column 
                label="课程代码" 
                prop="course_code" 
                align="center">
            </el-table-column>
            <el-table-column 
                label="成绩" 
                prop="score" 
                align="center">
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    name: "StudentGradesView",
    data() {
        return {
            tableData: [], // 成绩数据
            course_code: '', // 查询条件：课程代码
            user: JSON.parse(localStorage.getItem('honey-user') || '{}'), // 当前用户信息
        };
    },
    created() {
        this.loadGrades(); // 初始化加载成绩数据
    },
    methods: {
        // 加载成绩数据
        loadGrades() {
            const studentId = this.user.id;
            if (!studentId) {
                this.$message.error('未找到学生信息，请登录后重试');
                return;
            }

            this.$request.get(`/grades/selectByStudentId/${studentId}`).then((res) => {
                if (res.code === '200' && Array.isArray(res.data)) {
                    // 检查数据结构是否是数组，确保处理每一项成绩
                    this.tableData = res.data.map((item) => ({
                        course_code: item.course_code,
                        score: item.score !== null ? item.score : '-', // 处理成绩为 null 的情况
                    }));
                } else {
                    this.$message.error(res.msg || '加载成绩失败');
                }
            });
        },

        // 重置查询条件
        reset() {
            this.course_code = '';
            this.loadGrades();
        },
    },
};
</script>

<style scoped>
</style>
