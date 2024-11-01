<template>
<div class="" >
    <div style="margin: 10px 0;">
        <el-input style="width: 200px" placeholder="查询商品名" v-model="goodsName"></el-input>
        <el-button type="primary" @click="load(1)">查询</el-button>
        <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div style="margin-bottom: 10px" v-if="user.role !== '用户'">
        <el-button type="primary" plain @click="handleAdd">新增</el-button>
        <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>
    <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column label="ID" prop="id" align="center"></el-table-column>
        <el-table-column label="商品名" prop="goodsName" align="center"></el-table-column>
        <el-table-column label="价格" prop="price" align="center"></el-table-column>
        <el-table-column label="商品照">
            <template v-slot="scope">
                <div style="display: flex; align-items: center">
                    <el-image style="width: 50px; height: 50px; " v-if="scope.row.avatar" :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
                </div>
            </template>
        </el-table-column>
        <el-table-column label="操作" align="center" v-if="user.role !== '用户'" >
            <template v-slot="scope">
                <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
            </template>
        </el-table-column>
        <el-table-column label="操作购物车" align="center" v-if="user.role === '用户'">
            <template v-slot="scope">
                <div style="display: flex; align-items: center; justify-content: center; width: 100%;">
                    <el-button size="mini" type="primary" plain @click="addToCart(scope.row)" style="margin-right: 10px;">+</el-button>
                    <p style="margin: 0 10px; text-align: center;">{{ getItemQuantity(scope.row.id) }}</p>
                    <el-button size="mini" type="danger" plain @click="removeFromCart(scope.row)" style="margin-left: 10px;">-</el-button>
                </div>
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

    <el-dialog title="信息" :visible.sync="fromVisible" width="30%">
            <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
                <el-form-item label="商品名" prop="goodsName">
                <el-input v-model="form.goodsName" placeholder="商品名"></el-input>
                </el-form-item>
                <el-form-item label="价格" prop="price">
                <el-input v-model="form.price" placeholder="价格"></el-input>
                </el-form-item>

                <el-form-item label="头像">
                <el-upload
                    class="avatar-uploader"
                    action='http://localhost:8081/file/upload' 
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
import { mapActions,mapGetters } from 'vuex';
export default{
    name:"ShoppingView",
    data(){
        return{
            tableData:[],
            pageNum: 1,   // 当前的页码
            pageSize: 5,  // 每页显示的个数
            goodsName: '',
            total: 0,
            fromVisible: false,
            form: {},
            user: JSON.parse(localStorage.getItem('honey-user') || '{}'),
            rules: {
                username: [
                { required: true, message: '请输入账号', trigger: 'blur' },
                ]
            },
            ids: [],
        }
    },
    created(){
        this.load()
    },
    computed: {
        ...mapGetters(['getItemQuantity']), // 确保这个 getter 被映射
    },
    methods:{
        ...mapActions(['addToCart', 'removeFromCart']), // 映射 Vuex actions
        delBatch() {
            if (!this.ids.length) {
                this.$message.warning('请选择数据')
                return
            }
              // eslint-disable-next-line no-unused-vars
            this.$confirm('您确认批量删除这些数据吗？', '确认删除', {type: "warning"}).then(response => {
                this.$request.delete('/goods/delete/batch', { data: this.ids }).then(res => {
                if (res.code === '200') {   // 表示操作成功
                    this.$message.success('操作成功')
                    this.load()
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
                this.$request.delete('/goods/delete/' + id).then(res => {
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
            this.form = { role: '商品' }  // 新增数据的时候清空数据
            this.fromVisible = true   // 打开弹窗
        },
        save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
            this.$refs.formRef.validate((valid) => {
                if (valid) {

                console.log("???",this.form);
                
                this.$request({
                    url: this.form.id ? '/goods/update': '/goods/add',
                    method: this.form.id ? 'PUT' : 'POST',
                    data: this.form,
                    
                }).then(res => {
                    if (res.code === '200') {  // 表示成功保存
                    this.$message.success('保存成功')
                    this.load(1)
                    this.fromVisible = false
                    } else {
                    this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
                console.log(this.form);
                
                }
            })
        },
        reset() {
            this.goodsName = ''
            this.load()
        },
        load(pageNum) {  // 分页查询
            if (pageNum)  this.pageNum = pageNum
            this.$request.get('/goods/selectByPage', {
                params: {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                goodsName: this.goodsName,
                }
            }).then(res => {
                this.tableData = res.data.list
                this.total = res.data.toto
                
            })
            console.log(this.user.role);

            
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
        // eslint-disable-next-line no-unused-vars
        handleAvatarSuccess(response, file, fileList) {
            console.log(response);
            console.log(this.form.id);
            
    // 更新 form.avatar 和 fileList，确保 fileList 包含对象而不是字符串
            this.form.avatar = response.data;  // 后端返回的图片 URL            
        },
    }
}
</script>

<style scoped>
</style>