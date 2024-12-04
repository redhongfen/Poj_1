<template>
    <div class="">
        <div>
            <el-input style="width: 200px" placeholder="查询学生编号" v-model="student_id"></el-input>
            <el-input style="width: 200px; margin: 0 5px" placeholder="查询学生姓名" v-model="name"></el-input>
            <el-button type="primary" @click="load(1)">查询</el-button>
            <el-button type="info" @click="reset">重置</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary" plain @click="handleAdd">新增</el-button>
            <el-button type="danger" plain @click="delBatch">批量删除</el-button>
        </div>
        <el-table
            :data="tableData"
            stripe
            :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
            @selection-change="handleSelectionChange"
        >
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column label="学生编号" prop="student_id" align="center"></el-table-column>
            <el-table-column label="姓名" prop="name" align="center"></el-table-column>
            <el-table-column label="专业" prop="major" align="center"></el-table-column>
            <el-table-column label="班级" prop="grade" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
                <template v-slot="scope">
                    <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger" plain @click="del(scope.row.student_id)">删除</el-button>
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
                :total="total"
            >
            </el-pagination>
        </div>

        <el-dialog title="学生信息" :visible.sync="fromVisible" width="30%">
            <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
                <el-form-item label="学生编号" prop="student_id">
                    <el-input v-model="form.student_id" placeholder="学生编号"></el-input>
                </el-form-item>
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="form.name" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item label="专业" prop="major">
                    <el-input v-model="form.major" placeholder="年龄"></el-input>
                </el-form-item>
                <el-form-item label="班级" prop="grade">
                    <el-input type="grade" v-model="form.grade" placeholder="班级"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="fromVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "StudentsView",
    data() {
        return {
            tableData: [],
            pageNum: 1, // 当前的页码
            pageSize: 5, // 每页显示的个数
            student_id: "",
            name: "",
            total: 0,
            fromVisible: false,
            form: {},
            user: JSON.parse(localStorage.getItem("honey-user") || "{}"),
            rules: {
                student_id: [{ required: true, message: "请输入学生编号", trigger: "blur" }],
                name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
            },
            ids: [],
        };
    },
    created() {
        this.load();
    },
    methods: {
        delBatch() {
            if (!this.ids.length) {
                this.$message.warning("请选择数据");
                return;
            }
            this.$confirm("您确认批量删除这些数据吗？", "确认删除", { type: "warning" })
                .then(() => {
                    this.$request.delete("/students/delete/batch", { data: this.ids }).then((res) => {
                        if (res.code === "200") {
                            this.$message.success("操作成功");
                            this.load();
                        } else {
                            this.$message.error(res.msg);
                        }
                    });
                })
                .catch(() => {});
        },
        handleSelectionChange(rows) {
            this.ids = rows.map((v) => v.id);
        },
        del(id) {
            this.$confirm("您确认删除吗？", "确认删除", { type: "warning" })
                .then(() => {
                    this.$request.delete(`/students/delete/${id}`).then((res) => {
                        if (res.code === "200") {
                            this.$message.success("操作成功");
                            this.load(1);
                        } else {
                            this.$message.error(res.msg);
                        }
                    });
                })
                .catch(() => {});
        },
        handleEdit(row) {
            this.form = JSON.parse(JSON.stringify(row));
            this.fromVisible = true;
        },
        handleAdd() {
            this.form = {};
            this.fromVisible = true;
        },
        save() {
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.$request({
                        url: this.form.id ? "/students/update" : "/students/add",
                        method: this.form.id ? "PUT" : "POST",
                        data: this.form,
                    }).then((res) => {
                        if (res.code === "200") {
                            this.$message.success("保存成功");
                            this.load(1);
                            this.fromVisible = false;
                        } else {
                            this.$message.error(res.msg);
                        }
                    });
                }
            });
        },
        reset() {
            this.student_id = "";
            this.name = "";
            this.load();
        },
        load(pageNum) {
            if (pageNum) this.pageNum = pageNum;
            this.$request.get("/students/selectByPage", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    student_id: this.student_id,
                    name: this.name,
                },
            }).then((res) => {
                this.tableData = res.data.list;
                this.total = res.data.total;
            });
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum);
        },
    },
};
</script>

<style scoped></style>
