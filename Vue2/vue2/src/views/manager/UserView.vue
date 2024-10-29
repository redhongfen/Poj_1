<template>
    <div class="">
        <div>
            <el-input style="width: 200px" placeholder="查询用户名" v-model="username"></el-input>
            <el-input style="width: 200px; margin: 0 5px" placeholder="查询姓名"  v-model="name"></el-input>
            <el-button type="primary" @click="load(1)">查询</el-button>
            <el-button type="info" @click="reset">重置</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary" plain @click="handleAdd">新增</el-button>
            <el-button type="danger" plain @click="delBatch">批量删除</el-button>
        </div>
        <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column label="ID" prop="id" align="center"></el-table-column>
            <el-table-column label="用户名" prop="username" align="center"></el-table-column>
            <el-table-column label="姓名" prop="name" align="center"></el-table-column>
            <el-table-column label="地址" prop="address" align="center"></el-table-column>
            <el-table-column label="电话" prop="phone" align="center"></el-table-column>
            <el-table-column label="邮件地址" prop="email" align="center"></el-table-column>
            <el-table-column label="头像">
                <template v-slot="scope">
                    <div style="display: flex; align-items: center">
                        <el-image style="width: 50px; height: 50px; border-radius: 50%" v-if="scope.row.avatar" :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="角色" prop="role" align="center"></el-table-column>
            <el-table-column label="操作" align="center">
                <template v-slot="scope">
                    <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
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

        <el-dialog title="收货地址" :visible.sync="fromVisible" width="30%">
            <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
                <el-form-item label="用户名" prop="username">
                <el-input v-model="form.username" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item label="姓名" prop="name">
                <el-input v-model="form.name" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                <el-input v-model="form.phone" placeholder="电话"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="address">
                <el-input type="textarea" v-model="form.address" placeholder="地址"></el-input>
                </el-form-item>
                <el-form-item label="角色" prop="role">
                <el-radio-group v-model="form.role">
                    <el-radio label="管理员"></el-radio>
                    <el-radio label="用户"></el-radio>
                </el-radio-group>
                </el-form-item>
                <el-form-item label="头像">
                <el-upload
                    class="avatar-uploader"
                    action="http://localhost:8081/file/upload"
                    :headers="{ token: user.token }"
                    :file-list="form.avatar? [form.avatar] : []"
                    list-type="picture"
                    :on-success="handleAvatarSuccess"
                >
                    <el-button type="primary">上传头像</el-button>
                </el-upload>
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
export default{
    name:"UserView",
    data(){
        return{
            tableData:[],
            pageNum: 1,   // 当前的页码
            pageSize: 5,  // 每页显示的个数
            username: '',
            name: '',
            total: 0,
            fromVisible: false,
            form: {},
            user: JSON.parse(localStorage.getItem('honey-user') || '{}'),
            rules: {
                username: [
                { required: true, message: '请输入账号', trigger: 'blur' },
                ]
            },
            ids: []
        }
    },
    mounted() {
        // 在控制台打印数据
        console.log('tableData:', this.tableData);
        console.log('pageNum:', this.pageNum);
        console.log('pageSize:', this.pageSize);
        console.log('username:', this.username);
        console.log('name:', this.name);
    },
    created(){
        this.load()
    },
    
    methods:{

        delBatch() {
            if (!this.ids.length) {
                this.$message.warning('请选择数据')
                return
            }
              // eslint-disable-next-line no-unused-vars
            this.$confirm('您确认批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
                this.$request.delete('/user/delete/batch', { data: this.ids }).then(res => {
                if (res.code === '200') {   // 表示操作成功
                    this.$message.success('操作成功')
                    this.lode()
                } else {
                    this.$message.error(res.msg)  // 弹出错误的信息
                }
                })
            }).catch(() => {})
        },
        handleSelectionChange(rows) {   // 当前选中的所有的行数据
            this.ids = rows.map(v => v.id)
        },
        del(id) {
            // eslint-disable-next-line no-unused-vars
            this.$confirm('您确认删除吗？', '确认删除', {type: "warning"}).then(response => {
                this.$request.delete('/user/delete/' + id).then(res => {
                if (res.code === '200') {   // 表示操作成功
                    this.$message.success('操作成功')
                    this.load(1)
                } else {
                    this.$message.error(res.msg)  // 弹出错误的信息
                }
                })
            }).catch(() => {})
        },
        handleEdit(row) {   // 编辑数据
            this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
            this.fromVisible = true   // 打开弹窗
        },
        handleAdd() {   // 新增数据
            this.form = { role: '用户' }  // 新增数据的时候清空数据
            this.fromVisible = true   // 打开弹窗
        },
        save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                this.$request({
                    url: this.form.id ? '/user/update': '/user/add',
                    method: this.form.id ? 'PUT' : 'POST',
                    data: this.form
                }).then(res => {
                    if (res.code === '200') {  // 表示成功保存
                    this.$message.success('保存成功')
                    this.load(1)
                    this.fromVisible = false
                    } else {
                    this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
                }
            })
        },
        reset() {
            this.name = ''
            this.username = ''
            this.load()
        },
        load(pageNum) {  // 分页查询
            if (pageNum)  this.pageNum = pageNum
            this.$request.get('/user/selectByPage', {
                params: {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                username: this.username,
                name: this.name
                }
            }).then(res => {
                this.tableData = res.data.list
                this.total = res.data.toto
            })
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
        // eslint-disable-next-line no-unused-vars
        handleAvatarSuccess(response, file, fileList) {
        // 把user的头像属性换成上传的图片的链接
        this.form.avatar = response.data
        },
    }
}
</script>

<style scoped>
</style>