<template>
    <div>
        <!-- 查询部分 -->
        <div>
            <el-input style="width: 200px" placeholder="查询课程代码" v-model="course_code"></el-input>
            <el-input style="width: 200px; margin: 0 5px" placeholder="查询课程名称" v-model="course_name"></el-input>
            <el-button type="primary" @click="loadCourses(1)">查询</el-button>
            <el-button type="info" @click="reset">重置</el-button>
        </div>
        
        <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }">
            <el-table-column label="课程代码" prop="course_code" align="center"></el-table-column>
            <el-table-column label="课程名称" prop="course_name" align="center"></el-table-column>
            <el-table-column label="学分" prop="credits" align="center"></el-table-column>
            <el-table-column label="课程简介" prop="description" align="center"></el-table-column>
            <el-table-column label="学期" prop="semester" align="center"></el-table-column>

            <el-table-column label="操作" align="center">
                <template v-slot="scope">
                    <el-button 
                        v-if="!isCourseEnrolled(scope.row.course_code)" 
                        size="mini" 
                        type="primary" 
                        plain 
                        @click="enroll(scope.row)">
                        选课
                    </el-button>
                    <el-button 
                        v-else 
                        size="mini" 
                        type="danger" 
                        plain 
                        @click="unenroll(scope.row)">
                        取消选课
                    </el-button>
                </template>
            </el-table-column>
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
    name: "CoursesView",
    data() {
        return {
            tableData: [], // 课程数据
            pageNum: 1, // 当前页码
            pageSize: 5, // 每页显示的数量
            course_code: '', // 查询条件：课程代码
            course_name: '', // 查询条件：课程名称
            total: 0, // 总课程数
            enrolledCourses: [], // 已选课程列表
            user: JSON.parse(localStorage.getItem('honey-user') || '{}'), // 用户信息
        };
    },
    created() {
        this.loadCourses(1); // 初始化加载课程数据
    },
    methods: {
        // 加载课程数据
        loadCourses(pageNum) {
            if (pageNum) this.pageNum = pageNum;
            // 获取所有课程
            this.$request.get('/courses/selectByPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    course_code: this.course_code,
                    course_name: this.course_name,
                }
            }).then((res) => {
                console.log('课程接口返回数据:', res);
                const courses = Array.isArray(res.data.list) ? res.data.list : [];
                this.total = res.data.toto || 0;

                // 获取已选课程数据
                this.loadEnrollments().then(() => {
                    // 标记每门课程是否已选
                    this.tableData = courses.map((course) => ({
                        ...course,
                        isEnrolled: this.enrolledCourses.some(
                            (enrollment) => enrollment.course_code === course.course_code
                        ),
                    }));
                });
            });
        },

        // 加载已选课程数据
        loadEnrollments() {
            return this.$request.get('/enrollments/selectAll', {
                params: { student_id: this.user.id },
            }).then((res) => {
                this.enrolledCourses = res.data || [];
            });
        },

        // 选课逻辑
        enroll(course) {
            this.$request.post('/enrollments/add', {
                student_id: this.user.id,
                course_code: course.course_code,
            }).then((res) => {
                if (res.code === '200') {
                    this.$message.success('选课成功');
                    this.loadCourses(this.pageNum); // 重新加载课程列表
                } else {
                    this.$message.error(res.msg);
                }
            });
        },

        // 取消选课逻辑
        unenroll(course) {
            const enrollment = this.enrolledCourses.find(
                (item) => item.course_code === course.course_code
            );
            if (enrollment) {
                this.$request.delete(`/enrollments/delete/${enrollment.enrollment_id}`).then((res) => {
                    if (res.code === '200') {
                        this.$message.success('取消选课成功');
                        this.loadCourses(this.pageNum); // 重新加载课程列表
                    } else {
                        this.$message.error(res.msg);
                    }
                });
            }
        },

        // 判断课程是否已经选修
        isCourseEnrolled(courseCode) {
            return this.enrolledCourses.some((enrollment) => enrollment.course_code === courseCode);
        },

        // 重置查询条件
        reset() {
            this.course_code = '';
            this.course_name = '';
            this.loadCourses(1);
        },

        // 分页切换
        handleCurrentChange(pageNum) {
            this.loadCourses(pageNum);
        },
    },
};
</script>

<style scoped>
</style>
